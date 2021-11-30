package com.kh.switchswitch.card.model.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.kh.switchswitch.card.model.dto.FreeRequestList;

@Mapper
public interface FreeRequestListRepository {

	@Insert("insert into free_request_list values(sc_req_idx.nextval,#{requestedCard},#{requestedMemIdx},#{requestMemIdx})")
	void insertFreeRequestList(FreeRequestList freeRequest);

	
}
