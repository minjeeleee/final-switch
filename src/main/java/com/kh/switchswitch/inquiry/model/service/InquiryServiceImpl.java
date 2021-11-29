package com.kh.switchswitch.inquiry.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kh.switchswitch.board.model.dto.Board;
import com.kh.switchswitch.board.model.repository.BoardRepository;
import com.kh.switchswitch.common.util.FileDTO;
import com.kh.switchswitch.common.util.FileUtil;
import com.kh.switchswitch.common.util.pagination.Paging;
import com.kh.switchswitch.inquiry.model.dto.Inquiry;
import com.kh.switchswitch.inquiry.model.repository.InquiryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InquiryServiceImpl implements InquiryService{
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	private final InquiryRepository inquiryRepository;
	
	public void insertInquiry(Inquiry inquiry) {
		inquiryRepository.insertInquiry(inquiry);
	}
	
	//11/17
	public Map<String, Object> selectInquiryByIdx(int inquiryIdx) {
		Inquiry inquiry = inquiryRepository.selectInquiryByIdx(inquiryIdx);
		return Map.of("inquiry",inquiry);
	}

	//11/23 리스트받아오기
	public Map<String, Object> selectInquiryList(int page) {
		int cntPerPage = 5;
		Paging pageUtil = Paging.builder()
				.url("/inquiry/inquiry-list")
				.total(10)
				.curPage(page)
				.blockCnt(10)
				.cntPerPage(cntPerPage)
				.build();

		Map<String,Object> commandMap = new HashMap<String,Object>();
		commandMap.put("paging", pageUtil);
		commandMap.put("inquiryList", inquiryRepository.selectInquiryList(pageUtil));
		return commandMap;
	}

	@Override
	public void modifyInquiry(Inquiry inquiry) {
		inquiryRepository.modifyInquiry(inquiry);
		
	}

	@Override
	public void deleteInquiry(int inquiryIdx) {
		inquiryRepository.deleteInquiry(inquiryIdx);
		
	}















	
	

	
	
	
	
	
	
	
	
	
}
