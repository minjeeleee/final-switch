package com.kh.switchswitch.board.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import com.kh.switchswitch.board.model.dto.Board;

import com.kh.switchswitch.board.model.service.BoardService;
import com.kh.switchswitch.member.model.dto.Member;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("board")
public class BoardController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final BoardService boardService;
	
	@GetMapping("board-form")
	public void boardForm() {}

	//11/18 서버 테스트 통과
	//11/18 파일 추가중
	@PostMapping("upload")
	public String uploadBoard(Board board, List<MultipartFile> files) {
		// ,@SessionAttribute("authentication") Member member
		board.setUserId("userId");
		boardService.insertBoard(files, board);
		return "redirect:/";
	}
	
	//11/21 수정필요
	//list받아오기
	//paging처리필요	
	  @GetMapping("board-list") public void boardList(Model model, String bdIdx) {
	  //model.addAttribute("board", boardService.findBoardByIdx(bdIdx)); 
	}

	
	//11/21 수정필요
	  //ERROR: org.thymeleaf.TemplateEngine - [THYMELEAF][main] 
	  //Exception processing template "board/board-detail": Exception evaluating SpringEL expression: "title" (template: "board/board-detail" - line 43, col 15)
	@GetMapping("board-detail")
	public void boardDetail(String bdIdx, Model model) {
		Map<String,Object> commandMap = boardService.selectBoardByIdx(bdIdx);
		model.addAttribute("datas", commandMap);
	}
	
	@GetMapping("board-modify")
	public void boardModify() {}
	
	


}

