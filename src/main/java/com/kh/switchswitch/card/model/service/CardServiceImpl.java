package com.kh.switchswitch.card.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.switchswitch.card.model.dto.Card;
import com.kh.switchswitch.card.model.dto.CardRequestList;
import com.kh.switchswitch.card.model.repository.CardRepository;
import com.kh.switchswitch.card.model.repository.CardRequestListRepository;
import com.kh.switchswitch.common.util.FileUtil;
import com.kh.switchswitch.exchange.model.dto.ExchangeStatus;
import com.kh.switchswitch.exchange.model.repository.ExchangeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
	
	private final CardRepository cardRepository;
	private final CardRequestListRepository cardRequestListRepository;
	private final ExchangeRepository exchangeRepository;
	
	@Override
	public void insertCard(List<MultipartFile> imgList, Card card) {
		FileUtil fileUtil = new FileUtil();
		
		//card instance 생성
		cardRepository.insertCard(card);
		
		//card instance
		for (MultipartFile multipartFile : imgList) {
			if(!multipartFile.isEmpty()) {
				cardRepository.insertFileInfo(fileUtil.fileUpload(multipartFile));
			}
		}
	}

	@Override
	public List<Card> selectAllCard() {
		return cardRepository.selectAllCard();
	}

	public int selectCardMemberIdxWithCardIdx(int wishCardIdx) {
		return cardRepository.selectCardMemberIdxWithCardIdx(wishCardIdx);
	}

	public CardRequestList selectCardRequestListWithReqIdx(Integer reqIdx) {
		return cardRequestListRepository.selectCardRequestListWithReqIdx(reqIdx);
	}

	public Card selectCardWithCardIdx(Integer requestedCard) {
		return cardRepository.selectCardByCardIdx(requestedCard);
	}

	public List<Card> CardListWithMemberIdx(Integer requestMemIdx) {
		List<Card> cardList = new ArrayList<Card>();
		
		return null;
	}

	public void updateCardWithCardIdx(Card card) {
		cardRepository.updateCard(card);
	}
	
	public void updateCardStatusWithCardIdxSet(Set<Integer> cardIdxSet, String status) {
		for (Integer cardIdx : cardIdxSet) {
			Card card = new Card();
			card.setCardIdx(cardIdx);
			card.setExchangeStatus(status);
			cardRepository.updateCard(card);
		}
	}

	public void deleteCardRequestList(Integer reqIdx) {
		cardRepository.deleteCardRequestListWithReqIdx(reqIdx);
		
	}

	public void insertExchangeStatus(CardRequestList cardRequestList) {
		ExchangeStatus exchangeStatus = new ExchangeStatus();
		exchangeStatus.setPropBalance(cardRequestList.getPropBalance());
		exchangeStatus.setReqIdx(cardRequestList.getReqIdx());
		exchangeStatus.setRequestedMemIdx(cardRequestList.getRequestedMemIdx());
		exchangeStatus.setRequestMemIdx(cardRequestList.getRequestMemIdx());
		exchangeRepository.insertExchangeStatus(exchangeStatus);
	}
	

}
