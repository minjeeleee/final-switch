package com.kh.switchswitch.point.model.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.kh.switchswitch.point.model.dto.SavePoint;

@Mapper
public interface SavePointRepository {

	@Select("select * from save_point where member_idx=#{memberIdx}")
	SavePoint selectSavePointByMemberIdx(int memberIdx);

	void updateSavePoint(SavePoint savePoint);
	
}
