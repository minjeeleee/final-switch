package com.kh.switchswitch.card.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.switchswitch.card.model.dto.Card;
import com.kh.switchswitch.card.model.repository.CardRepository;
import com.kh.switchswitch.common.util.FileUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
	
	private final CardRepository cardRepository;
	
	@Override
	public void insertCard(List<MultipartFile> imgList, Card card) {
		FileUtil fileUtil = new FileUtil();
		
		//card instance 생성
		cardRepository.insertCard(card);
		
		//card instance
		for (MultipartFile multipartFile : imgList) {
			if(!multipartFile.isEmpty()) {
				cardRepository.insertFileInfo(fileUtil.fileUpload(multipartFile));
			}
		}
	}

	@Override
	public List<Card> selectAllCard() {
		return cardRepository.selectAllCard();
	}

	public int selectCardMemberIdxWithCardIdx(int wishCardIdx) {
		return cardRepository.selectCardMemberIdxWithCardIdx(wishCardIdx);
	}
	

}
