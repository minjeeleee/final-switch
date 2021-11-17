package com.kh.switchswitch.admin;

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
		//model.addAllAttributes(adminService.selectRealTimeCards());
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
		model.addAttribute("menu",adminService.selectMenuList());
		System.out.println(model);
	}
	
	@PostMapping("add-page")
	public String addPage(Menu menu) {
		System.out.println(menu);
		adminService.insertMenu(menu);
		return "redirect:/admin/page-setting";
	}
}
