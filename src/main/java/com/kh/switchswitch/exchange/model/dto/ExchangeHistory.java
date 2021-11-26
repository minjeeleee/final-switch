package com.kh.switchswitch.exchange.model.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ExchangeHistory {

	private Integer ehIdx;
	private Integer eIdx;
	private Date exchangeDate;
	private Integer userIdx1;
	private Integer userIdx2;
}
