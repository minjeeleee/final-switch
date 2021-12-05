package com.kh.switchswitch.point.model.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.kh.switchswitch.point.model.dto.SavePoint;

@Mapper
public interface SavePointRepository {

	@Select("select * from save_point where member_idx=#{memberIdx}")
	SavePoint selectSavePointByMemberIdx(int memberIdx);

	void updateSavePoint(SavePoint savePoint);
	
	@Insert("insert into save_point(POINT_IDX,MEMBER_IDX) values(sc_point_idx.nextval,sc_member_idx.currval")
	void insertSavePoint();
	
}
