package com.kh.switchswitch.chat.model.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.kh.switchswitch.chat.model.dto.ChatMessages;
import com.kh.switchswitch.chat.model.dto.Chatting;

@Mapper
public interface ChatRepository {
	@Select("select * from chat_messages where chatting_idx=#{chattingIdx}  order by cm_idx asc")
	List<ChatMessages> selectChatMessagesList(Integer receiverIdx);

	@Insert("insert into chat_messages values(sc_chat_idx.nextval,#{chattingIdx},#{senderId},#{message},sysdate,1)")
	void insertChatMessage(ChatMessages chatMessages);

	@Update("update chat_messages set is_read=0 where cm_idx=#{cmIdx}")
	void updateChatIsRead(Integer cmIdx);
	
	@Update("update chat_messages set is_read=0 where chatting_idx=#{chattingIdx}")
	void updateAllChatIsRead(Integer chattingIdx);

	@Insert("insert into chatting values(sc_chatting_idx.nextval,#{requestedMemIdx},#{requestedMemIdx2},sysdate)")
	Object insertChatting(Integer requestedMemIdx, Integer requestedMemIdx2);

	@Select("select * from chatting where ATTENDEE1 =#{memberIdx} or ATTENDEE2 = #{memberIdx}")
	List<Chatting> selectAllChattingByMemberIdx(Integer memberIdx);

	@Select("select message from chat_messages  where chatting_idx = #{chattingIdx} and ROWNUM <=1 ORDER BY cm_idx desc")
	String selectLastChatMessages(Integer chattingIdx);

	@Select("SELECT COUNT(*) from chat_messages where chatting_idx=#{chattingIdx} and is_read=1 and sender_id=#{senderId}")
	int selectCountOfIsReadByChattingIdx(@Param("chattingIdx") Integer chattingIdx,@Param("senderId")Integer senderId);
}
