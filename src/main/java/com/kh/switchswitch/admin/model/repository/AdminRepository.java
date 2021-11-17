package com.kh.switchswitch.admin.model.repository;

import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.kh.switchswitch.admin.model.dto.Menu;

@Mapper
public interface AdminRepository {
	
	@Select("")
	Map<String, Object> selectRealTimeCards();
	
	int deleteCard(int cardIdx);
	
	@Insert("insert into menu(url_idx,code,url,url_name,position,division,parent) values(sc_url_idx.nextval, #{code},#{url},#{urlName},#{position},#{division},#{parent})")
	void insertMenu(Menu Menu);


}
