package com.kh.switchswitch.comment.model.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class Comment {

	private int bdIdx; //게시물 번호
	private int cmIdx; //댓글번호
	private String userId; //작성자 아이디
	private int isDel; //삭제여부
	private String content; //댓글내용
	private Date regDate; //작성일자
	private int cmParent;
	private int cmDepth;
	private Integer cmOrder;

}