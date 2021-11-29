package com.kh.switchswitch.notice.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.switchswitch.board.model.dto.Board;
import com.kh.switchswitch.common.util.FileDTO;
import com.kh.switchswitch.common.util.pagination.Paging;
import com.kh.switchswitch.notice.model.dto.Notice;
import com.kh.switchswitch.notice.model.repository.NoticeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeServiceImpl implements NoticeService{
	
	private final NoticeRepository noticeRepository;
	Logger logger = LoggerFactory.getLogger(this.getClass());

	public void insertNotice(Notice notice) {
		noticeRepository.insertNotice(notice);
		
	}

	public Map<String, Object> selectNoticeList(int page) {
		int cntPerPage = 5;
		Paging pageUtil = Paging.builder()
				.url("/board/board-list")
				.total(10)
				.curPage(page)
				.blockCnt(10)
				.cntPerPage(cntPerPage)
				.build();

		Map<String,Object> commandMap = new HashMap<String,Object>();
		commandMap.put("paging", pageUtil);
		commandMap.put("noticeList", noticeRepository.selectNoticeList(pageUtil));
		return commandMap;
	}

	public Map<String, Object> selectNoticeByIdx(int noticeIdx) {
		Notice notice = noticeRepository.selectNoticeByIdx(noticeIdx);
		return Map.of("notice",notice);
	}















	
	

	
	
	
	
	
	
	
	
	
}
