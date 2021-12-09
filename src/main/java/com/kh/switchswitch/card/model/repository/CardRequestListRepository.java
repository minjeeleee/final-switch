package com.kh.switchswitch.card.model.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.kh.switchswitch.card.model.dto.CardRequestList;

@Mapper
public interface CardRequestListRepository {
	
	void insertCardRequestList(CardRequestList cardRequestList);

	@Select("select * from card_request_list where req_idx=#{reqIdx}")
	CardRequestList selectCardRequestListWithReqIdx(Integer reqIdx);

	@Select("select req_idx from card_request_list where requested_mem_idx=#{memberIdx}")
	List<Integer> selectReqIdxWithMemberIdx(Integer memberIdx);

	@Select("select sc_req_idx.currval from dual")
	Integer selectNewReqIdx();

	void updateCardRequestList(CardRequestList cardRequestList);

<<<<<<< HEAD
	@Select("select distinct req_idx from card_request_list where requested_card=#{cardIdx}")
	Integer selectReqIdxByRequestedCardIdx(Integer cardIdx);
	
	@Select("select req_idx from card_request_list\r\n"
			+ "	where #{cardIdx} in (request_card1, request_card2, request_card3, request_card4)")
	Integer selectReqIdxByRequestCardIdx(Integer cardIdx);
	
	@Select("select req_idx from exchange_status where #{memberIdx} = request_mem_idx"
			+ " and type in('ONGOING','APPLICANTCANCEL','OWNERCANCEL')")
	List<Integer> selectReqIdxForRequestByOngoingCardIdx(Integer memberIdx);
	
	@Select("select req_idx from exchange_status where #{memberIdx} = requested_mem_idx"
			+ " and type in('ONGOING','APPLICANTCANCEL','OWNERCANCEL')")
	List<Integer> selectReqIdxForRequestedByOngoingCardIdx(Integer memberIdx);
=======
	@Select("select req_idx from card_request_list where requested_card=#{cardIdx}")
	Integer selectReqIdxByRequestedCardIdx(Integer cardIdx);

	@Select("select req_idx from card_request_list "
			+ "where request_card1=#{cardIdx} or request_card2=#{cardIdx} or request_card3=#{cardIdx} or request_card4=#{cardIdx}")
	Integer selectReqIdxByRequestCardIdx(Integer cardIdx);

	@Select("select req_idx from card_request_list where "
			+ "(requested_card=#{cardIdx} or request_card1=#{cardIdx} or request_card2=#{cardIdx} or request_card3=#{cardIdx} or request_card4=#{cardIdx})"
			+ "and req_idx in(select req_idx from exchange_status where type='ONGOING' and (request_mem_idx=#{memberIdx} or requested_mem_idx=#{memberIdx}))")
	Integer selectReqIdxByOngoingCardIdx(Integer memberIdx, Integer cardIdx);
>>>>>>> branch 'main' of https://github.com/minjeeleee/final-switch.git
}
