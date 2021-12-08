package com.kh.switchswitch.notice.model.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.kh.switchswitch.common.util.pagination.Paging;
import com.kh.switchswitch.common.util.pagination.PagingV2;
import com.kh.switchswitch.notice.model.dto.Notice;

@Mapper
public interface NoticeRepository {

	@Insert("insert into notice(notice_idx, user_id, title, content, type)"
			+ " values(sc_notice_idx.nextval, #{userId}, #{title}, #{content}, #{type})")
	void insertNotice(Notice notice);
	
	@Select("select * from notice where  is_del=0 ORDER BY notice_idx DESC")
	List<Notice>  selectNoticeList(PagingV2 pageUtil);
	
	@Select("select * from notice where notice_idx = #{noticeIdx}")
	Notice selectNoticeByIdx(Integer noticeIdx);


	//총 게시글 갯수 출력
	@Select("select count(*) from notice where is_del = 0")
	int selectContentCnt();

	void modifyNotice(Notice notice);
	
	@Update("update notice set is_del = 1 where notice_idx = #{noticeIdx}")	
	void deleteNotice(Integer noticeIdx);

	@Select("select * from (select rownum rnum, noticer.* from (select notice.* from notice notice"
			+ " where is_del=0 order by REG_DATE DESC) noticer"
			+ " ) where rnum between #{startBoard} and #{lastBoard}")
	List<Notice> selectNoticeListWithPageNo(Map<String, Integer> map);



	

	

	




	
}
