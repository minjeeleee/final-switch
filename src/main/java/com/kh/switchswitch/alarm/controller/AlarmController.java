package com.kh.switchswitch.alarm.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

//@Controller
//@RequestMapping("alarm")
public class AlarmController extends TextWebSocketHandler {
	
    private SimpMessagingTemplate simpMessagingTemplate;
	
    @Autowired
    public void setMessagingTemplate(SimpMessagingTemplate simpMessagingTemplate) {
    	this.simpMessagingTemplate = simpMessagingTemplate;
    }
    
	private static List<WebSocketSession> sessionList = new ArrayList<WebSocketSession>();
	
	//@GetMapping("alarmList")
	//public void alarmList(@AuthenticationPrincipal MemberAccount certifiedMember, @Payload ... ...) {
	//	simpMessagingTemplate.convertAndSendToUser(certifiedMember.getMemberEmail(), "send/alarm", ...);
	//}
	
	//@Scheduled(cron="*/5 * * * * *")     //5초마다 수행하도록 설정
    //public void checkNotice(){
    //    logger.info("checkNotice call");
	//    try{
	//    	simpMessagingTemplate.setMessageConverter(new StringMessageConverter());
	//    	simpMessagingTemplate.convertAndSend("/subscribe/notice", new Date().toString() + " : 이것은 서버 메시지이빈다.");
	//
	//   }catch(Exception ex){
	//       logger.error(ex.getMessage());
	//    }
	//}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		
		System.out.println("#ChattingController, afterConnectionEstablished");
		sessionList.add(session);
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
	}

}
