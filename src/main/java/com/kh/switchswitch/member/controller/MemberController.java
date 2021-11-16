package com.kh.switchswitch.member.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kh.switchswitch.common.validator.ValidatorResult;
import com.kh.switchswitch.member.model.service.MemberService;
import com.kh.switchswitch.member.validator.JoinForm;
import com.kh.switchswitch.member.validator.JoinFormValidator;

@Controller
@RequestMapping("member")
public class MemberController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private MemberService memberService;
	private JoinFormValidator joinFormValidator;
	
	public MemberController(MemberService memberService, JoinFormValidator joinFormValidator) {
		super();
		this.memberService = memberService;
		this.joinFormValidator = joinFormValidator;
	}
	
	//model의 속성 중 속성명이 joinForm인 속성이 있는 경우에
	//WebDataBinder의 속성을 초기화
	//@InitBinder(value="joinForm")
	//public void initBinder(WebDataBinder webDataBinder) {
	//	webDataBinder.addValidators(joinFormValidator);
	//}
	
	@GetMapping("login")
	public void login() {}
	
	@GetMapping("join")
	public void joinForm(Model model) {
		model.addAttribute(new JoinForm()).addAttribute("error",new ValidatorResult().getError());
	}
	
	@PostMapping("join")
	public void join(@Validated JoinForm form
					, Errors errors //Error
					, Model model) {
		model.addAttribute(new JoinForm()).addAttribute("error",new ValidatorResult().getError());
	}
	
	@GetMapping("consentForm")
	public void consentForm() {}
	
	@GetMapping("findingId")
	public void findingId() {}
	
	@GetMapping("findingPw")
	public void findingPw() {}
}
