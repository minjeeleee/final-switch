package com.kh.switchswitch.admin.controller;

import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kh.switchswitch.admin.model.dto.Menu;
import com.kh.switchswitch.admin.model.service.AdminService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("admin")
public class AdminController {
	
	private final AdminService adminService;
	
	@GetMapping("main")
	public void main() {}
	
	@GetMapping("real-time-cards")
	public void realTimeCards(Model model) {
		Map<String,Object> cardList = adminService.selectRealTimeCards();
		model.addAttribute("cardList",cardList);
	}
	
	@GetMapping("all-cards")
	public void allCards() {
	}
	
	@RequestMapping("card-delete")
	public String cardDelete(int cardIdx) {
		adminService.deleteCard(cardIdx);
		return "admin/real-time-cards";
	}
	
	@GetMapping("all-members")
	public void allMembers() {}
	
	@GetMapping("black-list-members")
	public void blackListMembers() {}
	
	@GetMapping("refunds-history")
	public void refundsHistory() {}
	
	@GetMapping("member-profile")
	public void memberProfile() {}
	
	@GetMapping("member-profile-edit")
	public void memberProfileEdit() {}
	
	@GetMapping("page-setting")
	public void pageSetting(Model model) {
		model.addAttribute("menuList",adminService.selectMenuList());
		model.addAttribute("parentsMenuList",adminService.selectParentsMenuListByMenu());
		model.addAttribute("parentsSideMenuList",adminService.selectParentsMenuListBySideMenu());
		model.addAttribute("subMenuList",adminService.selectSubMenu());
		model.addAttribute("codeList",adminService.selectCodeList());
	}
	
	@PostMapping("add-menu")
	public String addPage(Menu menu) {
			if(menu.getParent().equals("없음")) {
				menu.setDivision("메인메뉴");
				adminService.insertMenu(menu);
			}else {
				menu.setDivision("서브메뉴");
				adminService.insertMenu(menu);
			}
		return "redirect:/admin/page-setting";
	}
	
	@GetMapping("delete-menu")
	public String deleteMenu(int urlIdx) {
		adminService.deleteMenu(urlIdx);
		return "redirect:/admin/page-setting";
	}
	
}
