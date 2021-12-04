package com.kh.switchswitch.comment.model.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.kh.switchswitch.comment.model.dto.Reply;

@Mapper
public interface ReplyRepository {

	@Insert("insert into COMMENT(CM_IDX,BD_IDX,USER_ID,CONTENT)"
			+ " values (SC_CM_IDX.nextval,#{bdIdx},#{userId},#{content})")
	void insertComment(Reply commet);
	/*

	 * 
	 * @Insert("insert into COMMENT(CM_IDX,BD_IDX,USER_ID,CONTENT)" +
	 * " values (SC_CM_IDX.nextval,366,'testID','테스트댓글') ") void insertBoard(Board
	 * board);
	 * 
	 * @Select("SELECT CM_IDX, USER_ID, CONTENT, REG_DATE FROM COMMENT WHERE BD_IDX = 366"
	 * ) Board selectBoardByIdx(int bdIdx);
	 */

	@Select("SELECT * FROM COMMENT WHERE BD_IDX = #{bdIdx}")
	void selectCommentList(Integer bdIdx);
}
