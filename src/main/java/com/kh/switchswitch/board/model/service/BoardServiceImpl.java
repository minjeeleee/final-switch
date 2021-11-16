package com.kh.switchswitch.board.model.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.switchswitch.board.model.dto.Board;
import com.kh.switchswitch.board.model.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	private final BoardRepository boardRepository;
	@Override
	public void insertBoard(List<MultipartFile> mfs, Board board) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Map<String, Object> selectBoardByIdx(String bdIdx) {
		// TODO Auto-generated method stub
		return null;
	}
	

	
	

	
	
	
	
	
	
	
	
	
}
