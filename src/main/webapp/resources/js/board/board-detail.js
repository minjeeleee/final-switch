
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



//댓글 입력
function commentCreate(){
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
		success:(data) => {
			console.dir(data);

		},
		error:(error) => {
			 console.log(error)
		}
	});
	return false;
}

// 댓글 수정창

			var html = "";
			$.each(data, function(index, value) {
				if(value.replyDepth == 0){
					if(value.userId ==  "${#authentication.principal.memberNick}"){ 
						html += "<div id='updateForm-"+value.id+"' style='display: none;'>"+
						"<form method='post' action='/reply/"+value.id+"' onsubmit='return replyUpdate("+value.id+");'>"+
							"<input type='hidden' name='_method' value='PUT'>"+
							"<textarea id='replyContent-"+value.id+"' name='content' class='form-control z-depth-1' rows='3' maxlength='1000' placeholder='댓글을 입력해주세요.'>"+value.content+"</textarea>"+
							"<input type='submit' style='width:50%' class='btn btn-success' value='수정'>"+
							"<input type='button' style='width:50%' class='btn btn-primary' value='취소' onclick='replyForm("+value.id+")'>"+
							"</form>"+
					"</div>"

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



function deleteConfirm(id){
	if(confirm("댓글을 삭제하시겠습니까?")){
		commentDelete(id);
	}else{
		return;
	}
}

// 댓글 삭제 
function commentDelete(){
	var content = $("#commentContent").val();

	var reply = {
			"cmIdx" :$(".cmIdx").val(),
			"userId" :userId,
			"content" : $("#commentContent").val()
	}

	$.ajax({
		url:"http://localhost:9191/board/delete-reply",
		type:"post",
		 dataType: "json",
		contentType : "application/json",
		data: JSON.stringify(reply),
		 beforeSend: function (xhr) {
            xhr.setRequestHeader(header,token);
        },
		success:(data) => {
			alert("댓글이 삭제되었습니다.");
		},
		error:(error) => {
			 console.log(error)
		}
	});
	return false;
}

function uxin_timestamp(time){
	var date = new Date(time);
	var year = date.getFullYear();
	var month = "0" + (date.getMonth()+1);
	var day = "0" + date.getDate();
	var hour = "0" + date.getHours();
	var minute = "0" + date.getMinutes();
	//var second = "0" + date.getSeconds();
	return year + "-" + month.substr(-2) + "-" + day.substr(-2) + " " + hour.substr(-2) + ":" + minute.substr(-2);
}

function listConfirm(id){
	if(confirm("새로고침 하시겠습니까?")){
		commentList(id);
	}else{
		return;
	}
}


function login(){
	if(confirm("로그인 하시겠습니까?")){
		location.href="/member/login";	
	}else{
		return;
	}
	return false;
	
}