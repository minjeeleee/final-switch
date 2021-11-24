package com.kh.switchswitch.member.model.repository;

import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.kh.switchswitch.common.util.FileDTO;
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

	void updateMember(Member member);
	
	void updateMemberForFile(Member member);

	@Select("select * from member where member_email = #{memberEmail} and member_del_yn = 1")
	Member selectMemberByEmailAndDelY(String memberEmail);
	
	@Insert("insert into file_info(FL_IDX,ORIGIN_FILE_NAME,RENAME_FILE_NAME,SAVE_PATH) "
			+ " values(sc_file_idx.nextval,#{originFileName},#{renameFileName},#{savePath})")
	void insertFileInfo(FileDTO fileUpload);

	@Select("select * from file_info where fl_idx = #{flIdx}")
	FileDTO selectFileInfoByFlIdx(int flIdx);

	@Select("select * from member where member_email = #{memberEmail}")
	Member selectMemberByEmail(String memberEmail);

	@Select("select member_email from member where member_nick = #{nickname} and member_tell = #{tell} and member_del_yn = 0")
	String selectEmailByNicknameAndTell(Map<String, String> map);
}
