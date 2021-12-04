package com.kh.switchswitch.common.socket.handler;

import java.util.HashMap;
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
	
	private static Map<Integer, WebSocketSession> userSessions = new HashMap<>();
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		
		Member loginMember = memberRepository.selectMemberByEmailAndDelN(session.getPrincipal().getName());
		
		System.out.println("#AlarmController, afterConnectionEstablished");
		
		userSessions.put(loginMember.getMemberIdx(), session);
		
	}

	@Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		
		Alarm newAlarm = new Alarm();
		
		Member loginMember = memberRepository.selectMemberByEmailAndDelN(session.getPrincipal().getName());
		
		System.out.println("#ChattingController, handleMessage");
        
        //protocol: 알람타입orisRead, alarmIDX, 수신자IDX, 요청IDX
        String msg = message.getPayload();
        if(!StringUtils.isEmpty(msg)) {
        	String[] strs = msg.split(",");
        	String alarmTypeOrisRead = strs[0];
        	Integer alarmIdx = strs[1].equals("") ? null : Integer.parseInt(strs[1]);
        	Integer receiverIdx = Integer.parseInt(strs[2]);
        	
    		if(alarmTypeOrisRead.equals("read")) {
    			Alarm updateAlarm = new Alarm();
    			updateAlarm.setAlarmIdx(alarmIdx);
    			updateAlarm.setIsRead(1);
    			alarmRepository.updateAlarmIsRead(updateAlarm);
    		} else {
            	newAlarm.setAlarmType(alarmTypeOrisRead);
            	newAlarm.setSenderIdx(loginMember.getMemberIdx());
            	newAlarm.setReceiverIdx(receiverIdx);
        		newAlarm.setReqIdx(Integer.parseInt(strs[3]));
    			alarmRepository.insertAlarm(newAlarm);
    			
    			newAlarm.setAlarmIdx(alarmRepository.selectCurrScAlarmIdx());
    		}
        	
        	WebSocketSession receiverSession =  userSessions.get(receiverIdx);
        	
        	if( receiverSession != null) {
				switch (alarmTypeOrisRead) {
				case "교환요청":
					receiverSession.sendMessage(new TextMessage(newAlarm.getAlarmIdx() +"," + loginMember.getMemberNick() + "님으로부터 교환 요청이 왔습니다."));
					break;
				case "요청거절":
					receiverSession.sendMessage(new TextMessage(newAlarm.getAlarmIdx() +"," + loginMember.getMemberNick() + "님이 교환 요청을 거절하였습니다."));
					break;
				case "요청수락":
					receiverSession.sendMessage(new TextMessage(newAlarm.getAlarmIdx() +"," + loginMember.getMemberNick() + "님이 교환 요청을 수락했습니다."));
					break;
				case "교환확정":
					receiverSession.sendMessage(new TextMessage(newAlarm.getAlarmIdx() +"," + loginMember.getMemberNick() + "님이 교환을 확정했습니다."));
					break;
				case "교환취소":
					receiverSession.sendMessage(new TextMessage(newAlarm.getAlarmIdx() +"," + loginMember.getMemberNick() + "님이 교환을 취소했습니다."));
					break;
				case "평점요청":
					receiverSession.sendMessage(new TextMessage(newAlarm.getAlarmIdx() +"," + loginMember.getMemberNick() + "님과의 교환은 어떠셨나요?<br>"
							+ loginMember.getMemberNick() + "에 대한 평점을 남겨주세요."));
					break;
				}
        	}
        }
    }
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		Member loginMember = memberRepository.selectMemberByEmailAndDelN(session.getPrincipal().getName());
		
		System.out.println("#ChattingController, afterConnectionClosed");
		
		userSessions.remove(loginMember.getMemberIdx());
		
	}
}