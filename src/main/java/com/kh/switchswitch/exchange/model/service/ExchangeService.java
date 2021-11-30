package com.kh.switchswitch.exchange.model.service;

import java.util.List;
import java.util.Map;

import com.kh.switchswitch.card.model.dto.Card;
import com.kh.switchswitch.card.model.dto.CardRequestList;
import com.kh.switchswitch.card.model.dto.FreeRequestList;
import com.kh.switchswitch.common.util.FileDTO;
import com.kh.switchswitch.exchange.model.dto.ExchangeHistory;
import com.kh.switchswitch.exchange.model.dto.ExchangeStatus;
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

	void insertExchangeHistory(ExchangeStatus exchangeStatus);

	void updateRequestExchange(CardRequestList cardRequestList, int length);

	boolean checkExchangeOngoing(Integer memberIdx);

	List<ExchangeStatus> selectEsByMemberIdxAndTypeOngoing(Integer memberIdx);

	List<Map<String,Object>> selectExchangeHistoryByMemIdx(Integer memberIdx);

	void requestFreeSharing(Integer memberIdx, Integer cardIdx);

	void rejectFreeSharing(Integer freqIdx);

	FreeRequestList selectFreeRequestListWithFreqIdx(Integer freqIdx);


	
	

}
