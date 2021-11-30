package com.kh.switchswitch.card.model.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.switchswitch.card.model.dto.Card;
import com.kh.switchswitch.card.model.dto.CardRequestList;
import com.kh.switchswitch.card.model.dto.SearchCard;
import com.kh.switchswitch.card.model.dto.WishList;
import com.kh.switchswitch.card.model.repository.CardRepository;
import com.kh.switchswitch.card.model.repository.CardRequestListRepository;
import com.kh.switchswitch.common.util.FileDTO;
import com.kh.switchswitch.common.util.FileUtil;
import com.kh.switchswitch.exchange.model.dto.ExchangeStatus;
import com.kh.switchswitch.exchange.model.repository.ExchangeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
	
	private final CardRepository cardRepository;
	private final CardRequestListRepository cardRequestListRepository;
	private final ExchangeRepository exchangeRepository;
	
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

	public CardRequestList selectCardRequestListWithReqIdx(Integer reqIdx) {
		return cardRequestListRepository.selectCardRequestListWithReqIdx(reqIdx);
	}

	public Card selectCardWithCardIdx(Integer requestedCard) {
		return cardRepository.selectCardByCardIdx(requestedCard);
	}

	public List<Card> CardListWithMemberIdx(Integer requestMemIdx) {
		List<Card> cardList = new ArrayList<Card>();
		
		return null;
	}

	public void updateCardWithCardIdx(Card card) {
		cardRepository.updateCard(card);
	}
	
	public void updateCardStatusWithCardIdxSet(CardRequestList cardRequestList, String status) {
		for (Integer cardIdx : getCardIdxSet(cardRequestList)) {
			Card card = new Card();
			card.setCardIdx(cardIdx);
			card.setExchangeStatus(status);
			cardRepository.updateCard(card);
		}
	}

	public void deleteCardRequestList(Integer reqIdx) {
		cardRepository.deleteCardRequestListWithReqIdx(reqIdx);
		
	}

	public void insertExchangeStatus(CardRequestList cardRequestList) {
		ExchangeStatus exchangeStatus = new ExchangeStatus();
		exchangeStatus.setPropBalance(cardRequestList.getPropBalance());
		exchangeStatus.setReqIdx(cardRequestList.getReqIdx());
		exchangeStatus.setRequestedMemIdx(cardRequestList.getRequestedMemIdx());
		exchangeStatus.setRequestMemIdx(cardRequestList.getRequestMemIdx());
		exchangeRepository.insertExchangeStatus(exchangeStatus);
	}
	


	public Set<Integer> getCardIdxSet(CardRequestList cardRequestList){
		Set<Integer> cardIdxSet = new LinkedHashSet<Integer>();
		cardIdxSet.add(cardRequestList.getRequestCard1());
		cardIdxSet.add(cardRequestList.getRequestCard2());
		cardIdxSet.add(cardRequestList.getRequestCard3());
		cardIdxSet.add(cardRequestList.getRequestCard4());
		cardIdxSet.remove(null);
		return cardIdxSet;
	}

	public String selectExchangeStatusType(Integer reqIdx) {
		return exchangeRepository.selectExchangeStatusType(reqIdx);
	}

	public void deleteExchangeStatus(Integer reqIdx) {
		exchangeRepository.deleteExchangeStatusWithReqIdx(reqIdx);
	}

	public void updateExchangeStatus(Integer reqIdx, String type) {
		ExchangeStatus exchangeStatus = new ExchangeStatus();
		exchangeStatus.setReqIdx(reqIdx);
		exchangeStatus.setType(type);
		exchangeRepository.updateExchangeStatus(exchangeStatus);
	}

	public ExchangeStatus selectExchangeStatusWithReqIdx(Integer reqIdx) {
		return exchangeRepository.selectExchangeStatusWithReqIdx(reqIdx);
	}

	public List<Card> selectCardList(Set<Integer> cardIdxSet) {
		List<Card> cardList = new ArrayList<Card>();
		for (Integer cardIdx : cardIdxSet) {
			cardList.add(cardRepository.selectCardByCardIdx(cardIdx));
		}
		return cardList;
	}

	public void updateCardWithStatus(int previousCardIdx, String status) {
		Card card = new Card();
		card.setCardIdx(previousCardIdx);
		card.setExchangeStatus(status);
		cardRepository.updateCard(card);
	}

	public List<Card> searchCategoryCard(String category) {
		return cardRepository.searchCategoryCard(category);
	}

	public Card selectCardByReqIdx(Integer reqIdx) {
		return cardRepository.selectCardByReqIdx(reqIdx);
	}

	public List<Map<String, Object>> selectRequestedCardList(Integer memberIdx) {
		List<Map<String, Object>> requestedCardList = new ArrayList<>();
		
		List<Integer> crlCardIdxList = cardRequestListRepository.selectCardIdxWithMemberIdx(memberIdx);
		
		for (Integer crlCardIdx : crlCardIdxList) {
			for (Integer esCardIdx : exchangeRepository.selectCardIdxWithMemberIdx(memberIdx)) {
				if(crlCardIdx.equals(esCardIdx)) {
					crlCardIdxList.remove(crlCardIdx);
				}
			}
		}
		
		List<Card> cardList = new ArrayList();
		for (Integer cardIdx : crlCardIdxList) {
			cardList.add(cardRepository.selectCardByCardIdx(cardIdx));
		}
		for (Card card : cardList) {
			requestedCardList.add(Map.of("requestedCard",card,"fileDTO", cardRepository.selectFileInfoByCardIdx(card.getCardIdx()).get(0)));
		}
		return requestedCardList;
	}

	public List<Map<String, Object>> selectOngoingCardList(Integer memberIdx) {
		List<Map<String, Object>> ongoingCardList = new ArrayList<>();
		List<Card> cardList = cardRepository.selectCardByMemberIdxWithOngoing(memberIdx);
		for (Card card : cardList) {
			ongoingCardList.add(Map.of("ongoingCard",card,"fileDTO", cardRepository.selectFileInfoByCardIdx(card.getCardIdx()).get(0)));
		}
		return ongoingCardList;
	}

	public List<Map<String, Object>> selectRequestCardList(Integer memberIdx) {
		List<Map<String, Object>> requestCardList = new ArrayList<>();
		List<Card> myRequestCardList = cardRepository.selectCardByMemberIdxWithRequest(memberIdx);
		for (Card card : myRequestCardList) {
			requestCardList.add(Map.of("requestCard",card,"fileDTO", cardRepository.selectFileInfoByCardIdx(card.getCardIdx()).get(0)));
		}
		return requestCardList;
	}

	
	public List<Map<String, Object>> selectDoneCardList(Integer memberIdx) {
		List<Map<String, Object>> doneCardList = new ArrayList<>();
		List<Card> cardList = cardRepository.selectCardByMemberIdxWithDONE(memberIdx);
		for (Card card : cardList) {
			doneCardList.add(Map.of("doneCard",card,"fileDTO", cardRepository.selectFileInfoByCardIdx(card.getCardIdx()).get(0)));
		}
		return doneCardList;
	}	

	@Override
	public List<Card> selectCardTrim(SearchCard searchCard) {
		return cardRepository.selectCardTrim(searchCard);
	}

	public void modifyCard(List<MultipartFile> imgList, Card card) {
		FileUtil fileUtil = new FileUtil();

		// card instance 생성
		cardRepository.modifyCard(card);

		//삭제
		cardRepository.deleteCard(card.getCardIdx());

		
		// card instance
		for (MultipartFile multipartFile : imgList) {
			if (!multipartFile.isEmpty()) {
				cardRepository.modifyFileInfo(fileUtil.fileUpload(multipartFile),card.getCardIdx());
			}
		}
	}

	public List<Map<String, Object>> selectMyExchangeCard(Integer memberIdx) {
		
		List<Map<String, Object>> exchangeCards = new ArrayList();
		List<Card> cardList = cardRepository.selectCardByMemberIdxAndIsFreeExceptDone(memberIdx,"N");
		for (Card card : cardList) {
			exchangeCards.add(Map.of("myExchangeCard",card,"fileDTO", cardRepository.selectFileInfoByCardIdx(card.getCardIdx()).get(0)));
		}
		
		return exchangeCards;
	}

	public List<Map<String, Object>> selectMyFreeCard(Integer memberIdx) {
		List<Map<String, Object>> freeCards = new ArrayList();
		
		List<Card> cardList = cardRepository.selectCardByMemberIdxAndIsFreeExceptDone(memberIdx,"Y");
		for (Card card : cardList) {
			freeCards.add(Map.of("freeCard",card,"fileDTO", cardRepository.selectFileInfoByCardIdx(card.getCardIdx()).get(0)));
		}
		
		return freeCards;
	}

	public Map<String, Object> selectModifyCard(Integer cardIdx) {

		Card card = cardRepository.selectCardByCardIdx(cardIdx);
		List<FileDTO> fileDTOs = cardRepository.selectFileInfoByCardIdx(cardIdx);
		String[] wishCard = card.getHopeKind().split(",");
		
		return Map.of("card",card,"files",fileDTOs ,"wishCard",wishCard);
	}

}
