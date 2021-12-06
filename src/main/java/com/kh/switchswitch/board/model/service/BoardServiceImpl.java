package com.kh.switchswitch.board.model.service;

import java.util.ArrayList;
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
import com.kh.switchswitch.comment.model.dto.Reply;
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
	
	public void insertBoard(List<MultipartFile> imgList, Board board) {
		FileUtil  fileUtil = new FileUtil();
		
		boardRepository.insertBoard(board);
		
		for (MultipartFile multipartFile : imgList) {
			if(!multipartFile.isEmpty()) {
				boardRepository.insertFileInfo(fileUtil.fileUpload(multipartFile));
			}
		}
	}

	public Map<String, Object> selectBoardByIdx(int bdIdx) {
		Board board = boardRepository.selectBoardByIdx(bdIdx);
		List<FileDTO> files = boardRepository.selectFilesByBdIdx(bdIdx);
		return Map.of("board",board,"files",files);
	}

	//11/23 리스트받아오기
	public Map<String, Object> selectBoardList(int page) {
		int cntPerPage = 10;
		Paging pageUtil = Paging.builder()
				.url("/board/board-list")
				.total(boardRepository.selectContentCnt())
				.curPage(page)
				.blockCnt(10)
				.cntPerPage(cntPerPage)
				.build();

		Map<String,Object> commandMap = new HashMap<String,Object>();
		commandMap.put("paging", pageUtil);
		commandMap.put("boardList", boardRepository.selectBoardListWithPageNo(Map.of("startBoard",(page-1)*cntPerPage+1,"lastBoard",(page-1)*cntPerPage+cntPerPage)));
		return commandMap;
	}
	
	@Transactional
	public void modifyBoard(Board board, List<MultipartFile> files) {
		FileUtil  fileUtil = new FileUtil();
		boardRepository.modifyBoard(board);

		
		for (MultipartFile multipartFile : files) {
			if(!multipartFile.isEmpty()) {
				boardRepository.modifyFileInfo(fileUtil.fileUpload(multipartFile),board.getBdIdx());
			}
		}
	}
	

	public void deleteBoard(int bdIdx) {
		boardRepository.deleteBoard(bdIdx);
		
	}

	public List<Reply> getCommetList(Map<String, Object> commandMap) {
		 List<Reply> commentList = boardRepository.getCommentList(((Board)commandMap.get("board")).getBdIdx());
		return commentList;
	    }

	public void boardReplyInsert(Reply reply) {
		
		int lastOrder = boardRepository.selectLastOrderOfBoard(reply.getBdIdx());		
		reply.setCmOrder(lastOrder+1);	
		boardRepository.insertReplyDepth1(reply);
		
		
	}

	public void modifyReply(Reply reply) {
		boardRepository.modifyReply(reply);
	}

	public void deleteReply(int cmIdx) {
		boardRepository.deleteReply(cmIdx);
		
	}












	
	

	
	
	
	
	
	
	
	
	
}
