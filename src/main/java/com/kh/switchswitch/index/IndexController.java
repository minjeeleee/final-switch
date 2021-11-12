package com.kh.switchswitch.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	
	
	@GetMapping("/")
	public String index() {
		//Controller 메서드의 return 타입
		//void : 해당 메서드가 호출된 url 경로와 같은 위치의 jsp파일로 요청이 재지정
		//		 요청 url : /index/index -> jsp file : WEB-INF/views/index/index.jsp
		//String : jsp 파일의 위치를 지정 -> rerunt "index/index" jsp file : WEB-INF/views/index/index.jsp
		//ModelAndView : Model 객체 + view(jsp 위치)
		
		return "index";
	}
	
	
	
	
	
	
	
}
