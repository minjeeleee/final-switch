package com.kh.switchswitch.board.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.switchswitch.board.model.dto.Board;
import com.kh.switchswitch.board.model.repository.BoardRepository;
import com.kh.switchswitch.common.util.FileDTO;
import com.kh.switchswitch.common.util.FileUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	private final BoardRepository boardRepository;
	

	
	//11/17
	@Override
	public Map<String, Object> selectBoardByIdx(String bdIdx) {
		Board board = boardRepository.selectBoardByIdx(bdIdx);
		/* List<FileDTO> files = boardRepository.selectFilesByBdIdx(bdIdx); */
		return Map.of("board",board);
	}

	@Override
	public void insertBoard(List<MultipartFile> files, Board board) {
		FileUtil  fileUtil = new FileUtil();
		
		boardRepository.insertBoard(board);
		
		for (MultipartFile multipartFile : files) {
			if(!multipartFile.isEmpty()) {
				boardRepository.insertFileInfo(fileUtil.fileUpload(multipartFile));
			}
		}
	}



	
	

	
	
	
	
	
	
	
	
	
}
