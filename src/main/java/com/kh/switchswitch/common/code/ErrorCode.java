package com.kh.switchswitch.common.code;

public enum ErrorCode {
	
	DATABASE_ACCESS_ERROR("데이터베이스와 통신 중 에러가 발생하였습니다"),
	FAILED_VALIDATED_ERROR("데이터의 양식이 적합하지 않습니다"),
	MAIL_SEND_FAIL_ERROR("이메일 발송 중 에러가 발생하였습니다"),
	HTTP_CONNECT_ERROR("HTTP 통신 중 에러가 발생하였습니다"),
	AUTHENTICATION_FAILED_ERROR("유효하지 않은 인증입니다"),
	UNAUTHORIZED_PAGE("접근 권한이 없는 페이지 입니다"),
	REDIRECT_LOGIN_PAGE_NO_MESSAGE("","/member/login"),
	FAILED_FILE_UPLOAD_ERROR("파일업로드에 실해했습니다"),
	FAILED_TO_JOIN_WITH_KAKAO("이메일 제공은 필수 입니다.", "/member/login"),
	FAILED_TO_JOIN_LIMIT_DATE("탈퇴 후 3개월 이후에 재가입 가능합니다.","/"),
	FAILED_TO_KAKAO_LOGIN("카카오 로그인 중 오류가 발생하였습니다.","/member/login");
	
	public final String MESSAGE;
	public final String URL;
	
	ErrorCode(String msg){
		this.MESSAGE = msg;
		this.URL = "/";
	}
	
	ErrorCode(String msg, String url){
		this.MESSAGE = msg;
		this.URL = url;
	}
	
}
