package com.kh.switchswitch.member.model.service;

import com.kh.switchswitch.member.model.dto.KakaoLogin;
import com.kh.switchswitch.member.model.dto.Member;
import com.kh.switchswitch.member.validator.JoinForm;

public interface MemberService {
	
	void insertMember(JoinForm form);

	void authenticateByEmail(JoinForm form, String token);
	
	Member selectMemberByEmailAndDelN(String memberEmail);

	Member selectMemberByNicknameAndDelN(String memberNick);

	KakaoLogin selectKakaoLoginById(String id);
	
	void insertMemberWithKakao(Member member, String id);

	void updateMemberDelYN(Member member);
	
}