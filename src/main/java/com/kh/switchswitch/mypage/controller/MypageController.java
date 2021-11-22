package com.kh.switchswitch.mypage.controller;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.switchswitch.common.validator.ValidatorResult;
import com.kh.switchswitch.member.model.dto.Member;
import com.kh.switchswitch.member.model.service.MemberService;
import com.kh.switchswitch.mypage.model.service.MypageService;
import com.kh.switchswitch.mypage.validator.ModifyForm;
import com.kh.switchswitch.mypage.validator.ModifyFormValidator;

@Controller
@RequestMapping("mypage")
public class MypageController {
	
	private MypageService mypageService;
	private MemberService memberService;
	private ModifyFormValidator modifyFormValidator;
	private PasswordEncoder passwordEncoder;


	public MypageController(MypageService mypageService, MemberService memberService, ModifyFormValidator modifyFormValidator,
			PasswordEncoder passwordEncoder) {
		super();
		this.mypageService = mypageService;
		this.memberService = memberService;
		this.modifyFormValidator = modifyFormValidator;
		this.passwordEncoder = passwordEncoder;
	}


	@InitBinder(value = "modifyForm")
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(modifyFormValidator);
	}


	@GetMapping("profile")
	public void profile(Model model) {
		model.addAttribute(new ModifyForm()).addAttribute("error", new ValidatorResult().getError());
	}
	
	@PostMapping("modify")
	public String modify(@Validated ModifyForm form
							,Errors errors
							,Model model
							,HttpSession session
							,RedirectAttributes redirectAttr
			) {
		ValidatorResult vr = new ValidatorResult();
		model.addAttribute("error", vr.getError());
		if(errors.hasErrors()) {
			vr.addErrors(errors);
			return "mypage/profile";
		}
		return "redirect:/mypage/profile";
	}

	@GetMapping("leave-member")
	public void leaveMember() {}
	
	@PostMapping("leave-member")
	public String leaveMemberImpl(@AuthenticationPrincipal Member certifiedUser
									,@RequestParam(required = false,name = "password") String password 
									,RedirectAttributes redirectAttr
									,Model model
								){

		System.out.println("비밀번호 : "+password);
		if(password.equals("")) {
			redirectAttr.addFlashAttribute("message","비밀번호가 입력되지 않았습니다");
			return "redirect:/mypage/leave-member";
		}
		
		
		if(mypageService.checkPwForLeave(certifiedUser, password) == 0) {
			redirectAttr.addFlashAttribute("message","비밀번호가 틀렸습니다"); return
			"redirect:/member/leave-member"; 
		}
		 
		
		model.addAttribute("msg", "회원탈퇴가 완료되었습니다");
		return "redirect:/";
	}
	
	@GetMapping("pw-check")
	@ResponseBody
	public String pwCheck(String password) {
		
		Member member = memberService.selectMemberByEmailAndDelN("projectteamyong@gmail.com");

		System.out.println(password);
		if(passwordEncoder.matches(password, member.getMemberPass())) {
			return "available";
		}else {
			return "disable";
		}
	}
	
	@GetMapping("nick-check")
	@ResponseBody
	public String nickCheck(String nickName) {
		
		System.out.println(nickName);
		Member member = memberService.selectMemberByEmailAndDelN("projectteamyong@gmail.com");
		
		if(nickName.equals(member.getMemberNick()) || mypageService.checkNickName(nickName)) {
			return "available";
		}else {
			return "disable";
		}
	}
	

	@GetMapping("chatting")
	public void chatting() {}
	
	@GetMapping("history")
	public void history() {}
	
	@GetMapping("mypage-inquiry")
	public void mypageInquiry() {}
}
