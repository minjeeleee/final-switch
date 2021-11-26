package com.kh.switchswitch.exchange.model.service;

import java.util.List;

import org.apache.ibatis.reflection.SystemMetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kh.switchswitch.card.model.dto.Card;
import com.kh.switchswitch.card.model.dto.CardRequestList;
import com.kh.switchswitch.card.model.repository.CardRepository;
import com.kh.switchswitch.card.model.repository.CardRequestListRepository;
import com.kh.switchswitch.common.util.FileDTO;
import com.kh.switchswitch.exchange.model.dto.ExchangeHistory;
import com.kh.switchswitch.exchange.model.dto.ExchangeStatus;
import com.kh.switchswitch.exchange.model.repository.ExchangeRepository;
import com.kh.switchswitch.exchange.model.repository.RatingRepository;
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
	private final CardRequestListRepository cardRequestListRepository;

	public List<Card> selecAvailableMyCardList(int certifiedMemberIdx) {
		return cardRepository.selectCardListIsDelAndStatus(certifiedMemberIdx);
	}

	public float selectMyRate(int certifiedMemberIdx) {
		List<Float> ratingList = ratingRepository.selectRatingByMemberIdx(certifiedMemberIdx).orElse(List.of(0f));
		
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

	public void requestExchange(CardRequestList cardRequestList,int length) {
		//card_request_list 테이블에 추가
		cardRequestListRepository.insertCardRequestList(cardRequestList);
			Card card;
			switch(5-length) {
			case 1 : 
				card = new Card();
				card.setCardIdx(cardRequestList.getRequestCard4());
				card.setExchangeStatus("REQUEST");
				cardRepository.updateCard(card);
			case 2 : 
				card = new Card();
				card.setCardIdx(cardRequestList.getRequestCard3());
				card.setExchangeStatus("REQUEST");
				cardRepository.updateCard(card); 
			case 3 : 
				card = new Card();
				card.setCardIdx(cardRequestList.getRequestCard2());
				card.setExchangeStatus("REQUEST");
				cardRepository.updateCard(card);
			case 4 : 
				card = new Card();
				card.setCardIdx(cardRequestList.getRequestCard1());
				card.setExchangeStatus("REQUEST");
				cardRepository.updateCard(card);
				break;
			default : logger.debug("왜 0이 들어오지??");
			}
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
				cardRepository.updateCard(card);
			case 2 : 
				card = new Card();
				card.setCardIdx(cardRequestList.getRequestCard3());
				card.setExchangeStatus("REQUEST");
				cardRepository.updateCard(card); 
			case 3 : 
				card = new Card();
				card.setCardIdx(cardRequestList.getRequestCard2());
				card.setExchangeStatus("REQUEST");
				cardRepository.updateCard(card);
			case 4 : 
				card = new Card();
				card.setCardIdx(cardRequestList.getRequestCard1());
				card.setExchangeStatus("REQUEST");
				cardRepository.updateCard(card);
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
}
