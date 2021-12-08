package com.kh.switchswitch.inquiry.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.switchswitch.board.model.dto.Board;
import com.kh.switchswitch.board.model.dto.Reply;
import com.kh.switchswitch.common.util.pagination.Paging;
import com.kh.switchswitch.common.util.pagination.PagingV2;
import com.kh.switchswitch.inquiry.model.dto.Answer;
import com.kh.switchswitch.inquiry.model.dto.Inquiry;
import com.kh.switchswitch.inquiry.model.repository.InquiryRepository;
import com.kh.switchswitch.member.model.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InquiryServiceImpl implements InquiryService{
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	private final InquiryRepository inquiryRepository;
	
	public void insertInquiry(Inquiry inquiry) {
		inquiryRepository.insertInquiry(inquiry);
	}
	
	//11/17
	public Map<String, Object> selectInquiryByIdx(Integer inquiryIdx) {
		Inquiry inquiry = inquiryRepository.selectInquiryByIdx(inquiryIdx);
		return Map.of("inquiry",inquiry);
	}
	
	public Map<String, Object> selectInquiryList(int page) {
		int cntPerPage = 10;
		int total = (inquiryRepository.selectContentCnt());
		int nowPage = page;
		String url = "/inquiry/inquiry-list2";
		
		PagingV2 pagingV2 = new PagingV2(total, nowPage, cntPerPage, url);
				

		Map<String,Object> commandMap = new HashMap<String,Object>();
		commandMap.put("paging", pagingV2);
		commandMap.put("inquiryList", inquiryRepository.selectInquiryListWithPageNo(Map.of("startBoard",pagingV2.getStartAlarm(),"lastBoard",pagingV2.getEndAlarm())));
		return commandMap;
	}
	
	
	public Map<String, Object> selectMyInquiryList(int page,String memberNick) {
		int cntPerPage = 10;
		Paging pageUtil = Paging.builder()
				.url("/mypage/personal-inquiry")
				.total(inquiryRepository.selectMyContentCnt(memberNick))
				.curPage(page)
				.blockCnt(10)
				.cntPerPage(cntPerPage)
				.build();

		Map<String,Object> commandMap = new HashMap<String,Object>();
		commandMap.put("paging", pageUtil);
		System.out.println("startBoard"+(page-1)*cntPerPage+1);
		commandMap.put("inquiryList", inquiryRepository.selectInquiryListWitchUserId(Map.of("startBoard",(page-1)*cntPerPage+1,"lastBoard",(page-1)*cntPerPage+cntPerPage,"userId",memberNick)));
		return commandMap;
	}
	@Transactional
	public void modifyInquiry(Inquiry inquiry) {
		inquiryRepository.modifyInquiry(inquiry);
		
	}

	public void deleteInquiry(Integer inquiryIdx) {
		inquiryRepository.deleteInquiry(inquiryIdx);
		
	}

	public void insertAnswer(Answer answer) {
		inquiryRepository.insertAnswer(answer);
		
	}

	public List<Answer> getAnswer(Map<String, Object> commandMap) {
		 List<Answer> answer = inquiryRepository.getAnswer(((Inquiry)commandMap.get("inquiry")).getInquiryIdx());
			return answer;
	}

	public void deleteAnswer(int answerIdx) {
		inquiryRepository.deleteAnswer(answerIdx);
		
	}














	
	

	
	
	
	
	
	
	
	
	
}
