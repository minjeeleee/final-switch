package com.kh.switchswitch.notice.model.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.switchswitch.common.util.pagination.Paging;
import com.kh.switchswitch.common.util.pagination.PagingV2;
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
		int cntPerPage = 10;
		int total = (noticeRepository.selectContentCnt());
		int nowPage = page;
		String url = "/notice/notice-list2";
		
		PagingV2 pagingV2 = new PagingV2(total, nowPage, cntPerPage, url);
				
		Map<String,Object> commandMap = new HashMap<String,Object>();
		commandMap.put("paging", pagingV2);
		commandMap.put("noticeList", noticeRepository.selectNoticeListWithPageNo(Map.of("startBoard",pagingV2.getStartAlarm(),"lastBoard",pagingV2.getEndAlarm())));
		return commandMap;
		
	}

	public Map<String, Object> selectNoticeByIdx(Integer noticeIdx) {
		Notice notice = noticeRepository.selectNoticeByIdx(noticeIdx);
		return Map.of("notice",notice);
	}

	@Transactional
	public void modifyNotice(Notice notice) {
		noticeRepository.modifyNotice(notice);
		
	}

	public void deleteNotice(Integer noticeIdx) {
		noticeRepository.deleteNotice(noticeIdx);
		
	}













	
	

	
	
	
	
	
	
	
	
	
}
