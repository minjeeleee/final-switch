package com.kh.switchswitch.admin.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.switchswitch.admin.model.dto.Code;
import com.kh.switchswitch.admin.model.dto.Menu;
import com.kh.switchswitch.admin.model.repository.AdminRepository;
import com.kh.switchswitch.card.model.dto.Card;
import com.kh.switchswitch.common.util.FileDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
	
	@Autowired
	private final AdminRepository adminRepository;

	public Map<String,Object> selectRealTimeCards() {
		List<Card> cardList = adminRepository.selectRealTimeCards();
		List<FileDTO> cardImgList = adminRepository.selectCardImgList();
		return Map.of("cardList",cardList,"cardImgList",cardImgList);
	}
	
	public int deleteCard(int cardIdx) {
		return adminRepository.deleteCard(cardIdx);
	}
	
	public void insertMenu(Menu menu) {
		adminRepository.insertMenu(menu);
	}

	public List<Menu> selectMenuList() {
		List<Menu> menuList = adminRepository.selectMenuList();
		return menuList;
	}
	
	public List<Code> selectCodeList(){
		List<Code> codeList = adminRepository.selectCodeList();
		return codeList;
	}
	
	public List<Menu> selectParentsMenuListByMenu() {
		List<Menu> parentsMenuList = adminRepository.selectParentsMenuListByMenu();
		return parentsMenuList;
	}
	
	public List<Menu> selectParentsMenuListBySideMenu(){
		List<Menu> parentsMenuList = adminRepository.selectParentsMenuListBySideMenu();
		return parentsMenuList;
	}
	
	public List<Menu> selectSubMenu() {
		return adminRepository.selectSubMenu();
	}

	public void deleteMenu(int urlIdx) {
		adminRepository.deleteMenu(urlIdx);
	}
	

	

	

	

}
