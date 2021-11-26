package com.kh.switchswitch.exchange.model.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.kh.switchswitch.exchange.model.dto.ExchangeHistory;
import com.kh.switchswitch.exchange.model.dto.ExchangeStatus;

@Mapper
public interface ExchangeRepository {

	@Insert("insert into exchange_status (e_idx, user_idx1, user_idx2) values(sc_es_idx.nextval, #{userIdx1}, #{userIdx2})" )
	void insertExchangeStatus(ExchangeStatus exchangeStatus);

	@Select("select * from exchange_status where (user_idx1=#{memberIdx} or user_idx2 =#{memberIdx}) and type='ONGOING'")
	List<ExchangeStatus> selectEhByMemberIdxAndTypeOngoing(Integer memberIdx);

	
}
