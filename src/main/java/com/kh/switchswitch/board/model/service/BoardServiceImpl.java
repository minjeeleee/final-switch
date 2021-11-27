package com.kh.switchswitch.board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kh.switchswitch.board.model.dto.Board;
import com.kh.switchswitch.board.model.repository.BoardRepository;
import com.kh.switchswitch.common.util.FileDTO;
import com.kh.switchswitch.common.util.FileUtil;
import com.kh.switchswitch.common.util.pagination.Paging;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardServiceImpl implements BoardService{
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	private final BoardRepository boardRepository;
	
	public void insertBoard(List<MultipartFile> files, Board board) {
		FileUtil  fileUtil = new FileUtil();
		
		boardRepository.insertBoard(board);
		
		for (MultipartFile multipartFile : files) {
			if(!multipartFile.isEmpty()) {
				boardRepository.insertFileInfo(fileUtil.fileUpload(multipartFile));
			}
		}
	}

	public Map<String, Object> selectBoardByIdx(String bdIdx) {
		Board board = boardRepository.selectBoardByIdx(bdIdx);
		List<FileDTO> files = boardRepository.selectFilesByBdIdx(bdIdx);
		return Map.of("board",board,"files",files);
	}

	//11/23 리스트받아오기
	public Map<String, Object> selectBoardList(int page) {
		int cntPerPage = 5;
		Paging pageUtil = Paging.builder()
				.url("/board/board-list")
				.total(10)
				.curPage(page)
				.blockCnt(10)
				.cntPerPage(cntPerPage)
				.build();

		Map<String,Object> commandMap = new HashMap<String,Object>();
		commandMap.put("paging", pageUtil);
		commandMap.put("boardList", boardRepository.selectBoardList(pageUtil));
		return commandMap;
	}
	
	@Transactional
	public void modifyBoard(Board board, List<MultipartFile> files) {
		FileUtil  fileUtil = new FileUtil();
		boardRepository.modifyBoard(board);
		for (MultipartFile multipartFile : files) {
			if(!multipartFile.isEmpty()) {
				boardRepository.insertFileInfo(fileUtil.fileUpload(multipartFile));
			}
		}
	}











	
	

	
	
	
	
	
	
	
	
	
}
