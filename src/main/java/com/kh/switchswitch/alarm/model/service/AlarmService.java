package com.kh.switchswitch.alarm.model.service;

import java.util.List;

import com.kh.switchswitch.alarm.model.dto.Alarm;

public interface AlarmService {
	
	List<Alarm> selectAlarmListWithReceiverIdx(Integer receiverIdx);
	
	void insertAndUpdateAlarmList(List<Alarm> alarmList);

	void updateAlarm(Alarm alarm);
}
