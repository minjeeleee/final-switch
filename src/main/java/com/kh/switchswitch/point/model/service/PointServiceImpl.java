package com.kh.switchswitch.point.model.service;

import java.net.URI;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.switchswitch.card.model.dto.CardRequestList;
import com.kh.switchswitch.common.code.Config;
import com.kh.switchswitch.point.model.dto.Iamport;
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
	
	
	
}
