package com.kh.switchswitch.exchange.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kh.switchswitch.card.model.dto.Card;
import com.kh.switchswitch.common.util.FileDTO;
import com.kh.switchswitch.exchange.model.dto.ExchangeStatus;
import com.kh.switchswitch.exchange.model.service.ExchangeService;
import com.kh.switchswitch.member.model.dto.MemberAccount;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("exchange")
@RequiredArgsConstructor
public class ExchangeController {
	
	private final ExchangeService exchangeService;
	
	@GetMapping("exchangeForm")
	public void exchangeForm(
			@AuthenticationPrincipal MemberAccount certifiedMember
			//, int wishCardIdx
			, Model model) {
		//내카드 리스트
		List<Map<String,Object>> cardlist = new ArrayList<>();
		List<Card> myCardList = exchangeService.selecAvailableMyCardList(certifiedMember.getMemberIdx());
		int wishCardIdx = 393;
		
		if(myCardList != null) {
			for (Card card : myCardList) {
				FileDTO fileDTO = exchangeService.selectImgFileByCardIdx(card.getCardIdx());
				cardlist.add(Map.of("card", card, "fileDTO", fileDTO));
			}
		}
		float myRate = exchangeService.selectMyRate(certifiedMember.getMemberIdx());
		
		
		for (Map map : cardlist) {
			System.out.println(((Card)map.get("card")).toString());
		}
		
		model.addAttribute("cardlist", cardlist);
		model.addAttribute("myRate",myRate);
		
		//교환 희망 카드
		Card cardInfo = exchangeService.selectCardByCardIdx(wishCardIdx);
		FileDTO fileDTO = exchangeService.selectImgFileByCardIdx(wishCardIdx);
		float userRate = exchangeService.selectMyRate(cardInfo.getMemberIdx());
		
		model.addAttribute("userRate", userRate);
		model.addAttribute("wishCard", Map.of("cardInfo", cardInfo, "fileDTO", fileDTO));
		
		//포인트 잔액
		int balance = exchangeService.selectBalanceByMemberIdx(certifiedMember.getMemberIdx());
		
		model.addAttribute("balance", balance);
		
	}
	
	@PostMapping("exchangeForm")
	public String exchangeForm(
			@AuthenticationPrincipal MemberAccount certifiedMember
			, int wishCardIdx
			, int offerPoint
			, Model model) {
		ExchangeStatus exchangeStatus = new ExchangeStatus();
		Card wishCard = exchangeService.selectCardByCardIdx(wishCardIdx);
		//교환신청 받은 사람
		exchangeStatus.setUserIdx2(wishCard.getMemberIdx());
		//교환신청자
		exchangeStatus.setUserIdx1(certifiedMember.getMemberIdx());
		
		exchangeService.insertExchangeStatus(exchangeStatus);
		
		//포인트 잔액
		int balance = exchangeService.selectBalanceByMemberIdx(certifiedMember.getMemberIdx());
		
		//포인트 holding ?? 후 가용 포인트
		int afterBalance = balance - offerPoint;
		System.out.println(balance);
		System.out.println(afterBalance);
		return "redirect:/";
	}

}
