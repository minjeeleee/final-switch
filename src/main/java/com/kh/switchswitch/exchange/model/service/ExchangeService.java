package com.kh.switchswitch.exchange.model.service;

import java.util.List;

import com.kh.switchswitch.card.model.dto.Card;
import com.kh.switchswitch.common.util.FileDTO;
import com.kh.switchswitch.exchange.model.dto.ExchangeStatus;

public interface ExchangeService {

	List<Card> selecAvailableMyCardList(int certifiedMemberIdx);

	float selectMyRate(int certifiedMemberIdx);

	FileDTO selectImgFileByCardIdx(int cardIdx);

	Card selectCardByCardIdx(int wishCardIdx);

	int selectBalanceByMemberIdx(int memberIdx);

	void insertExchangeStatus(ExchangeStatus exchangeStatus);
	
	

}
