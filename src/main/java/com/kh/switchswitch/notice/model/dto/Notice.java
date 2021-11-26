package com.kh.switchswitch.notice.model.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class Notice {
   private int noticeIdx;
   private String userId;
   private Date regDate;
   private String title;
   private String content;
   private String inportant;
   private int isDel;
}