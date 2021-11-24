package com.kh.switchswitch.exchange.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kh.switchswitch.card.model.dto.Card;
import com.kh.switchswitch.common.util.FileDTO;
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
			, Model model) {
		
		List<List> cardlist = new ArrayList<>();
		List<Card> myCardList = exchangeService.selecAvailableMyCardList(certifiedMember.getMemberIdx());
		
		if(myCardList != null) {
			for (Card card : myCardList) {
				FileDTO fileDTO = exchangeService.selectImgFileByCardIdx(card.getCardIdx());
				cardlist.add(List.of(card, fileDTO));
			}
		}
		float myRate = exchangeService.selectMyRate(certifiedMember.getMemberIdx());
		
		for (List card : cardlist) {
			System.out.println(card);
		}
		
		model.addAttribute("cardlist", cardlist);
		model.addAttribute("myRate",myRate);
		
		
	}

}
