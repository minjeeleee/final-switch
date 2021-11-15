document.querySelector('#bell').addEventListener("click", (e)=> {
	//1page
	document.querySelector(".noticeBoxPopUp").style.setProperty("visibility","visible");
});

document.querySelector('#close').addEventListener("click", (e)=> {
	//1page
	document.querySelector(".noticeBoxPopUp").style.setProperty("visibility","hidden");
});

document.querySelector("#ex_request").addEventListener("click", (e)=> {
	//fetch? ajax? ??
	msg = "Spring님으로부터 교환 요청이 왔습니다.";
	btn = "교환 요청 리스트로 가기";
	noticeSet(msg, btn);
});

document.querySelector("#ex_accepted").addEventListener("click", (e)=> {
	//fetch? ajax? ??
	msg = "Java님이 교환 요청을 수락했습니다.";
	btn = "교환창으로 이동";
	noticeSet(msg, btn);
});

document.querySelector("#notice_close").addEventListener("click", (e)=> {
	noticeInitialize();
});

function noticeInitialize(){
	document.querySelector("#notice_msg").innerHTML = "";
	document.querySelector("#notice_btn").innerHTML = "";
	document.querySelector(".noticePopUp").style.setProperty("visibility","hidden");
}

function noticeSet(msg, btn){
	document.querySelector("#notice_msg").innerHTML = msg;
	document.querySelector("#notice_btn").innerHTML = btn;
	document.querySelector(".noticePopUp").style.setProperty("visibility","visible");
}