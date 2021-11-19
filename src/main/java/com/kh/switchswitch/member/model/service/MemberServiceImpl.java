package com.kh.switchswitch.member.model.service;

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
import com.kh.switchswitch.member.model.dto.Member;
import com.kh.switchswitch.member.model.dto.MemberAccount;
import com.kh.switchswitch.member.model.repository.MemberRepository;
import com.kh.switchswitch.member.validator.JoinForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService, UserDetailsService {
	
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
		
		//RestTemplate의 기본 ContentType이 application/json이다.
		RequestEntity<MultiValueMap<String, String>> request =
				RequestEntity.post(Config.DOMAIN.DESC + "/mail")
				.accept(MediaType.APPLICATION_FORM_URLENCODED)
				.body(body);
		
		String htmlText = http.exchange(request, String.class).getBody();
		mailSender.sendEmail(form.getMemberEmail(), "회원가입을 축하합니다.", htmlText);
	}
	
	public Member selectMemberByEmailAndDelN(String memberEmail) {
		return memberRepository.selectMemberByEmailAndDelN(memberEmail);
	};
	
	public Member selectMemberByNicknameAndDelN(String memberNick) {
		return memberRepository.selectMemberByNicknameAndDelN(memberNick);
	};
}
