package com.kh.switchswitch.exchange.model.repository;

import org.apache.ibatis.annotations.Delete;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.kh.switchswitch.exchange.model.dto.ExchangeHistory;
import com.kh.switchswitch.exchange.model.dto.ExchangeStatus;

@Mapper
public interface ExchangeRepository {

	@Insert("insert into exchange_status values(sc_e_idx.nextval, #{requestMemIdx}, #{requestedMemIdx}, 'ONGOING', #{propBalance}, #{reqIdx})" )
	void insertExchangeStatus(ExchangeStatus exchangeStatus);

	@Select("select type from exchange_satus where req_idx=#{reqIdx}")
	String selectExchangeStatusType(Integer reqIdx);

	@Select("select * from exchange_status where (request_mem_idx=#{memberIdx} or requested_mem_idx =#{memberIdx}) and type='ONGOING'")
	List<ExchangeStatus> selectEsByMemberIdxAndTypeOngoing(Integer memberIdx);

	@Delete("delete from exchange_status where req_idx=#{reqIdx}")
	void deleteExchangeStatusWithReqIdx(Integer reqIdx);

	@Update("update exchange_status set type=#{type} where req_idx=#{reqIdx} ")
	void updateExchangeStatus(ExchangeStatus exchangeStatus);

	@Select("select * from exchange_satus where req_idx=#{reqIdx}")
	ExchangeStatus selectExchangeStatusWithReqIdx(Integer reqIdx);
	
	@Insert("insert into exchange_history values(sc_eh_idx.nextval, #{eIdx}, sysdate, #{requestedMemIdx}, #{requestMemIdx})")
	void insertExchangeHistory(ExchangeHistory exchangeHistory);

	@Select("select req_idx from exchange_status where requested_mem_idx=#{memberIdx}")
	List<Integer> selectCardIdxWithMemberIdx(Integer memberIdx);
	
}
