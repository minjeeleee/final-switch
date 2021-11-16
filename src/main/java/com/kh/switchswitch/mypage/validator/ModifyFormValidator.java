package com.kh.switchswitch.mypage.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.kh.switchswitch.member.model.repository.MemberRepository;

public class ModifyFormValidator implements Validator{

	private final MemberRepository memberRepository;
	
	public ModifyFormValidator(MemberRepository memberRepository) {
		super();
		this.memberRepository = memberRepository;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(ModifyForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		
	}
	
}
