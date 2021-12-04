package com.kh.switchswitch.admin.model.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.lang.Nullable;

import com.kh.switchswitch.admin.model.dto.Code;
import com.kh.switchswitch.admin.model.dto.Menu;
import com.kh.switchswitch.card.model.dto.Card;
import com.kh.switchswitch.common.util.FileDTO;
import com.kh.switchswitch.member.model.dto.Member;

@Mapper
public interface AdminRepository {
	
	@Select("select * from card where is_del = 0")
	List<Card> selectCardList();
	
	@Select("select * from card where is_del = 0 and isfree = 'N'")
	List<Card> selectCardListByTrade();
	
	@Select("select * from card where is_del = 0 and isfree = 'Y'")
	List<Card> selectCardListByFree();
	
	//@Select("select * from card where is_del = 0")
	List<Card> selectCardsDetail(@Param("searchPeriod")String searchPeriod, @Param("searchType")String searchType, @Nullable@Param("searchKeyword")String searchKeyword);
	
	@Select("select * from file_info where card_idx is not null and card_idx = #{cardIdx}")
	List<FileDTO> selectCardImgListByCardIdx(Integer cardIdx);
	
	@Select("select card_idx from card where is_del = 0")
	List<Integer> selectCardIdx();
	
	@Update("update card set is_del = 1 where card_idx = #{cardIdx}")
	Integer deleteCard(Integer cardIdx);
	
	@Update("update file_info set is_del = 1 where card_idx = #{cardIdx}")
	void deleteImg(Integer cardIdx);
	
	//@Select("select * from member where member_del_yn=0 and #{searchType}=#{keyword}")
	List<Member> selectMemberAllList(@Param("searchType") String searchType, @Param("keyword") String keyword);
	
	//@Select("select * from member where code = 'D' and member_del_yn=0")
	List<Member> selectMemberBlackList(@Param("searchType") String searchType, @Param("keyword") String keyword);
	
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
	
	@Select("select * from member where member_idx = #{memberIdx} and member_del_yn=0")
	Member selectMemberByIdx(int memberIdx);
	
	@Select("select * from file_info where fl_idx = #{flIdx} and is_del=0")
	FileDTO selectFileByMemberIdx(int flIdx);
	
	void updateMemberInfo(Member convertToMember);
	
	@Select("select * from member where member_nick = #{memberNick}")
	Member selectMemberByNickName(@Param("memberNick") String memberNick);
	
	@Update("update member set member_del_yn=1,member_del_date=sysdate where member_Idx=#{memberIdx}")
	void deleteMember(Integer memberIdx);
	
	@Update("update file_info set is_del=1 where fl_Idx = #{flIdx}")
	void deleteMemberProfileImg(Integer flIdx);
	
	@Select("select count(*) from card where member_idx = #{memberIdx} and is_del = 0")
	Integer selectCardCountByMemberIdx(Integer memberIdx);
	
	@Select("select * from file_info where card_idx is not null and card_idx = #{cardIdx}")
	List<FileDTO> selectFileInfoByCardIdx(Integer cardIdx);
	
	@Select("select * from card where member_idx = #{memberIdx} and rownum < 6 and is_del = 0")
	List<Card> selectCardListByMemberIdx(int memberIdx);
	
	@Select("select * from file_info where card_idx is not null and is_del = 0 ")
	List<FileDTO> selectCardImgList();
	
	@Select("select card_idx from file_info where fl_idx = #{flIdx}")
	Integer selectCardIdxByflIdx(Integer flIdx);
	
	
	


	

	

	
	

	

	

	

	

	

	


}
