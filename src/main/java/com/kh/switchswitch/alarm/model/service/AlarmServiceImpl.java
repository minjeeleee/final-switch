package com.kh.switchswitch.alarm.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.switchswitch.alarm.model.dto.Alarm;
import com.kh.switchswitch.alarm.model.repository.AlarmRepository;
import com.kh.switchswitch.card.model.dto.CardRequestList;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlarmServiceImpl implements AlarmService {
	
	private final AlarmRepository alarmRepository;
	
	public List<Alarm> selectAlarmList(String receiverIdx) {
		return alarmRepository.selectAlarmList(receiverIdx);
	}

	public void insertAndUpdateAlarmList(List<Alarm> alarmList) {
		if(alarmList.size() != 0) {
			for (Alarm alarm : alarmList) {
				//int null 비교하려면 wrapper class 사용
				if(Integer.valueOf(alarm.getAlarmIdx()) == null) {
					alarmRepository.insertAlarm(alarm);
				} else {
					alarmRepository.updateAlarmIsRead(alarm);
				}
			}
		}
		
	}

	public void updateAlarm(Alarm alarm) {
		alarm.setIsRead(1);
		alarmRepository.updateAlarmIsRead(alarm);
	}

	public void sendAlarmWithStatus(CardRequestList cardRequestList, String type) {
		Alarm alarm = new Alarm();
		alarm.setAlarmType(type);
		alarm.setReceiverIdx(cardRequestList.getRequestMemIdx());
		alarm.setSenderIdx(cardRequestList.getRequestedMemIdx());
		alarm.setReqIdx(cardRequestList.getReqIdx());
		alarmRepository.insertAlarm(alarm);
		
	}

}
