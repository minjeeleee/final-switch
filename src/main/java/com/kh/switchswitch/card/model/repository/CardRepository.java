package com.kh.switchswitch.card.model.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.kh.switchswitch.card.model.dto.Card;
import com.kh.switchswitch.card.model.dto.CardRequestList;
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

	@Select("select * from card where member_idx=#{certifiedMemberIdx} and is_del=0 and exchange_status in('NONE','REQUEST','REQUESTED')")
	List<Card> selectCardListIsDelAndStatus(int certifiedMemberIdx);

	@Select("select * from file_info where card_idx=#{cardIdx}")
	List<FileDTO> selectFileInfoByCardIdx(int cardIdx);

	@Select("select * from card where card_idx=#{wishCardIdx}")
	Card selectCardByCardIdx(int wishCardIdx);
	
	@Select("select * from card order by card_idx desc")
	List<Card> selectAllCard();

	@Select("select member_idx from card where card_idx=#{wishCardIdx}")
	int selectMemberIdxByCardIdx(int wishCardIdx);

	@Select("select member_idx from card where card_idx=#{wishCardIdx}")
	int selectCardMemberIdxWithCardIdx(int wishCardIdx);
	
	@Select("select * from card where category=#{category}")
	List<Card> searchCategoryCard(String category);

	void updateCard(Card card);
	
	@Select("select * from card_request_list where REQUESTED_MEM_IDX=#{memberIdx} or REQUEST_MEM_IDX=#{memberIdx}")
	List<CardRequestList> selectCardRequestListByMemIdx(Integer memberIdx);

	@Delete("delete from card_request_list where REQUESTED_MEM_IDX=#{memberIdx} or REQUEST_MEM_IDX=#{memberIdx}")
	void deleteAllCardRequestByMemIdx(Integer memberIdx);

	@Update("update card set IS_DEL=1 where member_idx =#{memberIdx}")
	void updateAllCardByMemIdx(Integer memberIdx);

	@Delete("delete from card_request_list where req_idx=#{reqIdx}")
	void deleteCardRequestListWithReqIdx(Integer reqIdx);

	@Select("select * from card where req_idx=#{reqIdx}")
	Card selectCardByReqIdx(Integer reqIdx);

	@Select("select * from card where member_idx=#{memberIdx}")
	List<Card> selectCardByMemberIdx(Integer memberIdx);

	@Select("select * from card where member_idx=#{memberIdx} and exchange_status='REQUEST'")
	List<Card> selectCardByMemberIdxWithRequest(Integer memberIdx);

	@Select("select * from card where member_idx=#{memberIdx} and exchange_status='ONGOING'")
	List<Card> selectCardByMemberIdxWithOngoing(Integer memberIdx);
	
	@Select("select * from card where member_idx=#{memberIdx} and exchange_status='DONE'")
	List<Card> selectCardByMemberIdxWithDONE(Integer memberIdx);
	
}
