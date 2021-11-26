package com.kh.switchswitch.exchange.model.service;

import java.util.List;

import com.kh.switchswitch.card.model.dto.Card;
import com.kh.switchswitch.card.model.dto.CardRequestList;
import com.kh.switchswitch.common.util.FileDTO;
import com.kh.switchswitch.point.model.dto.SavePoint;

public interface ExchangeService {

	List<Card> selecAvailableMyCardList(int certifiedMemberIdx);

	float selectMyRate(int certifiedMemberIdx);

	FileDTO selectImgFileByCardIdx(int cardIdx);

	Card selectCardByCardIdx(int wishCardIdx);

	SavePoint selectSavePointByMemberIdx(int memberIdx);

	void requestExchange(CardRequestList cardRequestList, int length);

	int selectMemberIdxByCardIdx(int wishCardIdx);
	
	List<Integer> selectMyRateCnt(int memberIdx);

	boolean checkExchangeOngoing(Integer memberIdx);
	

}
