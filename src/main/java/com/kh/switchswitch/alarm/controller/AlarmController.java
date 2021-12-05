package com.kh.switchswitch.alarm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AlarmController {
	
	@GetMapping("mypage/alarm")
	public void alarm() {}
	
}
