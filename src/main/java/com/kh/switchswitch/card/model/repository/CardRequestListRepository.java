package com.kh.switchswitch.card.model.repository;

import org.apache.ibatis.annotations.Mapper;

import com.kh.switchswitch.card.model.dto.CardRequestList;

@Mapper
public interface CardRequestListRepository {
	
	void insertCardRequestList(CardRequestList cardRequestList);

}
