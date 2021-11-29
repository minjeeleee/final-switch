package com.kh.switchswitch.admin;


import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.kh.switchswitch.admin.model.repository.AdminRepository;
import com.kh.switchswitch.common.util.FileDTO;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*-context.xml"})
public class AdminControllerTest {
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Test
	public void selectOneTest() {
		List<Integer> cardIdx = adminRepository.selectCardIdx();
		List<FileDTO> cardImgList = new ArrayList<FileDTO>();
		for (Integer e : cardIdx) {
			cardImgList = adminRepository.selectCardImgListByCardIdx(e);
		}
		System.out.println(cardImgList);
	}
	
}
