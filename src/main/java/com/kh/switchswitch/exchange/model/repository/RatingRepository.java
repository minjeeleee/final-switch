package com.kh.switchswitch.exchange.model.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RatingRepository {

	//Optional<> 이용시 tooManyResult 오류 발생하여 걷어냄
	@Select("select rating from rating where user_idx=#{certifiedMemberIdx}")
	List<Float> selectRatingByMemberIdx(int certifiedMemberIdx);

	@Select("select rating from rating where user_idx = #{memberIdx}")
	List<Integer> selectMyRateCnt(int memberIdx);
	
}
