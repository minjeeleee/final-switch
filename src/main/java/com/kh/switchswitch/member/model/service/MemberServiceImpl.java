package com.kh.switchswitch.member.model.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.switchswitch.common.code.Config;
import com.kh.switchswitch.common.mail.MailSender;
import com.kh.switchswitch.common.util.FileDTO;
import com.kh.switchswitch.common.util.FileUtil;
import com.kh.switchswitch.member.model.dto.KakaoLogin;
import com.kh.switchswitch.member.model.dto.Member;
import com.kh.switchswitch.member.model.dto.MemberAccount;
import com.kh.switchswitch.member.model.repository.KakaoRepository;
import com.kh.switchswitch.member.model.repository.MemberRepository;
import com.kh.switchswitch.member.validator.JoinForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final RestTemplate http;
	private final MemberRepository memberRepository;
	private final KakaoRepository kakaoRepository;
	private final PasswordEncoder passwordEncoder;
	private final MailSender mailSender;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = memberRepository.selectMemberByEmailAndDelN(username);
		if(member == null) {
			throw new UsernameNotFoundException(username);
		}
		return new MemberAccount(member);
	}
	
    public void insertMember(JoinForm form) {
       Member member = form.convertToMember();
       member.setMemberPass(passwordEncoder.encode(form.getMemberPass()));
       memberRepository.insertMember(member);
    }

	public void authenticateByEmail(JoinForm form, String token) {
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("mail-template", "join-auth-email");
		body.add("name", form.getMemberName());
		body.add("email", form.getMemberEmail());
		body.add("persistToken", token);
		
		
		try {
			URI uri = new URI(Config.DOMAIN.DESC + "/mail");
			
			//RestTemplate의 기본 ContentType이 application/json이다.
			RequestEntity<MultiValueMap<String, String>> request =
					RequestEntity.post(uri)
					.accept(MediaType.APPLICATION_FORM_URLENCODED)
					.body(body);
			
			String htmlText = http.exchange(request, String.class).getBody();
			mailSender.sendEmail(form.getMemberEmail(), "회원가입을 축하합니다.", htmlText);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	public Member selectMemberByEmailAndDelN(String memberEmail) {
		return memberRepository.selectMemberByEmailAndDelN(memberEmail);
	};
	
	public Member selectMemberByNicknameAndDelN(String memberNick) {
		return memberRepository.selectMemberByNicknameAndDelN(memberNick);
	}

	public KakaoLogin selectKakaoLoginById(String id) {
		return kakaoRepository.selectKakaoLoginById(id);
	}

	public void insertMemberWithKakao(Member member, String id) {
		int cnt = 0;
		//이메일 존재여부 확인 (전체)
		Member searchedmember = memberRepository.selectMemberByEmail(member.getMemberEmail());
		//이메일 존재 O
		if(searchedmember != null) {
			//카카오 존재 X
			if(kakaoRepository.selectKakaoLoginById(id) == null) {
				//비밀번호 업데이트
				searchedmember.setMemberPass(passwordEncoder.encode(id));
				memberRepository.updateMember(searchedmember);
				//기존회원idx와 함께 kakao login 인스턴스 생성
				kakaoRepository.insertKakaoLoginWithMemberIdxAndId(Map.of("memberIdx",searchedmember.getMemberIdx(),"id",id));
			}
			//탈퇴 O
			if(member.getMemberDelYn() == 1) {
				//기존 정보 업데이트
				member.setMemberIdx(searchedmember.getMemberIdx());
				member.setMemberDelYn(0);
				member.setMemberDelDate(null);
				//비밀번호 encoding
				member.setMemberPass(passwordEncoder.encode(id));
				//닉네임 중복시 cnt 1씩 증가
				while(memberRepository.selectMemberByNicknameAndDelN(member.getMemberNick()) != null) {
					member.setMemberNick(member.getMemberNick()+cnt);
					cnt++;
				}
				memberRepository.updateMember(member);
			}
		} else {
			//닉네임 중복시 cnt 1씩 증가
			while(memberRepository.selectMemberByNicknameAndDelN(member.getMemberNick()) != null) {
				member.setMemberNick(member.getMemberNick()+cnt);
				cnt++;
			}
			//null값 허용X -> default null
			member.setMemberAddress("null");
			member.setMemberName("null");
			//비밀번호 encoding
			member.setMemberPass(passwordEncoder.encode(id));
			//회원 인스턴스 및 kakao login 인스턴스 생성
			memberRepository.insertMember(member);
			kakaoRepository.insertKakaoLoginWithId(id);
		}
	}

	public void updateMemberDelYN(Member member) {
		memberRepository.updateMember(member);
	}
	
	public void updateMemberWithFile(Member member, MultipartFile profileImage) {
		member.setMemberIdx(selectMemberByEmailAndDelN(member.getMemberEmail()).getMemberIdx());
		member.setMemberPass(passwordEncoder.encode(member.getMemberPass()));
		
		System.out.println(profileImage);
		if(profileImage != null) {
			FileUtil fileUtil = new FileUtil();
			FileDTO file = memberRepository.insertFileInfo(fileUtil.fileUpload(profileImage));
			member.setFlIdx(file.getFlIdx());
		}
		
		memberRepository.updateMember(member);
	}

	public String getAccessTokenJsonData(String code) {
		String grant_type= "authorization_code";
		String client_id = "a66e69c27f0b16ebbfae461ad8678ee0";
		String redirect_uri = "http://localhost:9090/member/kakaoLogin";
		String client_secret = "rniH7XNqqtaV3XuC9sfRNF9Fr3g9wbIk";
		String token_url = "https://kauth.kakao.com/oauth/token";
	    // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity request = new HttpEntity(headers);

        // URI 빌더 사용
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(token_url)
                .queryParam("grant_type", grant_type)
                .queryParam("client_id", client_id)
                .queryParam("redirect_uri", redirect_uri)
                .queryParam("code", code)
                .queryParam("client_secret", client_secret);

        // 요청 URI과 헤더를 같이 전송
        ResponseEntity<String> responseEntity = http.exchange(
                uriComponentsBuilder.toUriString(),
                HttpMethod.POST,
                request,
                String.class
        );

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        }
        return "error";
	}

	public String getUserInfo(String accessToken) {
		try {
			String jsonData = "";
			
			URL url = new URL("https://kapi.kakao.com/v2/user/me?access_token=" + accessToken);
		    
			BufferedReader bf;
			bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
			String line;
			while((line = bf.readLine()) != null) {
				jsonData+=line;
			}
			
			return jsonData;
			
		} catch(Exception e) {
			return "error";
		}
		
	}
	
	public void updateMemberPass(int memberIdx, String id) {
		Member member = new Member();
		member.setMemberIdx(memberIdx);
		member.setMemberPass(passwordEncoder.encode(id));
		memberRepository.updateMember(member);
	}
	
	public void logoutKakao(String accessToken) {
		try {
			String jsonData = "";
			
			URL url = new URL("https://kapi.kakao.com/v1/user/logout?access_token=" + accessToken);
		    
			BufferedReader bf;
			bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
			
			String line;
			while((line = bf.readLine()) != null) {
				jsonData+=line;
			}
			ObjectMapper logoutMapper = new ObjectMapper();
			Map<String, Object> logoutMap = logoutMapper.readValue(jsonData, Map.class);
			logger.debug(logoutMap.get("id").toString());
			
		} catch(Exception e) {
			logger.debug("카카오 로그아웃 중 오류 발생!!!");
			e.printStackTrace();
		}
	}
	

	public boolean checkNickName(String nickName) {
		if(memberRepository.selectMemberByNickName(nickName) == null) {
			return true;
		}
		return false;
	}

	public FileDTO selectFileInfoByFlIdx(int flIdx) {
		return memberRepository.selectFileInfoByFlIdx(flIdx);
	}

	public String selectEmailByNicknameAndTell(String nickname, String tell) {
		return memberRepository.selectEmailByNicknameAndTell(Map.of("nickname",nickname,"tell", tell));
	}

	public void reissuePwAndSendToEmail(Member foundMember) {
		String reissuedPw = String.valueOf(((int)(Math.random()*100000)+1));
		foundMember.setMemberPass(passwordEncoder.encode(reissuedPw));
		memberRepository.updateMember(foundMember);
		
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("mail-template", "reissue-pw-email");
		body.add("name", foundMember.getMemberName());
		body.add("password", reissuedPw);
		
		try {
			URI uri = new URI(Config.DOMAIN.DESC + "/mail");
			
			//RestTemplate의 기본 ContentType이 application/json이다.
			RequestEntity<MultiValueMap<String, String>> request =
					RequestEntity.post(uri)
					.accept(MediaType.APPLICATION_FORM_URLENCODED)
					.body(body);
			
			String htmlText = http.exchange(request, String.class).getBody();
			mailSender.sendEmail(foundMember.getMemberEmail(), "SwitchSwitch 새로운 비밀번호가 발급되었습니다.", htmlText);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
	}
	
}
