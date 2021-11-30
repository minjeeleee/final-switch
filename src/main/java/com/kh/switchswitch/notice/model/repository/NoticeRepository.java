package com.kh.switchswitch.notice.model.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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


	//총 게시글 갯수 출력
	@Select("select count(*) from notice")
	int selectContentCnt();

	@Update("update notice set title=#{title}, content=#{content}, reg_date=#{regDate}, type=#{type}")
	void modifyNotice(Notice notice);

	@Update("update notice set is_del = 1 where notice_idx = #{noticeIdx.noticeIdx}")	
	void deleteNotice(@Param("noticeIdx")int noticeIdx);


	

	

	




	
}
