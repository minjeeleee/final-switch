package com.kh.switchswitch.member.model.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

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
	};
}
