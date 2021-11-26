package com.kh.switchswitch.card.model.service;

import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.kh.switchswitch.card.model.dto.Card;
import com.kh.switchswitch.card.model.dto.CardRequestList;

public interface CardService {

	void insertCard(List<MultipartFile> imgList, Card card);

	int selectCardMemberIdxWithCardIdx(int wishCardIdx);
	
	List<Card> selectAllCard();

	CardRequestList selectCardRequestListWithReqIdx(Integer reqIdx);

	Card selectCardWithCardIdx(Integer requestedCard);

	List<Card> CardListWithMemberIdx(Integer requestMemIdx);

	void updateCardWithCardIdx(Card card);

	void deleteCardRequestList(Integer reqIdx);

	void updateCardStatusWithCardIdxSet(Set<Integer> cardIdxSet, String status);

	void insertExchangeStatus(CardRequestList cardRequestList);
}
