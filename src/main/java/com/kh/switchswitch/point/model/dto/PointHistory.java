package com.kh.switchswitch.point.model.dto;

import lombok.Data;

@Data
public class PointHistory {
	
	private Integer phIdx;
	private String userId;
	private Integer type;
	private Integer points;
	private Integer resultPoint;
	private Integer key;
	private Integer registrationDate;

}
