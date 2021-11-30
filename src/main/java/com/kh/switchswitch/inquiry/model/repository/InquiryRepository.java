package com.kh.switchswitch.inquiry.model.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.kh.switchswitch.board.model.dto.Board;
import com.kh.switchswitch.common.util.FileDTO;
import com.kh.switchswitch.common.util.pagination.Paging;
import com.kh.switchswitch.inquiry.model.dto.Inquiry;

@Mapper
public interface InquiryRepository {

	//글작성
	@Insert("insert into inquiry(inquiry_idx, user_id, title, content,type)"
			+ " values(sc_inquiry_idx.nextval, #{userId}, #{title}, #{content}, #{type})")
	void insertInquiry(Inquiry inquiry);

	//상세글조회 
	@Select("select * from inquiry where inquiry_idx = #{inquiryIdx}")
	Inquiry selectInquiryByIdx(int inquiryIdx);
	
	//게시글목록
	@Select("select * from inquiry ORDER BY inquiry_idx DESC") 
	List<Inquiry> selectInquiryList(Paging pageUtil);

	//총 게시글 갯수 출력
	@Select("select count(*) from inquiry")
	int selectContentCnt();
	
	//수정
	@Update("update inquiry set title=#{title}, content=#{content}, reg_date=#{regDate}, type=#{type}")
	void modifyInquiry(Inquiry inquiry);
	
	@Update("update inquiry set is_del = 1 where inquiry_idx = #{inquiryIdx}")	
	void deleteInquiry(int inquiryIdx);
	
}
