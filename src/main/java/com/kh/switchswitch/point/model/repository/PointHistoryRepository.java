package com.kh.switchswitch.point.model.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.kh.switchswitch.point.model.dto.PointHistory;

@Mapper
public interface PointHistoryRepository {
	
	@Insert("insert into point_history values(sc_ph_idx.nextval,#{userIdx},#{type},#{points},#{resultPoint},sysdate,null)")
	void insertPointHistory(PointHistory pointHistory);
}
