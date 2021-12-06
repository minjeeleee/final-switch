
function replyList(){
		$.ajax({
		url:"http://localhost:9191/board/reply-list",
		type:"post",
		 dataType: "json",
		contentType : "application/json",
		data: JSON.stringify(reply),
		 beforeSend: function (xhr) {
            xhr.setRequestHeader(header,token);
        },
		success:(data) => {
			console.dir(data);
	$('#commentList').append(result);
		},
		error:(error) => {
			 console.log(error)
		}
	});
	return false;
}

function uploadConfirm(cmIdx){
	if(confirm("댓글을 입력하시겠습니까?")){
		commentCreate(cmIdx);
	}else{
		return;
	}
}
//댓글 입력
function commentCreate(data){
	var content = $("#commentContent").val();
	
	if(content==""){
		alert("댓글을 입력해주세요.");
		$("#commentContent").val("");
		$("#commentContent").focus();
		return false;
	}
	
	var reply = {
			"bdIdx" :$(".bdIdx").val(),
			"userId" :userId,
			"content" : $("#commentContent").val()
	}
	console.log($(".bdIdx").val())
	$.ajax({
		url:"http://localhost:9191/board/upload-reply",
		type:"post",
		 dataType: "json",
		contentType : "application/json",
		data: JSON.stringify(reply),
		 beforeSend: function (xhr) {
            xhr.setRequestHeader(header,token);
        },
		success:(text) => {
			console.dir(text);
			console.dir(document.getElementById(cmIdx));
			document.getElementById(cmIdx).style.visibility = 'visible';
			alert("댓글이 입력되었습니다.");

		},
		
		error:(error) => {
			 console.log(error)
		}
	});
	return false;
}



//댓글 수정
function updateComment(){
	var content = $("#commentContent").val();
	
	var reply = {
			"bdIdx" :$(".bdIdx").val(),
			"userId" :userId,
			"content" : $("#commentContent").val()
	}

	$.ajax({
		url:"http://localhost:9191/board/modify-reply",
		type:"post",
		 dataType: "json",
		contentType : "application/json",
		data: JSON.stringify(reply),
		 beforeSend: function (xhr) {
            xhr.setRequestHeader(header,token);
        },
		success:(data) => {
			console.dir(data);

		},
		error:(error) => {
			 console.log(error)
		}
	});
	return false;
}


function deleteConfirm(idx){
	if(confirm("댓글을 삭제하시겠습니까?")){
		commentDelete(idx);
	}else{
		return;
	}
}

// 댓글 삭제 
function commentDelete(idx){
	console.log(idx)
	var cmIdx  = $(".cmIdx").val();
	console.log(cmIdx)
	console.log(typeof cmIdx)
	console.log(userId)
	$.ajax({
		url:"http://localhost:9191/board/delete-reply?cmIdx="+cmIdx,
		type:"post",
		contentType : "application/json",
		 beforeSend: function (xhr) {
            xhr.setRequestHeader(header,token);
        },
		success:(data) => {
			console.dir(document.getElementById(idx));
			document.getElementById(idx).style.display = 'none';
			alert("댓글이 삭제되었습니다.");
			
		},
		error:(error) => {
			 console.log(error)
		}
	});
	return false;
}

function login(){
	if(confirm("로그인 하시겠습니까?")){
		location.href="/member/login";	
	}else{
		return;
	}
	return false;
	
}

//댓글 입력 폼 요청
function commentForm(id){
	if("${principal.id}" == ""){
		login();
		return;
	}
	var dropdownForm = $("#dropdownForm-"+id);
	var commentForm = $("#commentForm-"+id);
	var updateForm = $("#updateForm-"+id);
	var commentTocommentForm = $("#commentTocommentForm-"+id);
	
	updateForm.hide();
	commentTocommentForm.hide();
	commentForm.show();
	dropdownForm.show();
	$("#commentForm-"+id).focus();
}

// 댓글 업데이트 폼 요청
function commentUpdateForm(id){

	var dropdownForm = $("#dropdownForm-"+id);
	var commentForm = $("#commentForm-"+id);
	var updateForm = $("#updateForm-"+id);
	var commentTocommentForm = $("#commentTocommentForm-"+id);
	
	$("#commentContent-"+id).val($("#comment-"+id).text());
	commentForm.hide();
	dropdownForm.hide();
	commentTocommentForm.hide();
	updateForm.show();
	$("#commentContent-"+id).focus();
}

//대댓글 폼 요청
function commentTocommentForm(id){
	if("${principal.id}" == ""){
		login();
		return;
	}
	var dropdownForm = $("#dropdownForm-"+id);
	var commentForm = $("#commentForm-"+id);
	var updateForm = $("#updateForm-"+id);
	var commentTocommentForm = $("#commentTocommentForm-"+id);
	
	$("#commentTocomment-"+id).val("");
	commentForm.show();
	dropdownForm.hide();
	updateForm.hide();
	commentTocommentForm.show();
	$("#commentTocomment-"+id).focus();
}




//대댓글 입력 
function commentTocomment(id,commentGroup){
	var content = $("#commentTocomment-"+id).val().replace(/\s|/gi,'');
	
	if(content==""){
		alert("대댓글을 입력해주세요.");
		$("#commentTocommentForm").val("");
		$("#commentTocomment-"+id).focus();
		return false;
	}
	
	var reply = {
			"bdIdx" : "${reply.bdIdx}",
			"userId" : "${reply.userId}",
			"cmParent" : cmParent,
			"content" : content
	}
	
	$.ajax({
		url:"/board/upload-reply",
		type:"post",
		contentType : "application/json; charset=UTF-8",
		data: JSON.stringify(reply), 
		success:function(data){
			commentList("${reply.cmIdx}");
		},
		error:function(request,status,error){
			jsonValue = jQuery.parseJSON(request.responseText);
			code = jsonValue.code;
			if(code == 'C003'){
				console.log(code +" : "+jsonValue.message);
				alert("대댓글을 입력해주세요." + id);
				$("#commentTocommentForm").val("");
				$("#commentTocomment-"+id).focus();
			}
			if(code == 'B001'){
				console.log(code +" : "+jsonValue.message);
				alert(jsonValue.message);
				history.back();
			}
		}
	});
	return false;
}