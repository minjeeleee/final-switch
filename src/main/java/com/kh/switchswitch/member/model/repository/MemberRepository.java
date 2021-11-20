package com.kh.switchswitch.member.model.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.kh.switchswitch.member.model.dto.Member;

@Mapper
public interface MemberRepository {
	
	@Insert("insert into member(MEMBER_IDX,CODE,MEMBER_REG_DATE,MEMBER_PASS,MEMBER_EMAIL,MEMBER_DEL_YN,MEMBER_TELL"
			+ " ,MEMBER_NICK,MEMBER_ADDRESS,MEMBER_DEL_DATE,MEMBER_SCORE,MEMBER_NAME) "
			+ " values(SC_MEMBER_IDX.NEXTVAL,'B',sysdate,#{memberPass},#{memberEmail},0,#{memberTell}"
			+ " ,#{memberNick},#{memberAddress},null,null,#{memberName})")
	void insertMember(Member member);

	@Select("select * from member where member_nick = #{memberNick}")
	Member selectMemberByNickName(String memberNick);
	
	@Select("select * from member where member_email = #{memberEmail} and member_del_yn = 0")
	Member selectMemberByEmailAndDelN(String memberEmail);

	@Select("select * from member where member_nick = #{memberNick} and member_del_yn = 0")
	Member selectMemberByNicknameAndDelN(String memberNick);
	
}
