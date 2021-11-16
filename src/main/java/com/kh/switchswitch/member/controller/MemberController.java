package com.kh.switchswitch.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("member")
public class MemberController {
	
	@GetMapping("login")
	public void login() {}
	
	@GetMapping("join")
	public void joinForm() {}
	
	@GetMapping("consentForm")
	public void consentForm() {}
	
	@GetMapping("findingId")
	public void findingId() {}
	
	@GetMapping("findingPw")
	public void findingPw() {}
}
