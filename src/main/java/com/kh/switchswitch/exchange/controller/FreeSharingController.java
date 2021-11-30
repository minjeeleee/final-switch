package com.kh.switchswitch.exchange.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kh.switchswitch.card.model.dto.FreeRequestList;
import com.kh.switchswitch.exchange.model.service.ExchangeService;
import com.kh.switchswitch.member.model.dto.MemberAccount;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("freeSharing")
@RequiredArgsConstructor
public class FreeSharingController {

	private final ExchangeService exchangeService;

	//무료나눔 신청
	@GetMapping("request/{cardIdx}")
	public String freeSharingRequest(@AuthenticationPrincipal MemberAccount member,@PathVariable Integer cardIdx) {
		//신청하면 freeRequestList에 추가
		exchangeService.requestFreeSharing(member.getMemberIdx(),cardIdx);
		return "redirect:/";
	}
	
	//무료나눔 신청 거절
	@GetMapping("reject/{freqIdx}")
	public void reject() {
		//freeRequestList에서 삭제
	}
	
	//무료나눔 신청 취소
	@GetMapping("request-cancel/{freqIdx}")
	public void requestCancle() {
		//freeRequestList에서 삭제
	}
	
	//무료나눔 신청 수락
	@GetMapping("accept/{freqIdx}")
	public void accept() {
		//카드 상태 Ongoing으로 업데이트
		//exchangeStatus에 추가
	}
	
	
	//무료나눔 완료
	@GetMapping("complete/{freqIdx}")
	public void complete() {
		//카드 상태 Done으로 업데이트
		//exchangeStatus type Done
		//exchangeHistory에 추가
	}
}
