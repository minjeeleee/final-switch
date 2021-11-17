package com.kh.switchswitch.admin.model.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.kh.switchswitch.admin.model.dto.Menu;
import com.kh.switchswitch.admin.model.repository.AdminRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
	
	private final AdminRepository adminRepository;

	public Map<String,Object> selectRealTimeCards() {
		Map<String,Object> commandMap = adminRepository.selectRealTimeCards();
		return commandMap;
	}
	
	public int deleteCard(int cardIdx) {
		return adminRepository.deleteCard(cardIdx);
	}
	
	public void insertMenu(Menu menu) {
		adminRepository.insertMenu(menu);
	}

}
