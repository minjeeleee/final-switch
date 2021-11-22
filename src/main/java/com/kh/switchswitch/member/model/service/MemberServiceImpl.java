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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.kh.switchswitch.common.code.Config;
import com.kh.switchswitch.common.mail.MailSender;
import com.kh.switchswitch.member.model.dto.KakaoLogin;
import com.kh.switchswitch.member.model.dto.Member;
import com.kh.switchswitch.member.model.dto.MemberAccount;
import com.kh.switchswitch.member.model.repository.MemberRepository;
import com.kh.switchswitch.member.validator.JoinForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService, UserDetailsService {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final RestTemplate http;
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final MailSender mailSender;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = memberRepository.selectMemberByEmailAndDelN(username);
		return new MemberAccount(member);
	}
	
	@Transactional
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
		return memberRepository.selectKakaoLoginById(id);
	}

	public void insertMemberWithKakao(Member member, String id) {
		int cnt = 0;
		//이메일 존재여부 확인
		Member searchedmember = memberRepository.selectMemberByEmailAndDelN(member.getMemberEmail());
		//비밀번호 encoding
		member.setMemberPass(passwordEncoder.encode(member.getMemberPass()));
		//이메일 존재 시
		if(searchedmember != null) {
			//기존회원idx와 함께 kakao login 인스턴스 생성
			memberRepository.insertKakaoLogin(Map.of(searchedmember.getMemberIdx(),id));
		} else {
			//중복 닉네임 존재시 닉네임 뒤에 1씩 증가
			while(memberRepository.selectMemberByNicknameAndDelN(member.getMemberNick()) != null) {
				member.setMemberNick(member.getMemberNick()+cnt);
				cnt++;
			}
			//null값 허용X -> default null
			member.setMemberAddress("null");
			member.setMemberName("null");
			//회원 인스턴스 및 kakao login 인스턴스 생성
			memberRepository.insertMember(member);
			memberRepository.insertKakaoLoginWithId(id);
		}
	}

	public void updateMemberDelYN(Member member) {
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
				jsonData=line;
			}
			
			return jsonData;
			
		} catch(Exception e) {
			return "success";
		}
		
	}
}
