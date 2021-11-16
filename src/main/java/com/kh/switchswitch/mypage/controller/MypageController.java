package com.kh.switchswitch.mypage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("mypage")
public class MypageController {
	
	@GetMapping("profile")
	public void profile() {}

	@GetMapping("chatting")
	public void chatting() {}
	
	@GetMapping("history")
	public void history() {}
	
	@GetMapping("leave-member")
	public void leaveMember() {}
	
	@PostMapping("leave-member")
	public String leaveMemberImpl(){
		
		return "redirect:/";
	}
	
	@GetMapping("mypage-inquiry")
	public void mypageInquiry() {}
}
