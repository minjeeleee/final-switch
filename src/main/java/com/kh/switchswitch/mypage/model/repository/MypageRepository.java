package com.kh.switchswitch.mypage.model.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MypageRepository {

	@Update("update member set MEMBER_DEL_YN = 1 where MEMBER_EMAIL = #{email}")
	void leaveId(String email);
	
}
