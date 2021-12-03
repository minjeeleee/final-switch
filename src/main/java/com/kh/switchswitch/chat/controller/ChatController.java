package com.kh.switchswitch.chat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("chat")
public class ChatController {

	@GetMapping("chat")
	public void chat(@RequestParam(name = "chattingIdx")Integer chattingIdx) {
		System.out.println(chattingIdx);
	}
	
	@GetMapping("chatting-list")
	public void chattingList() {}
	
}
