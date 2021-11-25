package com.kh.switchswitch.exchange.model.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.kh.switchswitch.exchange.model.dto.ExchangeStatus;

@Mapper
public interface ExchangeRepository {

	@Insert("insert into exchange_status (e_idx, user_idx1, user_idx2) values(sc_es_idx.nextval, #{userIdx1}, #{userIdx2})" )
	void insertExchangeStatus(ExchangeStatus exchangeStatus);


	
}
