package com.kh.switchswitch.board.model.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.kh.switchswitch.board.model.dto.Board;

@Mapper
public interface BoardRepository {
	
	@Select("select * form community")
	public List<Board> getList();


}
