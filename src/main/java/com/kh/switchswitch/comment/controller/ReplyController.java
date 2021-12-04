package com.kh.switchswitch.comment.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kh.switchswitch.comment.model.service.ReplyService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("commnet")
public class ReplyController {
	
	private final ReplyService commentService;
	

	
}
