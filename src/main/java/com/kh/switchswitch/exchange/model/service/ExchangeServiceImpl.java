package com.kh.switchswitch.exchange.model.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kh.switchswitch.card.model.dto.Card;
import com.kh.switchswitch.card.model.dto.CardRequestList;
import com.kh.switchswitch.card.model.repository.CardRepository;
import com.kh.switchswitch.card.model.repository.CardRequestListRepository;
import com.kh.switchswitch.common.util.FileDTO;
import com.kh.switchswitch.exchange.model.dto.ExchangeStatus;
import com.kh.switchswitch.exchange.model.repository.ExchangeRepository;
import com.kh.switchswitch.exchange.model.repository.RatingRepository;
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

	public int selectBalanceByMemberIdx(int memberIdx) {
		return savePointRepository.selectBalanceByMemberIdx(memberIdx);
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
			default : logger.debug("왜 0이 들어오지??");
			}
		
	}
	
	

}
