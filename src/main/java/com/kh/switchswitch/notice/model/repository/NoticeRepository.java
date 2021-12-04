package com.kh.switchswitch.notice.model.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.kh.switchswitch.common.util.pagination.Paging;
import com.kh.switchswitch.notice.model.dto.Notice;

@Mapper
public interface NoticeRepository {

	@Insert("insert into notice(notice_idx, user_id, title, content, type)"
			+ " values(sc_notice_idx.nextval, #{userId}, #{title}, #{content}, #{type})")
	void insertNotice(Notice notice);
	
	@Select("select * from notice where  is_del=0 ORDER BY notice_idx DESC")
	List<Notice>  selectNoticeList(Paging pageUtil);
	
	@Select("select * from notice where notice_idx = #{noticeIdx}")
	Notice selectNoticeByIdx(Integer noticeIdx);


	//총 게시글 갯수 출력
	@Select("select count(*) from notice")
	int selectContentCnt();

	void modifyNotice(Notice notice);
	
	@Update("update notice set is_del = 1 where notice_idx = #{noticeIdx}")	
	void deleteNotice(Integer noticeIdx);

	@Select("select * from (select rownum rnum, NOTICE_IDX,USER_ID,REG_DATE,TITLE,CONTENT,IS_DEL,TYPE from NOTICE) NOTICE"
			+ " where rnum between #{startBoard} and #{lastBoard}")
	List<Notice> selectNoticeListWithPageNo(Map<String, Integer> map);



	

	

	




	
}
