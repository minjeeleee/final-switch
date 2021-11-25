package com.kh.switchswitch.point.model.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class PointReturn {
	
	private int prIdx;
	private String userKey;
	private int field2;
	private int sysCode;
	private int sysType;
	private String sysKey;
	private String statueCode; 
	private Date registrationDate;

}
