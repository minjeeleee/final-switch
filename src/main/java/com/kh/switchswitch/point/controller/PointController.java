package com.kh.switchswitch.point.controller;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
		this.api = new IamportClient("1908372205894058","fbb4edd1ddae96984025282fa5d9f9ca99264a2ead59b5415802131258d75b0231b8a11635d1549c");
	}

	@GetMapping("point-charge")
	public void pointCharge() {}
	
	@GetMapping("point-history")
	public void pointHistory() {}
	
	@GetMapping("point-return")
	public void pointReturn() {}
		
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
		

}
