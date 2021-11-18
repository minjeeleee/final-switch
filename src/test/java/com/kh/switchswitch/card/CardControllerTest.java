package com.kh.switchswitch.card;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.kh.switchswitch.member.model.dto.Member;


//가상으로 만들어지는 web.xml을 사용해 테스트환경을 구축
@WebAppConfiguration
//Junit을 실행할 방법
//테스트 때 사용할 가상의 applicationContext를 생성하고 관리
@RunWith(SpringJUnit4ClassRunner.class)
//가상의 applicationContext를 생성할 때 사용할 spring bean 설정파일의 위치를 지정
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*-context.xml"})
public class CardControllerTest {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	WebApplicationContext context;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		this.mockMvc = webAppContextSetup(context).build();
	}
	
	@Test
	public void cardInsert() throws Exception {
		MockMultipartFile file1 = new MockMultipartFile("imgList", "OFN1.txt", null, "OFN01".getBytes());
		MockMultipartFile file2 = new MockMultipartFile("imgList", "OFN2.txt", null, "OFN02".getBytes());
		
		
		Member member = new Member();
		member.setMemberIdx(1);

		mockMvc.perform(multipart("/card/card-form")
				.file(file1)
				.file(file2)
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.param("category", "category")
				.param("name", "name")
				.param("condition", "3")
				.param("deliveryCharge", "N")
				.param("content", "본문")
				.param("region", "게시글테스트메서드")
				.param("regionDetail", "본문")
				.param("method", "본문")
				.param("isfree", "N")
				.param("memberIdx", "1")
				.sessionAttr("authentication", member))
			.andExpect(status().is3xxRedirection())
			.andDo(print());
	}

	
}
