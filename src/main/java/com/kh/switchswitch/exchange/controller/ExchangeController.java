package com.kh.switchswitch.exchange.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
		for (Integer cardIdx : cardService.getCardIdxSet(cardRequestList)) {
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
		
		//cardRequestList
		model.addAttribute("cardRequestList",cardRequestList);
		
		model.addAttribute("status", cardService.selectExchangeStatusType(cardRequestList.getReqIdx()));
	}
	
	@GetMapping("reject/{reqIdx}")
	public void reject(@PathVariable Integer reqIdx) {
		//교환요청리스트
		CardRequestList cardRequestList = cardService.selectCardRequestListWithReqIdx(reqIdx);
		if(cardRequestList == null) {
			throw new HandlableException(ErrorCode.FAILED_TO_LOAD_INFO);
		}
		//card status ->'REQUEST->'NONE'
		cardService.updateCardStatusWithCardIdxSet(cardRequestList,"NONE");
		
		//돈 돌려줘야됨
		pointService.updateSavePoint(cardRequestList);
		
		//교환요청리스트 삭제
		cardService.deleteCardRequestList(cardRequestList.getReqIdx());
		
		//거절 알림 보내기
		alarmService.sendAlarmWithStatus(cardRequestList, "요청거절");
	}
	
	@GetMapping("accept/{reqIdx}")
	public void accept(@PathVariable Integer reqIdx) {
		//교환요청리스트
		CardRequestList cardRequestList = cardService.selectCardRequestListWithReqIdx(reqIdx);
		if(cardRequestList == null) {
			throw new HandlableException(ErrorCode.FAILED_TO_LOAD_INFO);
		}
		//card status ->'REQUEST->'ONGOING'
		cardService.updateCardStatusWithCardIdxSet(cardRequestList,"ONGOING");
		
		//교환형황 테이블 생성
		cardService.insertExchangeStatus(cardRequestList);
		
		//수락 알림 보내기
		alarmService.sendAlarmWithStatus(cardRequestList, "요청수락");
	}
	
	
	@GetMapping("request-cancel/{reqIdx}")
	public void requestCancel(@PathVariable Integer reqIdx) {
		//교환요청리스트
		CardRequestList cardRequestList = cardService.selectCardRequestListWithReqIdx(reqIdx);
		if(cardRequestList == null) {
			throw new HandlableException(ErrorCode.FAILED_TO_LOAD_INFO);
		}
		//card status ->'REQUEST->'NONE'
		cardService.updateCardStatusWithCardIdxSet(cardRequestList,"NONE");
		
		//돈 돌려줘야됨
		pointService.updateSavePoint(cardRequestList);
		
		//교환요청리스트 삭제
		cardService.deleteCardRequestList(cardRequestList.getReqIdx());
		
		//취소 알림 보내기
		alarmService.sendAlarmWithStatus(cardRequestList, "요청취소");
	}
	
	@GetMapping("exchange-cancel/{reqIdx}")
	public void exchangeCancel(@PathVariable Integer reqIdx) {
		//교환요청리스트
		CardRequestList cardRequestList = cardService.selectCardRequestListWithReqIdx(reqIdx);
		if(cardRequestList == null) {
			throw new HandlableException(ErrorCode.FAILED_TO_LOAD_INFO);
		}
		//card status ->'REQUEST->'NONE'
		cardService.updateCardStatusWithCardIdxSet(cardRequestList,"NONE");
		
		//돈 돌려줘야됨
		pointService.updateSavePoint(cardRequestList);
		
		//교환요청리스트 삭제
		cardService.deleteCardRequestList(cardRequestList.getReqIdx());
		
		//교환현황 삭제
		cardService.deleteExchangeStatus(cardRequestList.getReqIdx());
		
		//취소 알림 보내기
		alarmService.sendAlarmWithStatus(cardRequestList, "교환취소");
	}
	
	@GetMapping("complete/{reqIdx}")
	public void complete(@PathVariable Integer reqIdx) {
		//확정요청리스트
		CardRequestList cardRequestList = cardService.selectCardRequestListWithReqIdx(reqIdx);
		if(cardRequestList == null) {
			throw new HandlableException(ErrorCode.FAILED_TO_LOAD_INFO);
		}
		//card status ->'REQUEST->'DONE'
		cardService.updateCardStatusWithCardIdxSet(cardRequestList,"DONE");
		
		//교환현황 -> 'ONGOING'->'DONE'
		cardService.updateExchangeStatus(cardRequestList.getReqIdx(), "DONE");
		
		//교환 내역 생성
		exchangeService.insertExchangeHistory(cardService.selectExchangeStatusWithReqIdx(reqIdx));
		
		//교환완료 알림 보내기
		alarmService.sendAlarmWithStatus(cardRequestList, "교환완료");
	}
	
	@GetMapping("revise/{reqIdx}")
	public String revise(@PathVariable Integer reqIdx
						, @AuthenticationPrincipal MemberAccount certifiedMember
						, Model model) {
		
		//확정요청리스트
		CardRequestList cardRequestList = cardService.selectCardRequestListWithReqIdx(reqIdx);
		if(cardRequestList == null) {
			throw new HandlableException(ErrorCode.FAILED_TO_LOAD_INFO);
		}
		
		Set<Integer> cardIdxSet = cardService.getCardIdxSet(cardRequestList);
		List<Card> cardList = cardService.selectCardList(cardIdxSet);
		
		List<Map<String,Object>> cardlist = new ArrayList<>();
		if(cardList != null) {
			for (Card card : cardList) {
				FileDTO fileDTO = exchangeService.selectImgFileByCardIdx(card.getCardIdx());
				cardlist.add(Map.of("card", card, "fileDTO", fileDTO));
			}
		}
		float myRate = exchangeService.selectMyRate(certifiedMember.getMemberIdx());
		
		model.addAttribute("cardIdxSet",cardIdxSet);
		model.addAttribute("cardlist", cardlist);
		model.addAttribute("myRate",myRate);
		
		//교환 희망 카드
		Card cardInfo = exchangeService.selectCardByCardIdx(cardRequestList.getRequestedCard());
		FileDTO fileDTO = exchangeService.selectImgFileByCardIdx(cardRequestList.getRequestedCard());
		float userRate = exchangeService.selectMyRate(cardInfo.getMemberIdx());
		
		model.addAttribute("userRate", userRate);
		model.addAttribute("wishCard", Map.of("cardInfo", cardInfo, "fileDTO", fileDTO));
		
		//포인트 잔액
		SavePoint savePoint = exchangeService.selectSavePointByMemberIdx(certifiedMember.getMemberIdx());
		if(savePoint != null) {
			model.addAttribute("availableBal", savePoint.getAvailableBal()+cardRequestList.getPropBalance());
			model.addAttribute("balance", savePoint.getBalance());
		}
		
		model.addAttribute("propBalance", cardRequestList.getPropBalance());
		
		return "exchange/detailReviceForm";
	}
	
	@PostMapping("reviseForm")
	public String revise(@AuthenticationPrincipal MemberAccount certifiedMember
			, int wishCardIdx
			, String offerPoint
			, int availableBal
			, Set<Integer> previousCardIdxSet
			, @RequestParam(required = false)  String[] cardIdxList
			, Model model) {
		
		//교환요청리스트
		CardRequestList cardRequestList = new CardRequestList();
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
		exchangeService.updateRequestExchange(cardRequestList, cardIdxList.length);
		
		String[] previousCardIdxArr = (String[]) previousCardIdxSet.toArray();
		for(int i = 0;i < previousCardIdxArr.length; i++) {
			for(int j = 0; j < cardIdxList.length; j++) {
				if(previousCardIdxArr[i] == cardIdxList[j]) {
					previousCardIdxArr[i] = null;
					cardIdxList[j] = null;
				}
			}
		}
		// previousCardIdxArr -> request -> none 으로 변경해야되는 값
		for (String previousCardIdx : previousCardIdxArr) {
			if(previousCardIdx != null) {
				cardService.updateCardWithStatus(Integer.parseInt(previousCardIdx), "NONE");
			}
		}
		// cardIdxList -> none -> request 로 변경해야되는 값
		for (String cardIdx : cardIdxList) {
			if(cardIdx != null) {
				cardService.updateCardWithStatus(Integer.parseInt(cardIdx), "REQUEST");
			}
		}
		
		//포인트 holding ?? 후 가용 포인트
		pointService.updateSavePointWithAvailableBal(availableBal - offerPointInt, certifiedMember.getMemberIdx());
		
		return "exchange/detailReviceForm";
	}
	

}
