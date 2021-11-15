package com.kh.switchswitch.mypage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("mypage")
public class MypageController {

	@GetMapping("chatting")
	public void chatting() {}
	
	@GetMapping("history")
	public void history() {}
	
	@GetMapping("leave-member")
	public void leaveMember() {}
	
	@GetMapping("mypage-inquiry")
	public void mypageInquiry() {}
	
	@GetMapping("point-charge")
	public void pointCharge() {}
	
	@GetMapping("point-history")
	public void pointHistory() {}
	
	@GetMapping("profile")
	public void profile() {}
}
