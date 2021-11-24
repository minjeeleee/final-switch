package com.kh.switchswitch.exchange.model.service;

import java.util.List;

import com.kh.switchswitch.card.model.dto.Card;
import com.kh.switchswitch.common.util.FileDTO;

public interface ExchangeService {

	List<Card> selecAvailableMyCardList(int certifiedMemberIdx);

	float selectMyRate(int certifiedMemberIdx);

	FileDTO selectImgFileByCardIdx(int cardIdx);

}
