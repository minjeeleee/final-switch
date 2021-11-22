package com.kh.switchswitch.member.model.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class Member {
	
	private int memberIdx;
	private String code;
	private Date memberRegDate;
	private String memberPass;
	private String memberEmail;
	private int memberDelYn;
	private String memberTell;
	private String memberNick;
	private String memberAddress;
	private Date memberDelDate;
	private int memberScore;
	private String memberName;
	
}
