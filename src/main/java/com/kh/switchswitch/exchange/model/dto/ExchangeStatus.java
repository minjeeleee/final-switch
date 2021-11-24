package com.kh.switchswitch.exchange.model.dto;

import lombok.Data;

@Data
public class ExchangeStatus {
	
	private int eIdx;
	private String exchangeType;
	private int userIdx1;
	private int userIdx2;
	private String type;

}
