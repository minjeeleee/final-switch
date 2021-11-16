package com.kh.switchswitch.mypage.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.switchswitch.member.model.dto.Member;
import com.kh.switchswitch.member.model.service.MemberService;
import com.kh.switchswitch.mypage.model.service.MypageService;
import com.kh.switchswitch.mypage.model.service.MypageServiceImpl;

@Controller
@RequestMapping("mypage")
public class MypageController {
	
	Logger logger =  LoggerFactory.getLogger(this.getClass());

	private MypageServiceImpl mypageServiceImpl;
	
	@GetMapping("profile")
	public void profile() {}

	@GetMapping("leave-member")
	public void leaveMember() {}
	
	@PostMapping("leave-member")
	public String leaveMemberImpl(
			/*
			 * @SessionAttribute(name = "authentication") Member certifiedUser ,
			 */
			@RequestParam(required = false) String password 
			/* ,RedirectAttributes redirectAttr */
									,Model model
								){

									/*
									 * if(password == null) {
									 * redirectAttr.addFlashAttribute("message","비밀번호가 입력되지 않았습니다"); return
									 * "redirect:/member/leave-member"; }
									 * 
									 * if(mypageServiceImpl.checkPwForLeave(certifiedUser, password) == 0) {
									 * redirectAttr.addFlashAttribute("message","비밀번호가 틀렸습니다"); return
									 * "redirect:/member/leave-member"; }
									 * 
									 * model.addAttribute("msg", "회원탈퇴가 완료되었습니다");
									 */
		logger.debug(password);
		return "redirect:/";
	}
	

	@GetMapping("chatting")
	public void chatting() {}
	
	@GetMapping("history")
	public void history() {}
	
	@GetMapping("mypage-inquiry")
	public void mypageInquiry() {}
}
