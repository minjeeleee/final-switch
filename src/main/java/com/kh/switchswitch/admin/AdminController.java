package com.kh.switchswitch.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class AdminController {
	
	@GetMapping("main")
	public void main() {}
	
	@GetMapping("real-time-cards")
	public void realTimeCards() {}
	
	@GetMapping("all-cards")
	public void allCards() {}
	
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
	public void pageSetting() {}
}
