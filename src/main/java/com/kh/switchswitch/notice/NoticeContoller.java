package com.kh.switchswitch.notice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("notice")
public class NoticeContoller {
	
	@GetMapping("noticeList")
	public void noticeList() {}
}
