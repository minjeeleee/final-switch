package com.kh.switchswitch.mypage.model.service;

import org.springframework.stereotype.Service;

import com.kh.switchswitch.mypage.model.repository.MypageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MypageServiceImpl implements MypageService{

	private final MypageRepository mypageRepository;

	@Override
	public void leaveId(String email) {
		// TODO Auto-generated method stub
		
	}
}
