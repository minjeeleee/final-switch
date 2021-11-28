package com.kh.switchswitch.card.model.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.kh.switchswitch.card.model.dto.Card;
import com.kh.switchswitch.card.model.dto.CardRequestList;
import com.kh.switchswitch.card.model.dto.SearchCard;
import com.kh.switchswitch.exchange.model.dto.ExchangeStatus;

public interface CardService {

	void insertCard(List<MultipartFile> imgList, Card card);

	int selectCardMemberIdxWithCardIdx(int wishCardIdx);
	
	List<Card> selectAllCard();

	CardRequestList selectCardRequestListWithReqIdx(Integer reqIdx);

	Card selectCardWithCardIdx(Integer requestedCard);

	List<Card> CardListWithMemberIdx(Integer requestMemIdx);

	void updateCardWithCardIdx(Card card);

	void deleteCardRequestList(Integer reqIdx);

	void updateCardStatusWithCardIdxSet(CardRequestList cardRequestList, String status);

	void insertExchangeStatus(CardRequestList cardRequestList);
	
	Set<Integer> getCardIdxSet(CardRequestList cardRequestList);

	String selectExchangeStatusType(Integer reqIdx);

	void deleteExchangeStatus(Integer reqIdx);

	void updateExchangeStatus(Integer reqIdx, String type);

	ExchangeStatus selectExchangeStatusWithReqIdx(Integer reqIdx);

	List<Card> selectCardList(Set<Integer> cardIdxSet);

	void updateCardWithStatus(int previousCardIdx, String status);
	
	List<Card> searchCategoryCard(String category);

	Card selectCardByReqIdx(Integer reqIdx);

	List<Map<String, Object>> selectRequestedCardList(Integer memberIdx);

	List<Map<String, Object>> selectOngoingCardList(Integer memberIdx);

	List<Map<String, Object>> selectRequestCardList(Integer memberIdx);
	
	List<Map<String,Object>> selectDoneCardList(Integer memberIdx);

	List<Card> selectCardTrim(SearchCard searchCard);
}
