package com.kh.switchswitch.card.model.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kh.switchswitch.card.model.dto.Card;

public interface CardService {

	void insertCard(List<MultipartFile> imgList, Card card);

	int selectCardMemberIdxWithCardIdx(int wishCardIdx);
	
	List<Card> selectAllCard();
}
