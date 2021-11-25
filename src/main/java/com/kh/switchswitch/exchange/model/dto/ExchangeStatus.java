package com.kh.switchswitch.exchange.model.dto;

import lombok.Data;

@Data
public class ExchangeStatus {
	
	private Integer eIdx;
	private Integer reqIdx;
	private Integer userIdx1;
	private Integer userIdx2;
	private String type;
	private Integer propBalance;

}
