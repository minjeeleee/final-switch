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

@Mapper
public interface NoticeRepository {

	//글작성
	@Insert("insert into inquiry(inquiry_idx, user_id, title, content,type)"
			+ " values(sc_inquiry_idx.nextval, #{userId}, #{title}, #{content}, #{type})")
	void insertInquiry(Inquiry inquiry);

	//상세글조회 
	@Select("select * from inquiry where inquiry_idx = #{inquiryIdx}")
	Inquiry selectInquiryByIdx(String inquiryIdx);
	
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
	
	
	//파일업로드
	@Insert("insert into file_info(fl_idx,bd_idx,origin_file_name, rename_file_name, save_path)"
			+ " values(sc_file_idx.nextval, sc_bd_idx.currval, #{originFileName}, #{renameFileName}, #{savePath})")
	void insertFileInfo(FileDTO fileDTO);
	
	//파일다운
	@Select("select * from inquiry where inquiryIdx = #{inquiryIdx}")
	List<FileDTO> selectFilesByInquiryIdx(String inquiryIdx);

	//수정
	@Update("")
	Map<String, Object> inquiryModify(Inquiry inquiry);

	

	

	




	
}
