package com.kh.switchswitch.point.model.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class PointReturn {
	
	private Integer prIdx;
	private String userKey;
	private Integer field2;
	private Integer sysCode;
	private Integer sysType;
	private String sysKey;
	private String statueCode; 
	private Date registrationDate;

}
