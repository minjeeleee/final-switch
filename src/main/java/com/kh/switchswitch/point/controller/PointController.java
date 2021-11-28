package com.kh.switchswitch.point.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("point")
public class PointController {

	@GetMapping("point-charge")
	public void pointCharge() {}
	
	@GetMapping("point-history")
	public void pointHistory() {}
	
	@GetMapping("point-return")
	public void pointReturn() {}

}
