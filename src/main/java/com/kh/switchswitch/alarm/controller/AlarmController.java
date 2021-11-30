package com.kh.switchswitch.alarm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AlarmController {
	
	@RequestMapping("alarm")
	public String alarm() {
		return "test";
	}

}
