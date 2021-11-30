package com.kh.switchswitch.alarm.model.service;

import java.util.List;

import com.kh.switchswitch.alarm.model.dto.Alarm;
import com.kh.switchswitch.card.model.dto.CardRequestList;

public interface AlarmService {
	
	List<Alarm> selectAlarmList(Integer receiverIdx);
	
	void insertAndUpdateAlarmList(List<Alarm> alarmList);

	void updateAlarm(Alarm alarm);

	void sendAlarmWithStatus(CardRequestList cardRequestList, String type);
}
