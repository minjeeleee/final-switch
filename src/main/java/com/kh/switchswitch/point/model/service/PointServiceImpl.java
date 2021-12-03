package com.kh.switchswitch.point.model.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.switchswitch.card.model.dto.CardRequestList;
import com.kh.switchswitch.point.model.dto.SavePoint;
import com.kh.switchswitch.point.model.repository.SavePointRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService{
	
	private final SavePointRepository savePointRepository;
	private final RestTemplate http;

	public void updateSavePointWithAvailableBal(int availableBal, int memberIdx) {
		SavePoint savePoint = new SavePoint();
		savePoint.setAvailableBal(availableBal);
		savePoint.setMemberIdx(memberIdx);
		savePointRepository.updateSavePoint(savePoint);
	}

	public void updateSavePoint(CardRequestList cardRequestList) {
		SavePoint savePoint = savePointRepository.selectSavePointByMemberIdx(cardRequestList.getRequestMemIdx());
		savePoint.setAvailableBal(savePoint.getAvailableBal()+cardRequestList.getPropBalance());
		savePointRepository.updateSavePoint(savePoint);
	}

	public String checkAccount(String account) {
		
		String accessToken = "";
		String accountNum = "";
		String accountHolderName = "";
		String savingsBankName = "";
		
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("client_id", "4bd8e1e3-6c00-4aa1-bb89-46613a436a04");
		body.add("client_secret", "98888e4c-9600-4675-9f1a-f5e3ccbf7bb1");
		body.add("scope", "oob");
		body.add("grant_type", "client_credentials");
		
		//RestTemplate의 기본 ContentType이 application/json이다.
		RequestEntity<MultiValueMap<String, String>> request =
				RequestEntity.post("https://testapi.openbanking.or.kr/oauth/2.0/token")
				.accept(MediaType.APPLICATION_FORM_URLENCODED)
				.body(body);
		
		ResponseEntity<String> responseEntity = http.exchange(request, String.class);
	
		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			try {
				ObjectMapper objMapper = new ObjectMapper();
				Map<String, String> objMap = objMapper.readValue(responseEntity.getBody(), Map.class);
				
				accessToken = objMap.get("access_token");
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				return null;
			}
        }
		
		//은행연결하려면? 처음에 사용자등록 필요
		ObjectMapper obj = new ObjectMapper();
		
		Map<String, String> bodyForRealName = new HashMap<>();
		bodyForRealName.put("bank_tran_id", "M202113699U123456797");
		bodyForRealName.put("bank_code_std", "020");
		bodyForRealName.put("account_num","1002850748177");
		bodyForRealName.put("account_holder_info_type", " ");
		bodyForRealName.put("account_holder_info", "951220");
		bodyForRealName.put("tran_dtime", "20190910101921");
		
		try {
			String jsonRealName = obj.writerWithDefaultPrettyPrinter().writeValueAsString(bodyForRealName);
			
			System.out.println(accessToken);
			
			RequestEntity<String> requestRealName =
					RequestEntity.post("https://testapi.openbanking.or.kr/v2.0/inquiry/real_name")
					.accept(MediaType.APPLICATION_JSON)
					.header("Authorization", "Bearer " + accessToken)
					.body(jsonRealName);
			
			ResponseEntity<String> responseEntityRealName = http.exchange(requestRealName, String.class);
			
			if (responseEntityRealName.getStatusCode() == HttpStatus.OK) {
				try {
					ObjectMapper objMapper = new ObjectMapper();
					Map<String, String> objMap = objMapper.readValue(responseEntityRealName.getBody(), Map.class);
					
					for (String string : objMap.keySet()) {
						System.out.println(string + ":" + objMap.get(string));
					} 
					
					accountNum = objMap.get("account_num");
					accountHolderName = objMap.get("account_holder_name");
					savingsBankName = objMap.get("savings_bank_name");
					
					return accountNum + "," + accountHolderName + "," + savingsBankName;
					
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
	        }
			
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}
		
		
		
		
		
		
		return null;
	}
	
	
	
}
