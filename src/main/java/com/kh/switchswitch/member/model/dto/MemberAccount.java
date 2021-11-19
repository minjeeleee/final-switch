package com.kh.switchswitch.member.model.dto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class MemberAccount extends User {
	
	private static final long serialVersionUID = -771193703920731890L;
	
	private Member member;
	
	public MemberAccount(Member member) {
		super(member.getMemberEmail(),member.getMemberPass(),List.of(new SimpleGrantedAuthority(member.getCode())));
		this.member = member;
	}

	public Member getMember() {
		return member;
	}

	public int getMemberIdx() {
		return member.getMemberIdx();
	}

	public String getCode() {
		return member.getCode();
	}

	public LocalDate getMemberRegDate() {
		return member.getMemberRegDate();
	}

	public String getMemberPass() {
		return member.getMemberPass();
	}

	public String getMemberEmail() {
		return member.getMemberEmail();
	}

	public int getMemberDelYn() {
		return member.getMemberDelYn();
	}

	public String getMemberNick() {
		return member.getMemberNick();
	}

	public String getMemberAddress() {
		return member.getMemberAddress();
	}

	public LocalDate getMemberDelDate() {
		return member.getMemberDelDate();
	}

	public int getMemberScore() {
		return member.getMemberScore();
	}

	public String getMemberName() {
		return member.getMemberName();
	}
	

	
	

}