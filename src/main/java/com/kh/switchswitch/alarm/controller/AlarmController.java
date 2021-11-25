package com.kh.switchswitch.alarm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("alarm")
public class AlarmController {
	
	@GetMapping("alarmList")
	public void alarmList() {}

}
