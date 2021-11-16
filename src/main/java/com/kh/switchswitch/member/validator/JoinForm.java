package com.kh.switchswitch.member.validator;


import com.kh.switchswitch.member.model.dto.Member;

import lombok.Data;

@Data
public class JoinForm {
	
	private String email;
	private String password;
	private String tell;
	private String nickname;
	private String zipNo;
	private String address;
	private String detailAddress;
	
	public Member convertToMember() {
		Member member = new Member();
		member.setMemberEmail(email);
		member.setMemberPass(password);
		member.setMemberTell(tell);
		member.setMemberNick(nickname);
		member.setMemberAddress("[" + zipNo + "] " + address + ", " + detailAddress);		
		return member;
	}
	
	
	
}
