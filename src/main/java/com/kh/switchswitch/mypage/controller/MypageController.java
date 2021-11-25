package com.kh.switchswitch.mypage.controller;

import java.util.List;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.switchswitch.common.validator.ValidatorResult;
import com.kh.switchswitch.member.model.dto.Member;
import com.kh.switchswitch.member.model.dto.MemberAccount;
import com.kh.switchswitch.member.model.service.MemberService;
import com.kh.switchswitch.mypage.validator.ModifyForm;
import com.kh.switchswitch.mypage.validator.ModifyFormValidator;

@Controller
@RequestMapping("mypage")
public class MypageController {
	
	private MemberService memberService;
	private ModifyFormValidator modifyFormValidator;
	private PasswordEncoder passwordEncoder;


	public MypageController(MemberService memberService, ModifyFormValidator modifyFormValidator,
			PasswordEncoder passwordEncoder) {
		super();
		this.memberService = memberService;
		this.modifyFormValidator = modifyFormValidator;
		this.passwordEncoder = passwordEncoder;
	}


	@InitBinder(value = "modifyForm")
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(modifyFormValidator);
	}


	@GetMapping("profile")
	public void profile(@AuthenticationPrincipal MemberAccount member,Model model) {
		model.addAttribute("profileImage", memberService.selectFileInfoByFlIdx(member.getMember().getFlIdx()));
		model.addAttribute(new ModifyForm()).addAttribute("error", new ValidatorResult().getError());
	}
	
	@PostMapping("profile")
	public String profileModify(@Validated ModifyForm form
							,Errors errors
							,@RequestParam(required = false) MultipartFile profileImage
							,Model model
							,HttpSession session
							,RedirectAttributes redirectAttr
			) {
		
		ValidatorResult vr = new ValidatorResult();
		model.addAttribute("error", vr.getError());

		System.out.println(profileImage);
		if(errors.hasErrors()) {
			vr.addErrors(errors);
			return "mypage/profile";
		}

		memberService.updateMemberWithFile(form.convertToMember(),profileImage);
		return "redirect:/mypage/profile";
	}

	@GetMapping("leave-member")
	public void leaveMember() {}
	
	@PostMapping("leave-member")
	public String leaveMemberImpl(@AuthenticationPrincipal MemberAccount member
									 ,String password 
									,RedirectAttributes redirectAttr
									,Model model
								){

		System.out.println("비밀번호 : "+password);
		
		if(!passwordEncoder.matches(password,member.getMemberPass())) {
			model.addAttribute("message","비밀번호가 틀렸습니다"); 
			return "mypage/leave-member"; 
		}
		
		member.getMember().setMemberDelYn(1);
		memberService.updateMemberDelYN(member.getMember());
		model.addAttribute("msg", "회원탈퇴가 완료되었습니다");
		return "redirect:/";
	}
	
	@GetMapping("pw-check")
	@ResponseBody
	public String pwCheck(@AuthenticationPrincipal MemberAccount member,String password) {
		
		if(passwordEncoder.matches(password,member.getMemberPass())) {
			return "available";
		}else {
			return "disable";
		}
	}
	
	@GetMapping("nick-check")
	@ResponseBody
	public String nickCheck(@AuthenticationPrincipal MemberAccount member,String nickName) {
		
		if(nickName.equals(member.getMemberNick()) || memberService.checkNickName(nickName)) {
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
