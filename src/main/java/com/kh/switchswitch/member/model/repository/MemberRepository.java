package com.kh.switchswitch.member.model.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.kh.switchswitch.member.model.dto.Member;

@Mapper
public interface MemberRepository {
	
	@Select("select * from member where member_email = #{memberEmail}")
	Member selectMemberByEmail(String memberEmail);

	@Insert("insert member() values()")
	void insert(Member member);

	@Select("select * from member where member_nick = #{memberNick}")
	Member selectMemberByNickName(String memberNick);
	
}
