package com.kh.switchswitch.mypage.validator;

import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.kh.switchswitch.member.model.dto.MemberAccount;
import com.kh.switchswitch.member.model.repository.MemberRepository;

public class ModifyFormValidator implements Validator{

	private final MemberRepository memberRepository;
	private MemberAccount member;
	
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

		ModifyForm form = (ModifyForm) target;
		
		if(memberRepository.selectMemberByNickName(form.getMemberNick()) != null || !member.getMemberNick().equals(form.getMemberNick())) {
			errors.rejectValue("nickName", "err-nickName", "이미 존재하는 닉네임 입니다.");
		}else if(form.getMemberNick().equals("")) {
			errors.rejectValue("nickName", "err-nickName", "올바르게 닉네임을 작성해주세요.");
		}
		
		if(!Pattern.matches("(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[^a-zA-Zㄱ-힣0-9]).{8,}", form.getNewMemberPass())) {
			errors.rejectValue("new-password", "err-new-password", "비밀번호는 숫자 영문자 특수문자 조합인 8글자 이상의 문자열입니다.");
		}
		
		if(!form.getNewMemberPass().equals(form.getCheckMemberPss())) {
			errors.rejectValue("check-password", "err-check-password", "비밀번호가 일치하지 않습니다.");

		}
		
		if(!Pattern.matches("\\d{9,11}", form.getMemberTell())) {
			errors.rejectValue("tell", "err-tell", "전화번호는 9~11자리의 숫자입니다.");
		}
	}
	
}
