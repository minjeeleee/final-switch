package com.kh.switchswitch.point.model.service;

import org.springframework.stereotype.Service;

import com.kh.switchswitch.card.model.dto.CardRequestList;
import com.kh.switchswitch.point.model.dto.SavePoint;
import com.kh.switchswitch.point.model.repository.SavePointRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService{
	
	private final SavePointRepository savePointRepository;

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
