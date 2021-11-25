package com.kh.switchswitch.point.model.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.kh.switchswitch.point.model.dto.SavePoint;

@Mapper
public interface SavePointRepository {

	@Select("select balance from save_point where member_idx=#{memberIdx}")
	int selectBalanceByMemberIdx(int memberIdx);

	void updateSavePoint(SavePoint savePoint);
	
}
