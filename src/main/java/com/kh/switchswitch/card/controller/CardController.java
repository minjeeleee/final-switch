package com.kh.switchswitch.card.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.kh.switchswitch.card.model.dto.Card;
import com.kh.switchswitch.card.model.service.CardService;
import com.kh.switchswitch.exchange.model.dto.ExchangeStatus;
import com.kh.switchswitch.exchange.model.service.ExchangeService;
import com.kh.switchswitch.member.model.dto.MemberAccount;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("card")
@RequiredArgsConstructor
public class CardController {
	
	private final CardService cardService;
	private final ExchangeService exchangeService;

	@GetMapping("card-form")
	public void cardForm() {}
	
	@PostMapping("card-form")
	public String createCard(@RequestParam(required = false) List<MultipartFile> imgList
			//,@AuthenticationPrincipal MemberAccount member
			, Card card

			) {
		System.out.println(card.toString());
		card.setMemberIdx(1);
		cardService.insertCard(imgList, card); 

		return "redirect:/";
	}
	
	@GetMapping("cardExStatus")
	public void cardExStatus(@AuthenticationPrincipal MemberAccount certifiedMember
			, Model model) {
		model.addAttribute("myRate", exchangeService.selectMyRate(certifiedMember.getMemberIdx()));
		
		model.addAttribute("ongoingCardList", cardService.selectOngoingCardList(certifiedMember.getMemberIdx()));
		
		model.addAttribute("requestCardList", cardService.selectRequestCardList(certifiedMember.getMemberIdx()));
		
		model.addAttribute("requestedCardList", cardService.selectRequestedCardList(certifiedMember.getMemberIdx()));
		
	}
	
}
