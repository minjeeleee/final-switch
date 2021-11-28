package com.kh.switchswitch.alarm.handler;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class AlarmHandler extends TextWebSocketHandler {
	
	@Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
		session.sendMessage(message);
    }
	
}
