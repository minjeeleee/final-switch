package com.kh.switchswitch.comment.model.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentRepository {
	/*
	 * @Insert("insert into community(bd_idx, user_id, title, content)" +
	 * " values(sc_bd_idx.nextval, #{userId}, #{title}, #{content})")
	 * 
	 * @Insert("insert into COMMENT(CM_IDX,BD_IDX,USER_ID,CONTENT)" +
	 * " values (SC_CM_IDX.nextval,366,'testID','테스트댓글') ") void insertBoard(Board
	 * board);
	 * 
	 * @Select("SELECT CM_IDX, USER_ID, CONTENT, REG_DATE FROM COMMENT WHERE BD_IDX = 366"
	 * ) Board selectBoardByIdx(int bdIdx);
	 */
}
