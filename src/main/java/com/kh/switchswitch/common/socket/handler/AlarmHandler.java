package com.kh.switchswitch.common.socket.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class AlarmHandler extends TextWebSocketHandler {
	
	private static List<WebSocketSession> sessionList = new ArrayList<WebSocketSession>();
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		
		System.out.println("#ChattingController, afterConnectionEstablished");
		sessionList.add(session);
        System.out.println(session.getId() + "님이 입장하셨습니다.");
	}
	
	@Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("#ChattingController, handleMessage");
        System.out.println(message);
        
        for (WebSocketSession s : sessionList) {
			s.sendMessage(new TextMessage(message.getPayload()));
		}
    }
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println("#ChattingController, afterConnectionClosed");
		sessionList.remove(session);
        System.out.println(session.getId() + "님이 퇴장하셨습니다.");
	}
}