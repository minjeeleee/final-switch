package com.kh.switchswitch.inquiry.model.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class Inquiry {
   private int inquiryIdx;
   private String userId;
   private Date regDate;
   private String title;
   private String content;
   private String type;
   private String answer;
   private int isDel;
}