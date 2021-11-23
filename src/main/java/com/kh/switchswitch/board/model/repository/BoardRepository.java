package com.kh.switchswitch.board.model.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.kh.switchswitch.board.model.dto.Board;
import com.kh.switchswitch.common.util.FileDTO;
import com.kh.switchswitch.common.util.pagination.Paging;

@Mapper
public interface BoardRepository {

	//글작성
	@Insert("insert into community(bd_idx, user_id, title, content)"
			+ " values(sc_bd_idx.nextval, #{userId}, #{title}, #{content})")
	void insertBoard(Board board);

	//상세글조회 
	@Select("select * from community where bd_idx = #{bdIdx}")
	Board selectBoardByIdx(String bdIdx);
	
	//게시글목록
	@Select("select * from community ORDER BY bd_idx DESC") 
	List<Board> selectBoardList(Paging pageUtil);

	//총 게시글 갯수 출력
	@Select("select count(*) from community")
	int selectContentCnt();

	
	
	//수정
	@Update("")
	void updateBoard(Board board);
	
	@Delete("")
	void deleteBoard(Board board);
	
	
	//파일업로드
	@Insert("insert into file_info(fl_idx,bd_idx,origin_file_name, rename_file_name, save_path)"
			+ " values(sc_file_idx.nextval, sc_bd_idx.currval, #{originFileName}, #{renameFileName}, #{savePath})")
	void insertFileInfo(FileDTO fileDTO);
	
	//파일다운
	@Select("select * from file_info where bd_idx = #{bdIdx}")
	List<FileDTO> selectFilesByBdIdx(String bdIdx);


	
}
