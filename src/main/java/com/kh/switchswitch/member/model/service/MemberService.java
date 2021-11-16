package com.kh.switchswitch.member.model.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.kh.switchswitch.member.validator.JoinForm;

public interface MemberService {

	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
	
	void insertMember(JoinForm form);
	
}
