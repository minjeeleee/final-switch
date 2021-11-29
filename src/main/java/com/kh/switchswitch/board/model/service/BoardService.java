package com.kh.switchswitch.board.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.kh.switchswitch.board.model.dto.Board;

public interface BoardService {
	//게시글등록
	void insertBoard(List<MultipartFile> files, Board board);
	//상세글조회
	Map<String, Object> selectBoardByIdx(int bdIdx);
	
	//게시글 목록
	Map<String,Object> selectBoardList(int page);

	//게시글 수정
	void modifyBoard(Board board, List<MultipartFile> files);

	void deleteBoard(int bdIdx);



}
