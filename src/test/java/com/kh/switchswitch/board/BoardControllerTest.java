package com.kh.switchswitch.board;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.kh.switchswitch.member.model.dto.Member;



@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*-context.xml"})
public class BoardControllerTest {

	@Autowired
	WebApplicationContext context;
	
	private MockMvc mockMvc; //Moc객체는 가상환경에서 test할때 테스트를 수행하여 결과를 전달해주는 객체
	
	//@Before : 테스트 수행전 실행 될 메서드에 선언
	//@Test : 테스트를 수행할 메서드
	//@After : 테스트 수행 후 실행 될 메서드에 선언
	
	@Before
	public void setup() { //Mok객체를 생성해주는 메서드
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test 
	public void uploadBoard() throws Exception {
												//11/18 테스트통과 파일추가완료
												//11/17 테스트통과 (파일추가 없이)	
												// "files" is null 오류
												
												
		MockMultipartFile file1 = new MockMultipartFile("files", "OFN.txt", null, "OFN01".getBytes());

		Member member = new Member();
		member.setMemberNick("닉네임");
		
		mockMvc.perform(multipart("/board/upload")
				.file(file1)
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.param("title", "테스트 제목")
				.param("content", "테스트 본문")
				.sessionAttr("authentication", member))
		.andExpect(status().is3xxRedirection())
		.andDo(print());
	}
	
	
	//11/18 테스트오류 html문제같음
	//THYMELEAF][main] Exception processing template "board/board-detail"
	//: Exception evaluating SpringEL expression: "title" (template: "board/board-detail" - line 41, col 15)
	

	//11/21 수정필요
	  //ERROR: org.thymeleaf.TemplateEngine - [THYMELEAF][main] 
	  //Exception processing template "board/board-detail": Exception evaluating SpringEL expression: "title" (template: "board/board-detail" - line 43, col 15)

	@Test
	public void boardDetail() throws Exception{
		Member member = new Member();
		member.setMemberNick("닉네임");
		
		mockMvc.perform(get("/board/board-detail")
				.param("bdIdx", "370")
				.sessionAttr("authentication", member))
		.andExpect(status().isOk())
		.andDo(print());
	}
	
	
	
	@After
	public void afterTest() {
		System.out.println("테스트 종료");
	}


	
}
