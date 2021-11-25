package com.kh.switchswitch.card.model.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.kh.switchswitch.card.model.dto.CardRequestList;

@Mapper
public interface CardRequestListRepository {

	@Insert("insert into card_request_list values(sc_req_idx.nextval"
			+ " , #{requestedCard}, #{requestCard1}, #{requestCard2}, #{requestCard3}, #{requestCard4}"
			+ " , #{requestedMemIdx}, #{requestMemIdx}, #{propBalance})")
	void insertCardRequestList(CardRequestList cardRequestList);

}
