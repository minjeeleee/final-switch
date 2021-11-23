package com.kh.switchswitch.board.model.service;

import java.util.HashMap;
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
import com.kh.switchswitch.common.util.pagination.Paging;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	private final BoardRepository boardRepository;
	
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
	
	//11/17
	@Override
	public Map<String, Object> selectBoardByIdx(String bdIdx) {
		Board board = boardRepository.selectBoardByIdx(bdIdx);
		List<FileDTO> files = boardRepository.selectFilesByBdIdx(bdIdx);
		return Map.of("board",board,"files",files);
	}

	//11/23 리스트받아오기
	@Override
	public Map<String, Object> selectBoardList(int page) {
		Paging paging = Paging.builder()
				.cuurentPage(page)
				.blockCnt(5)
				.cntPerPage(10)
				.type("board")
				.sort("bd_idx")
				.direction("desc")
				.build();
		
		Map<String,Object> commandMap = new HashMap<String,Object>();
		commandMap.put("paging", paging);
		commandMap.put("boardList", boardRepository.selectBoardList(paging));
		return commandMap;
	}









	
	

	
	
	
	
	
	
	
	
	
}
