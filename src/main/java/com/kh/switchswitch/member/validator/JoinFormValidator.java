package com.kh.switchswitch.member.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.kh.switchswitch.member.model.repository.MemberRepository;

@Component
public class JoinFormValidator implements Validator {
	
	private final MemberRepository memberRepository;
	
	public JoinFormValidator(MemberRepository memberRepository) {
		super();
		this.memberRepository = memberRepository;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(JoinForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		JoinForm form = (JoinForm) target;
		
		//1. 아이디 존재 유무
		if(memberRepository.selectMemberByEmail(form.getEmail()) != null) {
			errors.rejectValue("email", "err-email", "이미 존재하는 이메일 입니다.");
		}
		
		
	}
	
	
	
}
