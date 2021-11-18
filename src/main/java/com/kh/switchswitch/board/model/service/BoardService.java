package com.kh.switchswitch.board.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.kh.switchswitch.board.model.dto.Board;

public interface BoardService {
	
	void insertBoard(List<MultipartFile> files, Board board);
	
	Map<String, Object> selectBoardByIdx(String bdIdx);



}
