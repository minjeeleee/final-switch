package com.kh.switchswitch.card.model.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
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
	
	public void updateCardStatusWithCardIdxSet(CardRequestList cardRequestList, String status) {
		for (Integer cardIdx : getCardIdxSet(cardRequestList)) {
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
	


	public Set<Integer> getCardIdxSet(CardRequestList cardRequestList){
		Set<Integer> cardIdxSet = new LinkedHashSet<Integer>();
		cardIdxSet.add(cardRequestList.getRequestCard1());
		cardIdxSet.add(cardRequestList.getRequestCard2());
		cardIdxSet.add(cardRequestList.getRequestCard3());
		cardIdxSet.add(cardRequestList.getRequestCard4());
		cardIdxSet.remove(null);
		return cardIdxSet;
	}

	public String selectExchangeStatusType(Integer reqIdx) {
		return exchangeRepository.selectExchangeStatusType(reqIdx);
	}

	public void deleteExchangeStatus(Integer reqIdx) {
		exchangeRepository.deleteExchangeStatusWithReqIdx(reqIdx);
	}

	public void updateExchangeStatus(Integer reqIdx, String type) {
		ExchangeStatus exchangeStatus = new ExchangeStatus();
		exchangeStatus.setReqIdx(reqIdx);
		exchangeStatus.setType(type);
		exchangeRepository.updateExchangeStatus(exchangeStatus);
	}

	public ExchangeStatus selectExchangeStatusWithReqIdx(Integer reqIdx) {
		return exchangeRepository.selectExchangeStatusWithReqIdx(reqIdx);
	}

	public List<Card> selectCardList(Set<Integer> cardIdxSet) {
		List<Card> cardList = new ArrayList<Card>();
		for (Integer cardIdx : cardIdxSet) {
			cardList.add(cardRepository.selectCardByCardIdx(cardIdx));
		}
		return cardList;
	}

	public void updateCardWithStatus(int previousCardIdx, String status) {
		Card card = new Card();
		card.setCardIdx(previousCardIdx);
		card.setExchangeStatus(status);
		cardRepository.updateCard(card);
	}

	public List<Card> searchCategoryCard(String category) {
		return cardRepository.searchCategoryCard(category);
	}
	

}
