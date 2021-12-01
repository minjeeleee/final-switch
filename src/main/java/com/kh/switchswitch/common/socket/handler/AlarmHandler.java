package com.kh.switchswitch.common.socket.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.kh.switchswitch.alarm.model.dto.Alarm;
import com.kh.switchswitch.alarm.model.repository.AlarmRepository;
import com.kh.switchswitch.member.model.dto.Member;
import com.kh.switchswitch.member.model.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AlarmHandler extends TextWebSocketHandler {
	
	private final AlarmRepository alarmRepository;
	private final MemberRepository memberRepository;
	
	private static List<WebSocketSession> sessionList = new ArrayList<WebSocketSession>();
	private static Map<Integer, WebSocketSession> userSessions = new HashMap<>();
	private static Map<Integer, List<Alarm>> alarmList = new HashMap<>();
	
	private int tempAlarmIdx = 0;
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		
		Member loginMember = memberRepository.selectMemberByEmailAndDelN(session.getPrincipal().getName());
		
		System.out.println("#AlarmController, afterConnectionEstablished");
		
		sessionList.add(session);
		
		userSessions.put(loginMember.getMemberIdx(), session);
		
		
		alarmList.put(loginMember.getMemberIdx(), new ArrayList<Alarm>());
		List<Alarm> memberAlarmList = alarmRepository.selectAlarmList(loginMember.getMemberIdx());
		if(memberAlarmList != null) {
			for (Alarm alarm : memberAlarmList) {
				alarmList.get(loginMember.getMemberIdx()).add(alarm);
			}
		}
		
		
	}

	@Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		Member loginMember = memberRepository.selectMemberByEmailAndDelN(session.getPrincipal().getName());
		
		System.out.println("#ChattingController, handleMessage");
        
        //protocol: 알람타입orisRead, alarmIDX, 수신자IDX, 요청IDX
        String msg = message.getPayload();
        if(!StringUtils.isEmpty(msg)) {
        	String[] strs = msg.split(",");
        	String alarmTypeOrisRead = strs[0];
        	Integer alarmIdx = strs[1].equals("") ? null : Integer.parseInt(strs[1]);
        	Integer senderIdx = loginMember.getMemberIdx() ;
        	Integer receiverIdx = Integer.parseInt(strs[2]) ;
        	Integer reqIdx = Integer.parseInt(strs[3]);
        	
        	Alarm newAlarm = new Alarm();
        	newAlarm.setAlarmIdx(--tempAlarmIdx);
        	newAlarm.setAlarmType(alarmTypeOrisRead);
        	newAlarm.setSenderIdx(senderIdx);
        	newAlarm.setReceiverIdx(receiverIdx);
    		newAlarm.setReqIdx(reqIdx);
        	
        	WebSocketSession receiverSession =  userSessions.get(receiverIdx);
        	
        	if( receiverSession == null) {
        		switch(alarmTypeOrisRead) {
        		case "read" : 
        			Alarm updateAlarm = new Alarm();
        			updateAlarm.setAlarmIdx(alarmIdx);
        			updateAlarm.setIsRead(1);
        			alarmRepository.updateAlarmIsRead(updateAlarm);
        		default : alarmRepository.insertAlarm(newAlarm);
        			
        		}
        	} else {
        		if("read".equals(alarmTypeOrisRead)) {
            		for (Alarm alarm : alarmList.get( loginMember.getMemberIdx())) {
    					if(alarmIdx == alarm.getAlarmIdx()) {
    						alarm.setIsRead(1);
    					}
    				} 
            	} else {
            		alarmList.get(receiverIdx).add(newAlarm);
            		switch(alarmTypeOrisRead) {
            		case "교환요청" : receiverSession.sendMessage(new TextMessage(loginMember.getMemberNick() + "님으로부터 교환 요청이 왔습니다.")); break;
            		case "요청거절" : receiverSession.sendMessage(new TextMessage(loginMember.getMemberNick() + "님이 교환 요청을 거절하였습니다.")); break;
            		case "요청수락" : receiverSession.sendMessage(new TextMessage(loginMember.getMemberNick() + "님이 교환 요청을 수락했습니다.")); break;
            		case "교환확정" : receiverSession.sendMessage(new TextMessage(loginMember.getMemberNick() + "님이 교환을 확정했습니다.")); break;
            		case "교환취소" : receiverSession.sendMessage(new TextMessage(loginMember.getMemberNick() + "님이 교환을 취소했습니다.")); break;
            		case "평점요청" : receiverSession.sendMessage(new TextMessage(
            				loginMember.getMemberNick() + "님과의 교환은 어떠셨나요?<br>" 
            				+loginMember.getMemberNick()+ "에 대한 평점을 남겨주세요.")); break;
            		}
            	}
        	}
        	
        }
        
    }
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		Member loginMember = memberRepository.selectMemberByEmailAndDelN(session.getPrincipal().getName());
		
		System.out.println("#ChattingController, afterConnectionClosed");
		
		if(alarmList.get(loginMember.getMemberIdx()) != null) {
			for (Alarm alarm : alarmList.get(loginMember.getMemberIdx())) {
				if(alarm.getAlarmIdx() < 0) {
					alarmRepository.insertAlarm(alarm);
				} else {
					alarmRepository.updateAlarmIsRead(alarm);
				}
			}
		}
		
		alarmList.remove(loginMember.getMemberIdx());
		
		userSessions.remove(loginMember.getMemberIdx());
		
		sessionList.remove(session);
	}
}