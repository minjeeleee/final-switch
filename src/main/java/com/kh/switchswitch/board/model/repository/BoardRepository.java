package com.kh.switchswitch.board.model.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.kh.switchswitch.board.model.dto.Board;
import com.kh.switchswitch.comment.model.dto.Reply;
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
	Board selectBoardByIdx(int bdIdx);
	
	 //게시글목록
	 @Select("select * from community where  is_del=0 ORDER BY bd_idx DESC")
	List<Board> selectBoardList(Paging pageUtil);

	//총 게시글 갯수 출력
	@Select("select count(*) from community")
	int selectContentCnt();

	//수정
	void modifyBoard(Board board);

	//파일업로드
	@Insert("insert into file_info(fl_idx,bd_idx,origin_file_name, rename_file_name, save_path)"
			+ " values(sc_file_idx.nextval, sc_bd_idx.currval, #{originFileName}, #{renameFileName}, #{savePath})")
	void insertFileInfo(FileDTO fileDTO);
	
	//파일다운
	@Select("select * from file_info where bd_idx = #{bdIdx}")
	List<FileDTO> selectFilesByBdIdx(int bdIdx);

	@Update("update community set is_del = 1 where bd_idx = #{bdIdx}")	
	void deleteBoard(int bdIdx);
	//파일업로드
	@Insert("insert into file_info(fl_idx,origin_file_name, rename_file_name, save_path,bd_idx)"
			+ " values(sc_file_idx.nextval, #{fileUpload.originFileName},#{fileUpload.renameFileName},#{fileUpload.savePath}, #{bdIdx})")
	void modifyFileInfo(@Param("fileUpload")FileDTO fileUpload, @Param("bdIdx")Integer bdIdx);
	
	@Select("select * from (select rownum rnum, BD_IDX,USER_ID,REG_DATE,TITLE,CONTENT,IS_DEL from community) community"
			+ " where rnum between #{startBoard} and #{lastBoard}")
	List<Board> selectBoardListWithPageNo(Map<String, Integer> map);

	@Select("SELECT * FROM reply WHERE BD_IDX = #{bdIdx}")
	List<Reply> getCommentList(Integer bdIdx);





	
}
