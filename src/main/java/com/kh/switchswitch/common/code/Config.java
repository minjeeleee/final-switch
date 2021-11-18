package com.kh.switchswitch.common.code;

public enum Config {
	
	//DOMAIN("http://www.pclass.com"),
	DOMAIN("http://localhost:9090"),
	SMTP_AUTHENTICATION_ID("projectteamyong.gmail.com"),
	SMTP_AUTHENTICATION_PASSWORD("switch1234!"),
	COMPANY_EMAIL("projectteamyong.gmail.com"),
	//UPLOAD_PATH("C:\\CODE\\upload") 운영서버
	UPLOAD_PATH("C:\\CODE\\upload\\");//개발서버
	
	public final String DESC;
	
	private Config(String desc) {
		this.DESC = desc;
	}
}
