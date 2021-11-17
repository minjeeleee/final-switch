package com.kh.switchswitch.board.model.service;

import java.util.Map;

import com.kh.switchswitch.board.model.dto.Board;

public interface BoardService {
	
	void insertBoard(Board board);
	
	Map<String, Object> selectBoardByIdx(String bdIdx);


}
