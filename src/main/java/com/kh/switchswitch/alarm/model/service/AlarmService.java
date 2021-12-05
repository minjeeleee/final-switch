package com.kh.switchswitch.alarm.model.service;

import java.util.List;
import java.util.Map;

import com.kh.switchswitch.alarm.model.dto.Alarm;

public interface AlarmService {
	
	List<Map<String, Object>> selectAlarmListWithReceiverIdx(Integer receiverIdx);
	
	void insertAndUpdateAlarmList(List<Alarm> alarmList);

	void updateAlarm(Alarm alarm);
}
