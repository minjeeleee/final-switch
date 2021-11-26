package com.kh.switchswitch.exchange.controller;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.switchswitch.alarm.model.dto.Alarm;
import com.kh.switchswitch.alarm.model.service.AlarmService;
import com.kh.switchswitch.card.model.dto.Card;
import com.kh.switchswitch.card.model.dto.CardRequestList;
import com.kh.switchswitch.card.model.service.CardService;
import com.kh.switchswitch.common.code.ErrorCode;
import com.kh.switchswitch.common.exception.HandlableException;
import com.kh.switchswitch.common.util.FileDTO;
import com.kh.switchswitch.exchange.model.service.ExchangeService;
import com.kh.switchswitch.member.model.dto.MemberAccount;
import com.kh.switchswitch.member.model.service.MemberService;
import com.kh.switchswitch.point.model.dto.SavePoint;
import com.kh.switchswitch.point.model.service.PointService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("exchange")
@RequiredArgsConstructor
public class ExchangeController {
	
	Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	private final ExchangeService exchangeService;
	private final PointService pointService;
	private final CardService cardService;
	private final AlarmService alarmService;
	private final MemberService memberService;
	
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
		
		model.addAttribute("cardlist", cardlist);
		model.addAttribute("myRate",myRate);
		
		//교환 희망 카드
		Card cardInfo = exchangeService.selectCardByCardIdx(wishCardIdx);
		FileDTO fileDTO = exchangeService.selectImgFileByCardIdx(wishCardIdx);
		float userRate = exchangeService.selectMyRate(cardInfo.getMemberIdx());
		
		model.addAttribute("userRate", userRate);
		model.addAttribute("wishCard", Map.of("cardInfo", cardInfo, "fileDTO", fileDTO));
		
		//포인트 잔액
		SavePoint savePoint = exchangeService.selectSavePointByMemberIdx(certifiedMember.getMemberIdx());
		if(savePoint != null) {
			model.addAttribute("availableBal", savePoint.getAvailableBal());
			model.addAttribute("balance", savePoint.getBalance());
		}
		
	}
	
	@PostMapping("exchangeForm")
	public String exchangeForm(
			@AuthenticationPrincipal MemberAccount certifiedMember
			, int wishCardIdx
			, String offerPoint
			, int availableBal
			, @RequestParam(required = false)  String[] cardIdxList
			, Model model) {
		//교환요청리스트
		CardRequestList cardRequestList = new CardRequestList();
		cardRequestList.setRequestedCard(wishCardIdx);
		if(cardIdxList != null) {
			switch(5-cardIdxList.length) {
			case 1 : cardRequestList.setRequestCard4(Integer.valueOf(cardIdxList[3]));
			case 2 : cardRequestList.setRequestCard3(Integer.valueOf(cardIdxList[2])); 
			case 3 : cardRequestList.setRequestCard2(Integer.valueOf(cardIdxList[1]));
			case 4 : cardRequestList.setRequestCard1(Integer.valueOf(cardIdxList[0])); break;
			default : logger.debug("왜 0이 들어오지??");
			}
		}
		cardRequestList.setRequestedMemIdx(cardService.selectCardMemberIdxWithCardIdx(wishCardIdx));
		cardRequestList.setRequestMemIdx(certifiedMember.getMemberIdx());
		Integer offerPointInt = Integer.parseInt(offerPoint);
		cardRequestList.setPropBalance(offerPointInt);
		exchangeService.requestExchange(cardRequestList, cardIdxList.length);
		
		//포인트 holding ?? 후 가용 포인트
		pointService.updateSavePointWithAvailableBal(availableBal - offerPointInt, certifiedMember.getMemberIdx());
		
		return "redirect:/";
	}
	
	@GetMapping("detail")
	public void detail(
			@AuthenticationPrincipal MemberAccount certifiedMember,
			//Alarm alarm, 
			Model model) {
		Alarm alarm = new Alarm();
		alarm.setAlarmIdx(1);
		alarm.setReqIdx(450);
		
		//알림 테이블 is_read 업데이트
		alarmService.updateAlarm(alarm);
		
		CardRequestList cardRequestList = cardService.selectCardRequestListWithReqIdx(alarm.getReqIdx());
		if(cardRequestList == null) {
			throw new HandlableException(ErrorCode.FAILED_TO_LOAD_INFO);
		}
		
		Card card = cardService.selectCardWithCardIdx(cardRequestList.getRequestedCard());
		FileDTO fileDTO = exchangeService.selectImgFileByCardIdx(card.getCardIdx());
		
		//요청 유저 카드 리스트
		List<Map<String,Object>> cardList = new ArrayList<Map<String,Object>>();
		Set<Integer> cardIdxSet = new LinkedHashSet<Integer>();
		cardIdxSet.add(cardRequestList.getRequestCard1());
		cardIdxSet.add(cardRequestList.getRequestCard2());
		cardIdxSet.add(cardRequestList.getRequestCard3());
		cardIdxSet.add(cardRequestList.getRequestCard4());
		cardIdxSet.remove(null);
		for (Integer cardIdx : cardIdxSet) {
			Card reqCard = cardService.selectCardWithCardIdx(cardIdx);
			FileDTO reqfileDTO = exchangeService.selectImgFileByCardIdx(reqCard.getCardIdx());
			cardList.add(Map.of("card",reqCard,"fileDTO",reqfileDTO));
		}
		
		//요청받은 유저 카드
		model.addAttribute("requestedCard", Map.of("card",card, "fileDTO", fileDTO));
		//요청받은 유저 평점
		model.addAttribute("requestedMemRate",exchangeService.selectMyRate(cardRequestList.getRequestedMemIdx()));
		
		//상대방 닉네임
		if(certifiedMember.getMemberIdx().equals(cardRequestList.getRequestMemIdx())) {
			model.addAttribute("counterpartNick",memberService.selectMemberNickWithMemberIdx(cardRequestList.getRequestedMemIdx()));
		} else {
			model.addAttribute("counterpartNick",memberService.selectMemberNickWithMemberIdx(cardRequestList.getRequestMemIdx()));
		}
		
		//요청 유저 평점
		model.addAttribute("reqMemRate", exchangeService.selectMyRate(cardRequestList.getRequestMemIdx()));
		
		//요청 유저 제시 포인트
		model.addAttribute("propBalance",cardRequestList.getPropBalance());
		model.addAttribute("reqCardList", cardList);
	}

}
