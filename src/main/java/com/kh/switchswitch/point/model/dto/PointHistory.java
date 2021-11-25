package com.kh.switchswitch.point.model.dto;

import lombok.Data;

@Data
public class PointHistory {
	
	private int phIdx;
	private String userId;
	private int type;
	private int points;
	private int resultPoint;
	private int key;
	private int registrationDate;

}
