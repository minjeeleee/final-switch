package com.kh.switchswitch.notice.model.service;

import java.util.Collection;
import java.util.Map;

import com.kh.switchswitch.notice.model.dto.Notice;

public interface NoticeService {

	void insertNotice(Notice notice);

	Map<String,Object>selectNoticeList(int page);

	Map<String, Object> selectNoticeByIdx(int noticeIdx);


}
