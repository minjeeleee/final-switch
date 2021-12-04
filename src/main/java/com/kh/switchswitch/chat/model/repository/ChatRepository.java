package com.kh.switchswitch.chat.model.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.kh.switchswitch.chat.model.dto.ChatMessages;

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
}
