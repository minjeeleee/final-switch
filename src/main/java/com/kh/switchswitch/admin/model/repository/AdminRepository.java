package com.kh.switchswitch.admin.model.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.kh.switchswitch.admin.model.dto.Code;
import com.kh.switchswitch.admin.model.dto.Menu;
import com.kh.switchswitch.card.model.dto.Card;
import com.kh.switchswitch.common.util.FileDTO;
import com.kh.switchswitch.member.model.dto.Member;

@Mapper
public interface AdminRepository {
	
	@Select("select * from card")
	List<Card> selectRealTimeCards();
	
	@Select("select * from file_info where card_idx is not null")
	List<FileDTO> selectCardImgList();
	
	int deleteCard(int cardIdx);
	
	@Select("select * from member")
	List<Member> selectMemberAllList();
	
	@Insert("insert into menu(url_idx,code,url,url_name,position,depth,parent) values(sc_url_idx.nextval, #{code},#{url},#{urlName},#{position},#{depth},#{parent})")
	void insertMenu(Menu Menu);
	
	@Select("select * from menu where depth = '1' and parent = '없음' and position = '메뉴바' order by seq asc")
	List<Menu> selectMenuList();
	
	@Select("select * from menu where depth = '1' and parent = '없음' and position = '사이드바' order by seq asc" )
	List<Menu> selectSideMenu();
	
	@Select("select * from menu where parent = #{parent} and depth = '2' order by seq asc")
	List<Menu> selectChildMenu(String parent);
	
	@Select("select distinct url_name from menu where depth = '1' and parent = '없음' and position = '메뉴바'")
	List<Menu> selectParentsMenuListByMenu();
	
	@Select("select distinct url_name from menu where depth = '1' and parent = '없음' and position = '사이드바'")
	List<Menu> selectParentsMenuListBySideMenu();
	
	@Select("select * from code")
	List<Code> selectCodeList();
	
	@Select("select * from menu")
	List<Menu> selectMenuAllList();
	
	@Delete("delete from menu where url_idx = #{urlIdx}")
	void deleteMenu(int urlIdx);

	@Update("update member set code=#{code} where member_idx=#{memberIdx}")
	void updateMemberCode(@Param("code")String code,@Param("memberIdx") int memberIdx);
	
	@Select("select * from member where code = 'D'")
	List<Member> selectMemberBlackList();
	
	@Select("select * from member where member_Idx = #{memberIdx}")
	Member searchDetailMemberProfile(int memberIdx);
	
	void updateMemberInfo(Member convertToMember, int memberIdx);

	

	

	

	

	

	

	


}
