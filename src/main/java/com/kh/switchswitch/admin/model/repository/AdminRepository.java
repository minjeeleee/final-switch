package com.kh.switchswitch.admin.model.repository;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminRepository {
	
	@Select("")
	Map<String, Object> selectRealTimeCards();
	
	int deleteCard(int cardIdx);


}
