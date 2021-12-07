<!-- 요청취소 -->
function requestCancel(){
	$("#sendResponse").attr("action", "http://localhost:9090/exchange/request-cancel/"+reqIdx);
	$("#sendResponse").submit();
}
<!-- 요청수정 -->
function requestRevise(){
	$("#sendResponse").attr("action", "http://localhost:9090/exchange/revise/"+reqIdx);
	$("#sendResponse").submit();
}
<!-- 요청수락 -->
function requestAccept(){
	$("#sendResponse").attr("action", "http://localhost:9090/exchange/accept/"+reqIdx);
	console.dir("요청수락 보내기 전");
	$("#sendResponse").submit();
}
<!-- 요청거절 -->
function requestReject(){
	$("#sendResponse").attr("action", "http://localhost:9090/exchange/reject/"+reqIdx);
	$("#sendResponse").submit();
}
<!-- 교환취소요청 -->

function exchangeCancelRequest(){
	$("#sendResponse")
	.attr("action", 
			"http://localhost:9090/exchange/cancel-request/"+reqIdx
			+"/"+status);
	console.dir("요청수락 보내기 후");
	$("#sendResponse").submit();
}
<!-- 교환취소요청수락 -->
function exchangeRequestCancel(){
	$("#sendResponse").attr("action", "http://localhost:9090/exchange/exchange-cancel/"+reqIdx);
	$("#sendResponse").submit();
}

<!-- 교환완료 -->
<!-- 평점요청창 생성 -->
function exchangeComplete(){
	<!-- 사용자평가 모달창 생성 -->
	let msg = counterpartNick+"님과의 교환은 어떠셨나요?<br>"
		+ counterpartNick + "에 대한 평점을 남겨주세요.";
	noticeSet(msg);
}

document.querySelector("#notice_close").addEventListener("click", (e)=> {
	noticeInitialize();
});

function noticeInitialize(){
	document.querySelector("#notice_msg").innerHTML = "";
	document.querySelector(".noticePopUp").style.setProperty("visibility","hidden");
}

function noticeSet(msg){
	document.querySelector("#notice_msg").innerHTML = msg;
	document.querySelector(".noticePopUp").style.setProperty("visibility","visible");
}

$("#submit_btn").on("click", function(){
	$(".notice").appendTo("#sendResponse");
	<!-- 제출 -->
	doSubmit();
})

function doSubmit(){
	$("#sendResponse").attr("action", "http://localhost:9090/exchange/complete/"+reqIdx);
	$("#sendResponse").submit();
}

<!-- 뒤로가기 -->
function moveBack(){
	let preUrl = document.referer;
	
	location.href=preUrl;
}



let loofCnt = 4-document.querySelector(".exc-whish-card-table").childElementCount;
for(let i = 0; i < loofCnt; i++){
	let cardContainer = document.createElement("div");
	let card = document.createElement("div");
	let img = document.createElement("img");
	img.setAttribute("src", "/resources/img/defaultCard.png");
	cardContainer.setAttribute("class", "card-contatiner");
	cardContainer.setAttribute("id", "default-card")
	card.setAttribute("class", "card");
	cardContainer.appendChild(card);
	card.appendChild(img);
	document.querySelector("#appendArea").appendChild(cardContainer);
}