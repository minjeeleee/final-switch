package com.kh.switchswitch.common.socket.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.switchswitch.alarm.model.dto.Alarm;
import com.kh.switchswitch.alarm.model.repository.AlarmRepository;
import com.kh.switchswitch.chat.model.dto.ChatMessages;
import com.kh.switchswitch.chat.model.repository.ChatRepository;
import com.kh.switchswitch.member.model.dto.Member;
import com.kh.switchswitch.member.model.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChattingHandler extends TextWebSocketHandler {
	
	private List<Map<String, Object>> sessionList = new ArrayList<Map<String, Object>>();
	private final MemberRepository memberRepository;
	private final ChatRepository chatRepository;

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		
		Member loginMember = memberRepository.selectMemberByEmailAndDelN(session.getPrincipal().getName());
		
		System.out.println("#AlarmController, afterConnectionEstablished");
		
	}
	// 클라이언트가 서버로 메세지 전송 처리
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		
		super.handleTextMessage(session, message);
		Member loginMember = memberRepository.selectMemberByEmailAndDelN(session.getPrincipal().getName());
		// JSON --> Map으로 변환
		ObjectMapper objectMapper = new ObjectMapper();
		
		//채팅 이력 불러오기
		List<ChatMessages> memberChatMessagesList = chatRepository.selectChatMessagesList(1);
		System.out.println("memberChatMessagesList : "+memberChatMessagesList);
		if(memberChatMessagesList != null) {
			for (ChatMessages chatMessages : memberChatMessagesList) {
				//회원 아이디도 담아서 전송 => 수정 해야함
				String cmd = "";
				System.out.println(chatMessages.getSenderId());
				System.out.println(loginMember.getMemberIdx());
				if(chatMessages.getSenderId() == loginMember.getMemberIdx()) cmd = "CMD_MSG_SEND";
				else cmd = "CMD_ENTER";
				String jsonStr = objectMapper.writeValueAsString(Map.of("bang_id", chatMessages.getChattingIdx()
						,"cmd",cmd,"msg",memberRepository.selectMemberNickWithMemberIdx(chatMessages.getSenderId())
						+ " : " +chatMessages.getMessage()));
				session.sendMessage(new TextMessage(jsonStr));
			}
		}
		//새로운 채팅
		Map<String, String> mapReceive = objectMapper.readValue(message.getPayload(), Map.class);
		System.out.println(mapReceive);
		switch (mapReceive.get("cmd")) {
		// CLIENT 입장
		case "CMD_ENTER":
			// 세션 리스트에 저장
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("memberIdx", loginMember.getMemberIdx());
			map.put("bang_id", mapReceive.get("bang_id"));
			map.put("session", session);
			sessionList.add(map);
			break;
			
		// CLIENT 메세지
		case "CMD_MSG_SEND":
			System.out.println(sessionList);
			// 같은 채팅방에 메세지 전송
			for (int i = 0; i < sessionList.size(); i++) {
				Map<String, Object> mapSessionList = sessionList.get(i);
				String bang_id = (String) mapSessionList.get("bang_id");
				WebSocketSession sess = (WebSocketSession) mapSessionList.get("session");
				
				if (bang_id.equals(mapReceive.get("bang_id"))) {
					//여기서도 보낼떄 회원 아이디 추가
					Map<String, String> mapToSend = new HashMap<String, String>();
					mapToSend.put("bang_id", bang_id);
					mapToSend.put("cmd", "CMD_MSG_SEND");
					mapToSend.put("msg"
									, memberRepository.selectMemberNickWithMemberIdx((Integer)mapSessionList.get("memberIdx")) 
									+ " : " + mapReceive.get("msg"));
					
					if(loginMember.getMemberIdx().equals(mapSessionList.get("memberIdx"))) {
						ChatMessages chatMessages = new ChatMessages();
						chatMessages.setChattingIdx(Integer.parseInt(bang_id));
						chatMessages.setMessage(mapReceive.get("msg"));
						chatMessages.setSenderId(loginMember.getMemberIdx());
						chatRepository.insertChatMessage(chatMessages);
					}
					
					String jsonStr = objectMapper.writeValueAsString(mapToSend);
					sess.sendMessage(new TextMessage(jsonStr));
				}
			}
			
			break;
		}
	}

	// 클라이언트가 연결을 끊음 처리
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

		super.afterConnectionClosed(session, status);
        
		ObjectMapper objectMapper = new ObjectMapper();
		String now_bang_id = "";
		
		// 사용자 세션을 리스트에서 제거
		for (int i = 0; i < sessionList.size(); i++) {
			Map<String, Object> map = sessionList.get(i);
			String bang_id = (String) map.get("bang_id");
			WebSocketSession sess = (WebSocketSession) map.get("session");
			
			if(session.equals(sess)) {
				now_bang_id = bang_id;
				sessionList.remove(map);
				break;
			}	
		}
		
		
	}
}
