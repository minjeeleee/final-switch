package com.kh.switchswitch.alarm.model.dto;

import lombok.Data;

@Data
public class Alarm {
	
	private int alarmIdx;
	private int senderIdx;
	private int receiverIdx;
	private String alarmType;
	private int isRead;
	
}
