package com.kh.switchswitch.exchange.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kh.switchswitch.card.model.dto.Card;
import com.kh.switchswitch.card.model.dto.CardRequestList;
import com.kh.switchswitch.card.model.dto.FreeRequestList;
import com.kh.switchswitch.card.model.repository.CardRepository;
import com.kh.switchswitch.card.model.repository.CardRequestListRepository;
import com.kh.switchswitch.card.model.repository.FreeRequestListRepository;
import com.kh.switchswitch.common.util.FileDTO;
import com.kh.switchswitch.exchange.model.dto.ExchangeHistory;
import com.kh.switchswitch.exchange.model.dto.ExchangeStatus;
import com.kh.switchswitch.exchange.model.repository.ExchangeRepository;
import com.kh.switchswitch.exchange.model.repository.RatingRepository;
import com.kh.switchswitch.member.model.repository.MemberRepository;
import com.kh.switchswitch.point.model.dto.SavePoint;
import com.kh.switchswitch.point.model.repository.SavePointRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService{
	
	Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	private final CardRepository cardRepository;
	private final RatingRepository ratingRepository;
	private final SavePointRepository savePointRepository;
	private final ExchangeRepository exchangeRepository;
	private final MemberRepository memberRepository;
	private final CardRequestListRepository cardRequestListRepository;
	private final FreeRequestListRepository freerequestListRepository;

	public List<Card> selecAvailableMyCardList(int certifiedMemberIdx) {
		return cardRepository.selectCardListIsDelAndStatus(certifiedMemberIdx);
	}

	public float selectMyRate(int certifiedMemberIdx) {
		List<Float> ratingList = ratingRepository.selectRatingByMemberIdx(certifiedMemberIdx);
		
		float sum = 0;
		for (Float f : ratingList) {
			sum += f;
		}
		if(sum == 0) {
			return 0;
		}
		return sum/ratingList.size();
	}

	public FileDTO selectImgFileByCardIdx(int cardIdx) {
		List<FileDTO> fileDTOList = cardRepository.selectFileInfoByCardIdx(cardIdx);
		return fileDTOList.get(0);
	}

	public Card selectCardByCardIdx(int wishCardIdx) {
		return cardRepository.selectCardByCardIdx(wishCardIdx);
	}

	public SavePoint selectSavePointByMemberIdx(int memberIdx) {
		return savePointRepository.selectSavePointByMemberIdx(memberIdx);
	}

	public void insertExchangeStatus(ExchangeStatus exchangeStatus) {
		exchangeRepository.insertExchangeStatus(exchangeStatus);
		
	}

	public int selectMemberIdxByCardIdx(int wishCardIdx) {
		return cardRepository.selectMemberIdxByCardIdx(wishCardIdx);
	}

	public CardRequestList requestExchange(CardRequestList cardRequestList,int length) {
		//card_request_list 테이블에 추가
		cardRequestListRepository.insertCardRequestList(cardRequestList);
		Integer reqIdx = cardRequestListRepository.selectNewReqIdx();
		CardRequestList crl = cardRequestListRepository.selectCardRequestListWithReqIdx(reqIdx);
			Card card;
			switch(5-length) {
			case 1 : 
				card = new Card();
				card.setCardIdx(cardRequestList.getRequestCard4());
				card.setExchangeStatus("REQUEST");
				cardRepository.modifyCard(card);
			case 2 : 
				card = new Card();
				card.setCardIdx(cardRequestList.getRequestCard3());
				card.setExchangeStatus("REQUEST");
				cardRepository.modifyCard(card);
			case 3 : 
				card = new Card();
				card.setCardIdx(cardRequestList.getRequestCard2());
				card.setExchangeStatus("REQUEST");
				cardRepository.modifyCard(card);
			case 4 : 
				card = new Card();
				card.setCardIdx(cardRequestList.getRequestCard1());
				card.setExchangeStatus("REQUEST");
				cardRepository.modifyCard(card);
				break;
			default : logger.debug("왜 0이 들어오지??");
		}
			
		return crl;
	}
	
	public List<Integer> selectMyRateCnt(int memberIdx) {
		return ratingRepository.selectMyRateCnt(memberIdx);
	}


	public void insertExchangeHistory(ExchangeStatus exchangeStatus) {
		ExchangeHistory exchangeHistory = new ExchangeHistory();
		exchangeHistory.setEIdx(exchangeStatus.getEIdx());
		exchangeHistory.setRequestedMemIdx(exchangeStatus.getRequestedMemIdx());
		exchangeHistory.setRequestMemIdx(exchangeStatus.getRequestMemIdx());
		exchangeRepository.insertExchangeHistory(exchangeHistory);
	}

	public void updateRequestExchange(CardRequestList cardRequestList, int length) {
		//card_request_list 테이블에 추가
		//cardRequestListRepository.updateCardRequestList(cardRequestList);
			Card card;
			switch(5-length) {
			case 1 : 
				card = new Card();
				card.setCardIdx(cardRequestList.getRequestCard4());
				card.setExchangeStatus("REQUEST");
				cardRepository.modifyCard(card);
			case 2 : 
				card = new Card();
				card.setCardIdx(cardRequestList.getRequestCard3());
				card.setExchangeStatus("REQUEST");
				cardRepository.modifyCard(card); 
			case 3 : 
				card = new Card();
				card.setCardIdx(cardRequestList.getRequestCard2());
				card.setExchangeStatus("REQUEST");
				cardRepository.modifyCard(card);
			case 4 : 
				card = new Card();
				card.setCardIdx(cardRequestList.getRequestCard1());
				card.setExchangeStatus("REQUEST");
				cardRepository.modifyCard(card);
				break;
			default : logger.debug("왜 0이 들어오지??");
			}
		
	}


	public boolean checkExchangeOngoing(Integer memberIdx) {
		List<ExchangeStatus> exchangeStatus = exchangeRepository.selectEsByMemberIdxAndTypeOngoing(memberIdx);
		if(exchangeStatus.size() != 0) {
			return true;
		}
		return false;
	}

	public List<ExchangeStatus> selectEsByMemberIdxAndTypeOngoing(Integer memberIdx) {
		return exchangeRepository.selectEsByMemberIdxAndTypeOngoing(memberIdx);
	}

	
	public List<Map<String,Object>> selectExchangeHistoryByMemIdx(Integer memberIdx) {
		List<ExchangeHistory> ehList = exchangeRepository.selectExchangeHistoryByMemIdx(memberIdx);
		//필요한 정보 거래날짜(exchange_history) 교환포인트(prop_balance) 교환카드 기존카드 별점 등록여부 상대방 닉네임
		//필요한 정보 가져오기
		List<CardRequestList> crlList = new ArrayList<>();
		List<String> opponentNickList = new ArrayList<>();

		List<Integer> isRateList = new ArrayList<>();
		for (ExchangeHistory exchangeHistory : ehList) {
			crlList.add(cardRepository.selectCardRequestByEIdx(exchangeHistory.getEIdx()));
			isRateList.add(ratingRepository.selectRatingByMemIdxAndEhIdx(memberIdx,exchangeHistory.getEhIdx()));
			if(memberIdx.equals(exchangeHistory.getRequestMemIdx())) {
				opponentNickList.add(memberRepository.selectMemberNickWithMemberIdx(exchangeHistory.getRequestedMemIdx()));
			} else {
				opponentNickList.add(memberRepository.selectMemberNickWithMemberIdx(exchangeHistory.getRequestMemIdx()));
			}
		}
		List<String> requestedCardNameList = getRequestCardNameList(crlList);
		List<String> requestCardNameList = getRequestedCardNameList(crlList);
		
		List<Map<String, Object>> exchangeHistoryList = new ArrayList();
		for (int i = 0; i < ehList.size(); i++) {
			exchangeHistoryList.add(Map.of("eh",ehList.get(i),"crl",crlList.get(i)
					,"isRate",isRateList.get(i),"requestedCardName",requestedCardNameList.get(i)
					,"requestCardName",requestCardNameList.get(i)
					,"opponentNickList",opponentNickList.get(i)));
		}
		
		return exchangeHistoryList;
	}
	
	public List<String> getRequestCardNameList(List<CardRequestList> crlList){
		List<String> requestedCardNameList = new ArrayList();
		for (CardRequestList cardRequestList : crlList) {
			requestedCardNameList.add(cardRepository.selectCardByCardIdx(cardRequestList.getRequestedCard()).getName());
		}
		return requestedCardNameList;
	}
	
	public List<String> getRequestedCardNameList(List<CardRequestList> crlList){
		List<String> requestCardNameList = new ArrayList();
		for (CardRequestList cardRequestList : crlList) {
			String cardNames = "";
			cardNames += cardRepository.selectCardByCardIdx(cardRequestList.getRequestCard1()).getName();
			if(cardRequestList.getRequestCard2()!= null)cardNames += ","+cardRepository.selectCardByCardIdx(cardRequestList.getRequestCard2()).getName();
			if(cardRequestList.getRequestCard3()!= null)cardNames += ","+cardRepository.selectCardByCardIdx(cardRequestList.getRequestCard3()).getName();
			if(cardRequestList.getRequestCard4()!= null)cardNames += ","+cardRepository.selectCardByCardIdx(cardRequestList.getRequestCard4()).getName();
			requestCardNameList.add(cardNames);
		}
		return requestCardNameList;
	}

	public void requestFreeSharing(Integer memberIdx, Integer cardIdx) {
		FreeRequestList freeRequest = new FreeRequestList();
		freeRequest.setRequestedCard(cardIdx);
		freeRequest.setRequestMemIdx(memberIdx);
		freeRequest.setRequestedMemIdx(cardRepository.selectMemberIdxByCardIdx(cardIdx));
		freerequestListRepository.insertFreeRequestList(freeRequest);
		
	}

	
	public void rejectFreeSharing(Integer freqIdx) {
		freerequestListRepository.deleteFreeRequestList(freqIdx);
	}

	@Override
	public FreeRequestList selectFreeRequestListWithFreqIdx(Integer freqIdx) {
		return freerequestListRepository.selectFreeRequestListWithFreqIdx(freqIdx);
	}

}
