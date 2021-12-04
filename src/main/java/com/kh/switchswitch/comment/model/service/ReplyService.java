package com.kh.switchswitch.comment.model.service;

import java.util.List;

import com.kh.switchswitch.comment.model.dto.Reply;

public interface ReplyService {

	Object selectCommentList(Reply commet);

	void saveComment(Reply comment);

	void modifyComment(Reply comment);

	void enabledComment(Reply comment);

	List<Reply> selectCommentList(Integer bdIdx);


}
