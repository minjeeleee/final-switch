package com.kh.switchswitch.card.model.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.kh.switchswitch.card.model.dto.Card;
import com.kh.switchswitch.common.util.FileDTO;

@Mapper
public interface CardRepository {

	@Insert("insert into card("
			+ " CARD_IDX, MEMBER_IDX,CATEGORY,NAME,CONDITION,DELIVERY_CHARGE"
			+ " ,ISFREE,CONTENT,REGION,REGION_DETAIL,METHOD,HOPE_KIND) "
			+ " values(SC_CARD_IDX.nextval, #{memberIdx},#{category},#{name},#{condition},#{deliveryCharge}"
			+ " , #{isfree},#{content},#{region},#{regionDetail},#{method},#{hopeKind})")
	void insertCard(Card card);

	@Insert("insert into file_info(FL_IDX,ORIGIN_FILE_NAME,RENAME_FILE_NAME,SAVE_PATH,CARD_IDX) "
			+ " values(sc_file_idx.nextval,#{originFileName},#{renameFileName},#{savePath}"
			+ " ,sc_card_idx.currval)")
	void insertFileInfo(FileDTO fileUpload);

	@Select("select * from card where member_idx=#{certifiedMemberIdx} and is_del=0 and exchange_status in('request','requested')")
	List<Card> selectCardListIsDelAndStatus(int certifiedMemberIdx);

	@Select("select * from file_info where card_idx=#{cardIdx}")
	List<FileDTO> selectFileInfoByCardIdx(int cardIdx);

	
}
