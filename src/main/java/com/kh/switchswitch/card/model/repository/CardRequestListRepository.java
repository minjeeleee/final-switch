package com.kh.switchswitch.card.model.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.kh.switchswitch.card.model.dto.CardRequestList;

@Mapper
public interface CardRequestListRepository {
	
	void insertCardRequestList(CardRequestList cardRequestList);

	@Select("select * from card_request_list where req_idx=#{reqIdx}")
	CardRequestList selectCardRequestListWithReqIdx(Integer reqIdx);

	@Select("select requested_card from card_request_list where requested_mem_idx=#{memberIdx}")
	List<Integer> selectCardIdxWithMemberIdx(Integer memberIdx);

	@Select("select sc_req_idx.currval from dual")
	Integer selectNewReqIdx();

}
