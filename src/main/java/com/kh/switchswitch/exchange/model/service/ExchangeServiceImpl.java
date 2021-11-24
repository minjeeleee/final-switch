package com.kh.switchswitch.exchange.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.switchswitch.card.model.dto.Card;
import com.kh.switchswitch.card.model.repository.CardRepository;
import com.kh.switchswitch.common.util.FileDTO;
import com.kh.switchswitch.exchange.model.repository.RatingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService{
	
	private final CardRepository cardRepository;
	private final RatingRepository ratingRepository;

	public List<Card> selecAvailableMyCardList(int certifiedMemberIdx) {
		return cardRepository.selectCardListIsDelAndStatus(certifiedMemberIdx);
	}

	public float selectMyRate(int certifiedMemberIdx) {
		List<Float> ratingList = ratingRepository.selectRatingByMemberIdx(certifiedMemberIdx);
		float sum = 0;
		for (Float f : ratingList) {
			sum += f;
		}
		return sum/ratingList.size();
	}

	public FileDTO selectImgFileByCardIdx(int cardIdx) {
		List<FileDTO> fileDTOList = cardRepository.selectFileInfoByCardIdx(cardIdx);
		return fileDTOList.get(0);
	}
	
	

}
