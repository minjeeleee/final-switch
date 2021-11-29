package com.kh.switchswitch.notice.model.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.kh.switchswitch.common.util.FileDTO;
import com.kh.switchswitch.common.util.pagination.Paging;
import com.kh.switchswitch.inquiry.model.dto.Inquiry;
import com.kh.switchswitch.notice.model.dto.Notice;

@Mapper
public interface NoticeRepository {

	@Insert("insert into notice(notice_idx, user_id, title, content, type)"
			+ " values(sc_notice_idx.nextval, #{userId}, #{title}, #{content}, #{type})")
	void insertNotice(Notice notice);
	
	@Select("select * from notice where  is_del=0 ORDER BY notice_idx DESC")
	List<Notice>  selectNoticeList(Paging pageUtil);
	
	@Select("select * from notice where notice_idx = #{noticeIdx}")
	Notice selectNoticeByIdx(int noticeIdx);

	
	//게시글목록
	@Select("select * from inquiry ORDER BY inquiry_idx DESC") 
	List<Inquiry> selectInquiryList(Paging pageUtil);

	//총 게시글 갯수 출력
	@Select("select count(*) from inquiry")
	int selectContentCnt();

	//수정
	@Update("")
	void updateInquiry(Inquiry inquiry);
	
	@Delete("")
	void deleteInquiry(String inquiryIdx);


	

	

	




	
}
