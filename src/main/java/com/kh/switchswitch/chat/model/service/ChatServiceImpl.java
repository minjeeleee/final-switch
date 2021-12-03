package com.kh.switchswitch.chat.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kh.switchswitch.chat.model.dto.ChatMessages;
import com.kh.switchswitch.chat.model.repository.ChatRepository;
import com.kh.switchswitch.member.model.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{
	
	private final ChatRepository chatRepository;
	private final MemberRepository memberRepository;

	public List<Map<String, Object>> selectChatMessageByChattingIdx(Integer chattingIdx,Integer memberIdx) {
		List<Map<String, Object>> chatMessageList = new ArrayList<Map<String,Object>>();
		List<ChatMessages> chatMessages = chatRepository.selectChatMessagesList(chattingIdx);
		for (ChatMessages chatMessage : chatMessages) {
			chatMessageList.add(
					Map.of("chatMessage",chatMessage
							,"senderName",memberRepository.selectMemberNickWithMemberIdx(chatMessage.getSenderId())));
			//목록 불러올 때 읽음 처리 되어 있지 않은 메세지들 전부 읽음 처리
			if(chatMessage.getIsRead() == 1 && chatMessage.getSenderId() != memberIdx) {
				chatRepository.updateChatIsRead(chatMessage.getCmIdx());
			}
		}
		return chatMessageList;
	}
	
	
}
