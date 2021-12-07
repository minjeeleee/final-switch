package com.kh.switchswitch.exchange.model.service;

import com.kh.switchswitch.exchange.model.dto.ExchangeStatus;

public interface RatingService {

	void createRating(ExchangeStatus exchangeStatus, Integer rate);
	
}
