package com.kh.switchswitch.point.model.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class SavePoint {
	
	private int puIdx;
	private int userKey;
	private int amtSubstraction;
	private int sysCode;
	private int sysType;
	private int sysKey;
	private int statueCode;
	private Date registrationDate;

}
