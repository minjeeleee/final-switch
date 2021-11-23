package com.kh.switchswitch.admin.model.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.kh.switchswitch.admin.model.dto.Code;
import com.kh.switchswitch.admin.model.dto.Menu;
import com.kh.switchswitch.card.model.dto.Card;
import com.kh.switchswitch.common.util.FileDTO;

@Mapper
public interface AdminRepository {
	
	@Select("select * from card")
	List<Card> selectRealTimeCards();
	
	@Select("select * from file_info where card_idx is not null")
	List<FileDTO> selectCardImgList();
	
	int deleteCard(int cardIdx);
	
	@Insert("insert into menu(url_idx,code,url,url_name,position,division,parent) values(sc_url_idx.nextval, #{code},#{url},#{urlName},#{position},#{division},#{parent})")
	void insertMenu(Menu Menu);
	
	@Select("select * from menu where division = '메인메뉴' and parent != '없음'")
	List<Menu> selectMenuList();
	
	@Select("select distinct url_name from menu where division = '서브' and position = '메뉴바'")
	List<Menu> selectParentsMenuListByMenu();
	
	@Select("select distinct url_name from menu where division = '서브' and position = '사이드바'")
	List<Menu> selectParentsMenuListBySideMenu();
	
	@Select("select * from code")
	List<Code> selectCodeList();
	
	@Select("select * from menu where division = '서브메뉴' and parent != '없음'" )
	List<Menu> selectSubMenu();
	
	@Delete("delete from menu where url_idx = #{urlIdx}")
	void deleteMenu(int urlIdx);

	

	

	

	


}
