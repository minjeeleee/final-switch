package com.kh.switchswitch.card.model.repository;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.kh.switchswitch.card.model.dto.FreeRequestList;

@Mapper
public interface FreeRequestListRepository {

	@Insert("insert into free_request_list values(sc_freq_idx.nextval,#{requestedCard},#{requestedMemIdx},#{requestMemIdx})")
	void insertFreeRequestList(FreeRequestList freeRequest);

	@Delete("delete from free_request_list where freq_idx=#{freqIdx}")
	void deleteFreeRequestList(Integer freqIdx);

	@Select("select * from free_request_list where freq_idx=#{freqIdx}")
	FreeRequestList selectFreeRequestListWithFreqIdx(Integer freqIdx);

	
}
