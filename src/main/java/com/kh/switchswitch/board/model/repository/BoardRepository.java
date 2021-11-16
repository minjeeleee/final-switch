package com.kh.switchswitch.board.model.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.kh.switchswitch.board.model.dto.Board;

@Mapper
public interface BoardRepository {
	
	@Insert("insert into community(bd_idx, title, content, user_id, is_del, reg_date)"
			+ " values(sc_bd_idx.nextval, #{userId}, #{title}, #{content})")
	void insertBoard(Board board);

}
