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
		System.out.println("#ChattingController, handleMessage");
		
		Alarm newAlarm = new Alarm();
		
		Member loginMember = memberRepository.selectMemberByEmailAndDelN(session.getPrincipal().getName());
        
        //protocol: 알람타입, 수신자IDX, 요청IDX
        String msg = message.getPayload();
        if(!StringUtils.isEmpty(msg)) {
        	String[] strs = msg.split(",");
        	String alarmType = strs[0];
        	Integer receiverIdx = Integer.parseInt(strs[1]);
        	Integer reqIdx = Integer.parseInt(strs[2]);
        	
        	newAlarm.setAlarmType(alarmType);
        	newAlarm.setSenderIdx(loginMember.getMemberIdx());
        	newAlarm.setReceiverIdx(receiverIdx);
    		newAlarm.setReqIdx(reqIdx);
			alarmRepository.insertAlarm(newAlarm);
			
			newAlarm.setAlarmIdx(alarmRepository.selectCurrScAlarmIdx());
			
        	WebSocketSession receiverSession =  userSessions.get(receiverIdx);
        	
        	if( receiverSession != null) {
				switch (alarmType) {
				case "교환요청":
					receiverSession.sendMessage(new TextMessage(reqIdx +"," + newAlarm.getAlarmIdx() +"," + loginMember.getMemberNick() + "님으로부터 교환 요청이 왔습니다."));
					break;
				case "요청거절":
					receiverSession.sendMessage(new TextMessage(reqIdx +"," + newAlarm.getAlarmIdx() +"," + loginMember.getMemberNick() + "님이 교환 요청을 거절하였습니다."));
					break;
				case "요청수락":
					receiverSession.sendMessage(new TextMessage(reqIdx +"," + newAlarm.getAlarmIdx() +"," + loginMember.getMemberNick() + "님이 교환 요청을 수락했습니다."));
					break;
				case "교환취소요청":
					receiverSession.sendMessage(new TextMessage(reqIdx +"," + newAlarm.getAlarmIdx() +","+ loginMember.getMemberNick() + "이 교환취소를 요청하였습니다."));
					break;
				case "교환취소요청거절":
					receiverSession.sendMessage(new TextMessage(reqIdx +"," + newAlarm.getAlarmIdx() +","+ loginMember.getMemberNick() + "이 교환취소요청을 거절하였습니다."));
					break;
				case "교환취소":
					receiverSession.sendMessage(new TextMessage(reqIdx +"," + newAlarm.getAlarmIdx() +"," + "교환취소가 완료되었습니다."));
					break;
				case "평점요청":
					receiverSession.sendMessage(new TextMessage(reqIdx +"," + newAlarm.getAlarmIdx() +"," + loginMember.getMemberNick() + "님과의 교환은 어떠셨나요?<br>"
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