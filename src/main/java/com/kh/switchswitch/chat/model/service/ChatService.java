package com.kh.switchswitch.chat.model.service;

import java.util.List;
import java.util.Map;

public interface ChatService {

	List<Map<String, Object>> selectChatMessageByChattingIdx(Integer chattingIdx, Integer memberIdx);

}
