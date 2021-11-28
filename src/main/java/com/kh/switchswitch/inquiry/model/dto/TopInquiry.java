package com.kh.switchswitch.inquiry.model.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class TopInquiry {
   private int supIdx;
   private String userId;
   private Date regDate;
   private String title;
   private String content;
   private int isDel;
}