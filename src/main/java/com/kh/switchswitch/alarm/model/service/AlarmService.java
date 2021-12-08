package com.kh.switchswitch.alarm.model.service;

import java.util.List;

import com.kh.switchswitch.alarm.model.dto.Alarm;

public interface AlarmService {
	
	List<Object> selectAlarmListWithReceiverIdx(Integer receiverIdx, int page);
	
	void insertAndUpdateAlarmList(List<Alarm> alarmList);

	void updateAlarm(Alarm alarm);
}
