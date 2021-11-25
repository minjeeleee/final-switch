package com.kh.switchswitch.exchange.model.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ExchangeHistory {

	private int ehIdx;
	private int eIdx;
	private Date exchangeDate;
	private int userIdx1;
	private int userIdx2;
}
