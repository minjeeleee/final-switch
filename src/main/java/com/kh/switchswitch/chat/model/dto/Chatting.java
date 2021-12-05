package com.kh.switchswitch.chat.model.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class Chatting {

	private Integer ChattingIdx;
	private Integer Attendee1;
	private Integer Attendee2;
	private Date createAt;
}
