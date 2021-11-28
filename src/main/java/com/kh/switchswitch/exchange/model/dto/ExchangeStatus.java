package com.kh.switchswitch.exchange.model.dto;

import lombok.Data;

@Data
public class ExchangeStatus {
	
	private Integer eIdx;
	private Integer reqIdx;
	private Integer requestMemIdx;
	private Integer requestedMemIdx;
	private String type;
	private Integer propBalance;

}
