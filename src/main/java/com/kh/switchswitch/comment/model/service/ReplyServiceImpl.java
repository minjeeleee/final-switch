package com.kh.switchswitch.comment.model.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.switchswitch.board.model.dto.Reply;
import com.kh.switchswitch.comment.model.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReplyServiceImpl implements ReplyService{
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	private final ReplyRepository commentRepository;

	@Override
	public Object selectCommentList(Reply commet) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void saveComment(Reply comment) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void modifyComment(Reply comment) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void enabledComment(Reply comment) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public List<Reply> selectCommentList(Integer bdIdx) {
		commentRepository.selectCommentList(bdIdx);
		return null;
	}
	











	
	

	
	
	
	
	
	
	
	
	
}
