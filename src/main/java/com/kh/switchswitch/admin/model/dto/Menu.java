package com.kh.switchswitch.admin.model.dto;

import lombok.Data;

@Data
public class Menu {
	
	private int urlIdx;
	private String code;
	private String url;
	private String urlName;
	private String position;
	private String division;
	private String parent;

}
