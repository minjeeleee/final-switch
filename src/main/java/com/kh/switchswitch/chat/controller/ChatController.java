package com.kh.switchswitch.chat.controller;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.switchswitch.chat.model.service.ChatService;
import com.kh.switchswitch.member.model.dto.MemberAccount;

import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("chat")
@RequiredArgsConstructor
public class ChatController {

	private final ChatService chatService;
	
	@GetMapping("chat")
	public void chat(Integer chattingIdx,Model model,@AuthenticationPrincipal MemberAccount member) {
		System.out.println(chattingIdx);
		List<Map<String,Object>> chatMessageList = chatService.selectChatMessageByChattingIdx(chattingIdx,member.getMemberIdx());
		System.out.println(chatMessageList);
		model.addAttribute("chatMessageList", chatMessageList);
	}
	
	@GetMapping("chatting-list")
	public void chattingList(@AuthenticationPrincipal MemberAccount member,Model model) {
		//채팅방 목록 불러오기
		model.addAttribute("chattingList", chatService.selectAllChattingList(member.getMemberIdx()));
	}
	
}
