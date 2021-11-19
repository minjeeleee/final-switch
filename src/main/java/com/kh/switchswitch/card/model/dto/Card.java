package com.kh.switchswitch.card.model.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Card {
	
	private int cardIdx;
	private int memberIdx;
	private int  eIdx;
	private String category;
	private String name;
	private int condition;
	private String deliveryCharge;
	private LocalDate regDate;
	private String isfree;
	private String exchangeStatus;
	private String content;
	private String region;
	private int views;
	private String regionDetail;
	private String method;
	private int isDel;

}
