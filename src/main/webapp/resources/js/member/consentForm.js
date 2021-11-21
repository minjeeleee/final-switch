

$(".next_btn").on("click",function(){
	//필수 동의만 필요한 경우
	if($("input:checked").val() == "consent"){
		$('<form id="consentForm" action="/member/consentForm" method="post"></form>').appendTo("body");
		$('<input type="hidden" name="consent" value="consent">').appendTo("#consentForm");
		$("#consentForm").submit();
	} else {
		alert("개인정보 제공 동의가 필요합니다.");
		return;
	}
})