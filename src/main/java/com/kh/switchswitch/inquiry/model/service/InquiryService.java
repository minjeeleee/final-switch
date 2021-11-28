package com.kh.switchswitch.inquiry.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.kh.switchswitch.inquiry.model.dto.Inquiry;

public interface InquiryService {
	//게시글등록
	void insertInquiry(Inquiry inquiry);
	//상세글조회
	Map<String, Object> selectInquiryByIdx(String inquiryIdx);	
	//게시글 목록
	Map<String,Object> selectInquiryList(int page);


	



}
