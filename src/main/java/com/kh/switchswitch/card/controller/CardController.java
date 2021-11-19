package com.kh.switchswitch.card.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.kh.switchswitch.card.model.dto.Card;
import com.kh.switchswitch.card.model.service.CardService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("card")
@RequiredArgsConstructor
public class CardController {
	
	private final CardService cardService;

	@GetMapping("card-form")
	public void cardForm() {}
	
	@PostMapping("card-form")
	public String createCard(@RequestParam(required = false) List<MultipartFile> imgList
			//,@AuthenticationPrincipal MemberAccount member
			, Card card
			) {
		
		card.setMemberIdx(5);
		cardService.insertCard(imgList, card);
		return "redirect:/";
	}
}
