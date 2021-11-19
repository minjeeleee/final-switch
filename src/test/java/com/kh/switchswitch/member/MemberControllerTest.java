package com.kh.switchswitch.member;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.kh.switchswitch.member.model.dto.Member;
import com.kh.switchswitch.member.model.dto.MemberAccount;
import com.kh.switchswitch.member.validator.JoinForm;


//가상으로 만들어지는 web.xml을 사용해 테스트환경을 구축
@WebAppConfiguration
//Junit을 실행할 방법
//테스트 때 사용할 가상의 applicationContext를 생성하고 관리
@RunWith(SpringJUnit4ClassRunner.class)
//가상의 applicationContext를 생성할 때 사용할 spring bean 설정파일의 위치를 지정
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*-context.xml"})
public class MemberControllerTest {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	WebApplicationContext context;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		this.mockMvc = webAppContextSetup(context).build();
	}
	
	//이메일 중복검사 - 성공
	@Test
	public void emailCheckWithSuccess() throws Exception {
		mockMvc.perform(get("/member/email-check")
				.param("memberEmail","prac@abc.com"))
			.andExpect(status().isOk())
			.andExpect(content().string("available"))
			.andDo(print());
	}
	
	//이메일 중복검사 - 실패
	@Test
	public void emailCheckWithFailure() throws Exception {
		mockMvc.perform(get("/member/email-check")
				.param("memberEmail","test@email.com"))
			.andExpect(status().isOk())
			.andExpect(content().string("disable"))
			.andDo(print());
	}
	
	//이메일 발송 이후 회원가입 완료처리
	@Test
	public void joinImpl() throws Exception {
		JoinForm form = new JoinForm();
		form.setMemberName("test");
		form.setMemberEmail("test@email.com");
		form.setMemberPass("123qwe!@#");
		form.setMemberTell("01012341234");
		form.setMemberNick("testNick");
		form.setZipNo("0123");
		form.setAddress("서울시");
		
		mockMvc.perform(get("/member/join-impl/1234")
				.sessionAttr("persistToken","1234")
				.sessionAttr("persistUser", form))
			.andExpect(status().is3xxRedirection())
			.andDo(print());
	}
	
	//security 로그인 test... controller PostMapping 필요?? status ??
	@Test
	public void login() throws Exception {
		Member member = new Member();
		member.setMemberEmail("projectteamyong@gmail.com");
		member.setMemberPass("asdf1234*");
		member.setCode("B");
		MemberAccount memberAccount = new MemberAccount(member);
		
		mockMvc.perform(post("/member/login")
				.with(user(memberAccount)))
			.andExpect(status().is3xxRedirection())
			.andDo(print());
	}

	
}
