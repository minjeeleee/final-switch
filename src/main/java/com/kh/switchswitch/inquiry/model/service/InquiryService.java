package com.kh.switchswitch.inquiry.model.service;

import java.util.Map;

import com.kh.switchswitch.inquiry.model.dto.Inquiry;

public interface InquiryService {
	//게시글등록
	void insertInquiry(Inquiry inquiry);
	//상세글조회
	Map<String, Object> selectInquiryByIdx(int inquiryIdx);	
	//게시글 목록
	Map<String,Object> selectInquiryList(int page);
	void modifyInquiry(Inquiry inquiry);
	void deleteInquiry(int inquiryIdx);


	



}
