package com.kh.switchswitch.board.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.kh.switchswitch.board.model.dto.Board;
import com.kh.switchswitch.board.model.service.BoardService;
import com.kh.switchswitch.member.model.dto.MemberAccount;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("board")
public class BoardController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final BoardService boardService;
	
	@GetMapping("board-form")
	public void boardForm() {}

	@PostMapping("upload")
	public String uploadBoard(Board board, List<MultipartFile> files, @AuthenticationPrincipal MemberAccount member ) {
		//,@SessionAttribute("authentication") Member member
		board.setUserId(member.getMemberNick());
		boardService.insertBoard(files, board);
		return "redirect:/";
	}
	
	//11/23
	//list받아오기 성공
	//paging처리필요	
	  @GetMapping("board-list") 
	  public String boardList(Model model, @RequestParam(required = true, defaultValue = "1") int page) {
		  model.addAllAttributes(boardService.selectBoardList(page));
			return "board/board-list";
	}

	
	//11/25
	  //detail 받아오기 성공
	  //file다운기능 필요
	  @GetMapping("board-detail")
	public void boardDetail(int bdIdx, Model model) {
		Map<String,Object> commandMap = boardService.selectBoardByIdx(bdIdx);
		model.addAttribute("datas", commandMap);
	}
	
	@GetMapping("board-modify")
	public void boardModify(Model model, int bdIdx) {
		Map<String,Object> commandMap = boardService.selectBoardByIdx(bdIdx);
		model.addAttribute("datas", commandMap);
	}
	

	@PostMapping("modify")
	public String modifyBoard(Board board,  List<MultipartFile> files, int bdIdx) {
		System.out.println(board);
		for (MultipartFile multipartFile : files) {
			System.out.println(multipartFile);
		}
		board.setBdIdx(bdIdx);
		boardService.modifyBoard(board,files);
		return "redirect:/board/board-detail?bdIdx="+board.getBdIdx();
	}

	@PostMapping("delete")
	public String deleteBoard(int bdIdx) {
		boardService.deleteBoard(bdIdx);
		return "redirect:/";
	}

	


}

