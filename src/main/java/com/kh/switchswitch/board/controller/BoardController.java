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
	
	
	//11/17
	//list받아오기
	//paging처리
	@GetMapping("board-list")
	public void boardList(Model model, String bdIdx) {
		//model.addAttribute("board", boardService.findBoardByIdx(bdIdx));
	}
	
	@GetMapping("board-detail")
	public void boardDetail(String bdIdx, Model model) {
		Map<String,Object> commandMap = boardService.selectBoardByIdx(bdIdx);
		model.addAttribute("datas", commandMap);
	}
	
	@GetMapping("board-modify")
	public void boardModify() {}
	
	
	//11/17 500오류   수정필요
	@PostMapping("upload") 
	//NullPointerException
	//: Cannot invoke "com.kh.switchswitch.member.model.dto.Member.getMemberEmail()" because "member" is null
	public String uploadBoard(Board board,@SessionAttribute("authentication") Member member) {//required=false, name=
		board.setUserId(member.getMemberNick());
		boardService.insertBoard(board);
		return "redirect:/board/board-detail";
	}


}

