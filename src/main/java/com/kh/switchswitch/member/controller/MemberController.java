package com.kh.switchswitch.member.controller;

import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.switchswitch.common.code.ErrorCode;
import com.kh.switchswitch.common.exception.HandlableException;
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
	@InitBinder(value="joinForm")
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(joinFormValidator);
	}
	
	@GetMapping("login")
	public void login() {}
	
	@GetMapping("email-check")
	@ResponseBody
	public String emailCheck(String memberEmail) {
		if(memberService.selectMemberByEmailAndDelN(memberEmail) == null) {
			return "available";
		}else {
			return "disable";
		}
	}
	
	@GetMapping("nickname-check")
	@ResponseBody
	public String nicknameCheck(String memberNick) {
		if(memberService.selectMemberByNicknameAndDelN(memberNick) == null) {
			return "available";
		}else {
			return "disable";
		}
	}
	
	@GetMapping("addrPopup")
	public void addrPopupG(String inputYn, Model model) {
		
		String confmKey = "devU01TX0FVVEgyMDIxMTExODAxMTE0MDExMTkwMDg=";
		 
		model.addAttribute("confmKey", confmKey); 
		model.addAttribute("inputYn", inputYn);
	}
	
	@PostMapping("addrPopup")
	public void addrPopupP(String inputYn, String roadFullAddr, String zipNo, Model model) {
		
		model.addAttribute("inputYn", inputYn); 
		model.addAttribute("roadFullAddr", roadFullAddr); 
		model.addAttribute("zipNo", zipNo);
	}
	
	@GetMapping("join")
	public void joinForm(Model model) {
		model.addAttribute(new JoinForm())
		.addAttribute("error",new ValidatorResult().getError());
	}
	
	@PostMapping("join")
	public String join(@Validated JoinForm form
					, Errors errors //Errors 객체는 반드시 검증할 객체의 바로 뒤에 작성
					, Model model
					, HttpSession session) {
		
		ValidatorResult vr = new ValidatorResult();
		model.addAttribute("error", vr.getError());
		
		if(errors.hasErrors()) {
			vr.addErrors(errors);
			return "member/join";
		}
		
		String token = UUID.randomUUID().toString();
		session.setAttribute("persistUser", form);
		session.setAttribute("persistToken", token);
		
		memberService.authenticateByEmail(form,token);
		return "redirect:/";
	}
	
	@GetMapping("join-impl/{token}")
	public String joinImpl(@PathVariable String token
			, @SessionAttribute(value = "persistToken", required = false) String persistToken
			, @SessionAttribute(value = "persistUser", required = false) JoinForm form
			, HttpSession session
			, RedirectAttributes redirectAttrs) {
		
		if(!token.equals(persistToken)) {
			throw new HandlableException(ErrorCode.AUTHENTICATION_FAILED_ERROR);
		}
		
		memberService.insertMember(form);
		redirectAttrs.addFlashAttribute("message", "회원가입을 환영합니다. 로그인 해주세요");
		session.removeAttribute("persistToken");
		session.removeAttribute("persistUser");
		return "redirect:/member/login";
		
	}
	
	@GetMapping("consentForm")
	public void consentForm() {}
	
	@GetMapping("findingId")
	public void findingId() {}
	
	@GetMapping("findingPw")
	public void findingPw() {}
}
