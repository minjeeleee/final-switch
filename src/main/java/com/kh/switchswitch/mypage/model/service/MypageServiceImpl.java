package com.kh.switchswitch.mypage.model.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kh.switchswitch.member.model.dto.Member;
import com.kh.switchswitch.member.model.repository.MemberRepository;
import com.kh.switchswitch.mypage.model.repository.MypageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MypageServiceImpl implements MypageService{

	private final MypageRepository mypageRepository;
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public void leaveId(String email) {
		mypageRepository.leaveId(email);
	}
	
	public int checkPwForLeave(Member member, String password) {
		if(passwordEncoder.matches(member.getMemberPass(), password)) {
			leaveId(member.getMemberEmail());
			return 1;
		}
		return 0;
	}
	
	public boolean checkNickName(String nickName) {
		if(memberRepository.selectMemberByNickName(nickName) == null) {
			return true;
		}
		return false;
	}
}
