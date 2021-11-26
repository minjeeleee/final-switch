package com.kh.switchswitch.alarm.model.dto;

import lombok.Data;

@Data
public class Alarm {
	
	private Integer alarmIdx;
	private Integer reqIdx;
	private Integer senderIdx;
	private Integer receiverIdx;
	private String alarmType;
	private Integer isRead;
	
}
