package com.kh.switchswitch.board.model.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class Comment {

	private int cmIdx; //댓글번호
	private int bdIdx; //게시물 번호
	private int isDel; //삭제여부
	private String userId; //작성자 아이디
	private Date regDate; //작성일자
	private String content; //내용
	private int type; //계층
	private int cmLeave; //순서
	private int cmGroup; //그룹
}