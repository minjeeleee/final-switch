package com.kh.switchswitch.point.controller;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kh.switchswitch.point.model.dto.InquiryRealName;
import com.kh.switchswitch.point.model.service.PointService;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

@Controller
@RequestMapping("point")
public class PointController {
	
	private PointService pointService;
	
	private IamportClient api;
	
	public PointController(PointService pointService) {
		this.pointService = pointService;
		// REST API 키와 REST API secret 를 아래처럼 순서대로 입력한다.
		this.api = new IamportClient("7002192506815642","52530b66901a86e01810d0a822adfc6f641449e0e1614b7c7e87d43dd8aaab73e87dfee87c55bab2");
	}

	@GetMapping("point-charge")
	public void pointCharge() {}
	
	@GetMapping("point-history")
	public void pointHistory() {}
	
	@GetMapping("point-history2")
	public void pointHistory2() {}
	
	@GetMapping("point-return")
	public void pointReturn() {}
	
	@GetMapping("point-return2")
	public void pointReturn2() {}
		
	@ResponseBody
	@RequestMapping(value="/verifyIamport/{imp_uid}")
	public IamportResponse<Payment> paymentByImpUid(
			Model model
			, Locale locale
			, HttpSession session
			, @PathVariable(value= "imp_uid") String imp_uid) throws IamportResponseException, IOException
	{	
			return api.paymentByImpUid(imp_uid);
	}
	
	@ResponseBody
	@GetMapping(value="checkAccount", produces = "text/html; charset=utf-8")
	public String checkAccount(@ModelAttribute InquiryRealName inquiryRealName){
		String userInfo = pointService.checkAccount(inquiryRealName);
 		if(userInfo != null) {
			return userInfo;
		}
		
		return "실패";
	}
	
	@GetMapping("refund")
	public String refundPoint(/* 환불신청 테이블? */){
		return "point/point-return";
	}

    
		

}
