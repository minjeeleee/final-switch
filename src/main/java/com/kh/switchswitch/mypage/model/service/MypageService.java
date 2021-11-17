package com.kh.switchswitch.mypage.model.service;

import com.kh.switchswitch.member.model.dto.Member;

public interface MypageService {

	void leaveId(String email);

	boolean checkNickName(String nickName);

	int checkPwForLeave(Member certifiedUser, String password);
	
}
