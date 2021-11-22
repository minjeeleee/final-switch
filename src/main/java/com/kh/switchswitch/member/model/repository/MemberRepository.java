package com.kh.switchswitch.member.model.repository;

import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.kh.switchswitch.member.model.dto.KakaoLogin;
import com.kh.switchswitch.member.model.dto.Member;

@Mapper
public interface MemberRepository {
	
	void insertMember(Member member);

	@Select("select * from member where member_nick = #{memberNick}")
	Member selectMemberByNickName(String memberNick);
	
	@Select("select * from member where member_email = #{memberEmail} and member_del_yn = 0")
	Member selectMemberByEmailAndDelN(String memberEmail);

	@Select("select * from member where member_nick = #{memberNick} and member_del_yn = 0")
	Member selectMemberByNicknameAndDelN(String memberNick);

	@Select("select * from kakao_login where kakao_id = #{id}")
	KakaoLogin selectKakaoLoginById(String id);

	void updateMember(Member member);

	@Insert("insert into kakao_login values(sc_kakao_idx.nextval,#{memberIdx},#{id})")
	void insertKakaoLogin(Map<Integer, String> map);
	
	@Insert("insert into kakao_login values(sc_kakao_idx.nextval,sc_member_idx.currval,#{id})")
	void insertKakaoLoginWithId(String id);
	
}
