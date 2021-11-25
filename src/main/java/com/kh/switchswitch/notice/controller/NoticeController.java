package com.kh.switchswitch.notice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("notice")
public class NoticeController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	

}
