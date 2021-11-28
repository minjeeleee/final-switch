package com.kh.switchswitch.notice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.kh.switchswitch.board.model.dto.Board;
import com.kh.switchswitch.member.model.dto.MemberAccount;
import com.kh.switchswitch.notice.model.dto.Notice;
import com.kh.switchswitch.notice.model.service.NoticeService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("notice")
public class NoticeController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final NoticeService noticeService;

	@GetMapping("notice-form")
	public void noticeForm() {}
	
	@PostMapping("upload")
	public String uploadBoard(Notice notice,  @AuthenticationPrincipal MemberAccount member ) {

		return "redirect:/";
	}
	
	
	
	
}
