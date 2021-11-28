package com.kh.switchswitch.mypage.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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

import com.kh.switchswitch.card.model.dto.Card;
import com.kh.switchswitch.card.model.dto.CardRequestList;
import com.kh.switchswitch.card.model.service.CardService;
import com.kh.switchswitch.common.code.ErrorCode;
import com.kh.switchswitch.common.exception.HandlableException;
import com.kh.switchswitch.common.validator.ValidatorResult;
import com.kh.switchswitch.exchange.model.dto.ExchangeHistory;
import com.kh.switchswitch.exchange.model.dto.ExchangeStatus;
import com.kh.switchswitch.exchange.model.service.ExchangeService;
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
	private ExchangeService exchangeService;
	private CardService cardService;


	public MypageController(MemberService memberService, ModifyFormValidator modifyFormValidator,
			PasswordEncoder passwordEncoder,ExchangeService exchangeService,CardService cardService) {
		super();
		this.memberService = memberService;
		this.modifyFormValidator = modifyFormValidator;
		this.passwordEncoder = passwordEncoder;
		this.exchangeService = exchangeService;
		this.cardService = cardService;
	}


	@InitBinder(value = "modifyForm")
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(modifyFormValidator);
	}


	@GetMapping("profile")
	public void profile(@AuthenticationPrincipal MemberAccount member,Model model) {
		float myRate = exchangeService.selectMyRate(member.getMemberIdx());
		int myRateCnt = exchangeService.selectMyRateCnt(member.getMemberIdx()).size();
		
		List<Integer> totalMyRate = exchangeService.selectMyRateCnt(member.getMemberIdx());
		model.addAttribute("myRate", Map.of("score",Math.ceil(myRate*10)/10,"cnt",myRateCnt));
		model.addAttribute("rateList"
							,Map.of("one",Math.round((double)Collections.frequency(totalMyRate, 1)/(double)myRateCnt*100)
									,"two",Math.round((double)Collections.frequency(totalMyRate, 2)/(double)myRateCnt*100)
									,"three",Math.round((double)Collections.frequency(totalMyRate, 3)/(double)myRateCnt*100)
									,"four",Math.round((double)Collections.frequency(totalMyRate, 4)/(double)myRateCnt*100)
									,"five",Math.round((double)Collections.frequency(totalMyRate, 5)/(double)myRateCnt*100)));
		if(member.getFlIdx() != null) {
			model.addAttribute("profileImage", memberService.selectFileInfoByFlIdx(member.getFlIdx()));
		}
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

		memberService.updateMemberDelYN(form.convertToMember());

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
		
		System.out.println(exchangeService.checkExchangeOngoing(member.getMemberIdx()));
		if(exchangeService.checkExchangeOngoing(member.getMemberIdx())) {
			throw new HandlableException(ErrorCode.FAILED_TO_LEAVE_MEMBER);
 		}
		
		member.getMember().setMemberDelYn(1);
		memberService.updateMemberDelYNForLeave(member.getMember());
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
	public void history(@AuthenticationPrincipal MemberAccount member,Model model) {
		//내 별점 구하기
		model.addAttribute("myRate", exchangeService.selectMyRate(member.getMemberIdx()));
		//거래 완료된 카드들
		model.addAttribute("doneCardList",cardService.selectDoneCardList(member.getMemberIdx()));
		//교환내역 찾기
		model.addAttribute("ehList", exchangeService.selectExchangeHistoryByMemIdx(member.getMemberIdx()));
	}
	
	@GetMapping("mypage-inquiry")
	public void mypageInquiry() {}
}
