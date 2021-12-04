$(document).ready(function(){
	replyList("${responseDto.id}");
});
		// 댓글 리스트 불러오기
		 function replyList(id){
		 	$.ajax({
		 		url:"/replys/${responseDto.id}",
		 		type:"GET",
		 		contentType : "application/json; charset=UTF-8",
		 		dataType: "JSON",
		 		success:function(data){
		 			$("#replyList").empty();
		 			$("#replyContent").val("");
		 			var html = "";
		 			$.each(data, function(index, value) {
		 				if(value.replyDepth == 0){
		 					if(value.accountId == "${principal.id}"){
		 						html += "<li class='list-group-item'>"+
		 								 	"<div style='position: relative; height: 100%'>"+
		 								  		"<div>"+
		 											"<div>"+
		 												"<span><a href=/account/info/"+value.accountId+">"+value.nickname+"</a></span><span class='text-muted'> | <small>"+uxin_timestamp(value.regdate)+" 작성</small></span>";
		 										if(value.modifyDate != null){
		 											html +=
		 												"<span class='text-muted'><small> ᛫ "+uxin_timestamp(value.modifyDate)+" 수정</small></span>";
		 										}
		 										if(value.enabled != 0){
		 											html +=	
		 												"<div id='dropdownForm-"+value.id+"' style='float: right;'>"+
		 													"<a onClick='replyToReplyForm("+value.id+")'>답글</a> ᛫ "+
		 													"<a onClick='replyUpdateForm("+value.id+")'>수정</a> ᛫ "+
		 													"<a onClick='deleteConfirm("+value.id+")'>삭제</a>"+
		 												"</div>";
		 										}
		 										html +=
		 												"<div id='replyForm-"+value.id+"' style='white-space : pre-wrap;height: 100%'>"+
		 													"<p id='reply-"+value.id+"'>"+value.content+"</p>"+
		 												"</div>"+
		 											"</div>"+
		 										"</div>"+
		 									"</div>"+
		 								"</li>";
		 					}else if('${principal.authorities}' == "[ROLE_ADMIN]"){
		 						html += 
		 							"<li class='list-group-item'>"+
		 							 	"<div style='position: relative; height: 100%'>"+
		 							  		"<div>"+
		 										"<div>"+
		 											"<span><a href=/account/info/"+value.accountId+">"+value.nickname+"</span><span class='text-muted'> | <small>"+uxin_timestamp(value.regdate)+" 작성</small></span>";
		 									if(value.modifyDate != null){
		 										html +=
		 											"<span class='text-muted'><small> ᛫ "+uxin_timestamp(value.modifyDate)+" 수정</small></span>";
		 									}
		 									if(value.enabled != 0){
		 										html +=	
		 											"<div id='dropdownForm-"+value.id+"' style='float: right;'>"+
		 												"<a onClick='replyToReplyForm("+value.id+")'>답글</a> ᛫ "+
		 												"<a onClick='deleteConfirm("+value.id+")'>삭제</a>"+
		 											"</div>";
		 									}
		 									html += 
		 											"<div id='replyForm-"+value.id+"' style='white-space : pre-wrap;height: 100%'>"+
		 												"<p id='reply-"+value.id+"'>"+value.content+"</p>"+
		 											"</div>"+
		 										"</div>"+
		 									"</div>"+
		 								"</div>"+
		 							"</li>";
		 					}
		 					else{
		 						html +=
		 							"<li class='list-group-item'><span><a href=/account/info/"+value.accountId+">"+value.nickname+"</span><span class='text-muted'> | <small>"+uxin_timestamp(value.regdate)+" 작성</small></span>";
		 						if(value.modifyDate != null){
		 							html +=
		 								"<span class='text-muted'><small> ᛫ "+uxin_timestamp(value.modifyDate)+" 수정</small></span>";
		 						}
		 						if(value.enabled != 0){
		 							html +=	
		 								"<div id='dropdownForm-"+value.id+"' style='float: right;'>"+
		 									"<a onClick='replyToReplyForm("+value.id+")'>답글</a>"+
		 								"</div>";
		 						}
		 						html +=	
		 								"<div id='replyForm-"+value.id+"' style='white-space : pre-wrap;height: 100%'>"+
		 									"<p id='reply-"+value.id+"'>"+value.content+"</p>"+
		 								"</div>"+
		 							"</li>";
		 					}
		 				}else{
		 					if(value.accountId == "${principal.id}"){
		 						html += "<li class='list-group-item'>"+
		 								 	"<div style='position: relative; height: 100%'>"+
		 								  		"<div>"+
		 											"<div>"+
		 												"<span style='margin-left:8px'>ㄴ"+value.nickname+"</span><span class='text-muted'> | <small>"+uxin_timestamp(value.regdate)+" 작성</small></span>";
		 										if(value.modifyDate != null){
		 											html +=
		 												"<span class='text-muted'><small> ᛫ "+uxin_timestamp(value.modifyDate)+" 수정</small></span>";
		 										}
		 										if(value.enabled != 0){
		 											html +=	
		 												"<div id='dropdownForm-"+value.id+"' style='float: right;'>"+
		 													"<a onClick='replyUpdateForm("+value.id+")'>수정</a> ᛫ "+
		 													"<a onClick='deleteConfirm("+value.id+")'>삭제</a>"+
		 												"</div>";
		 										}
		 										html +=
		 												"<div id='replyForm-"+value.id+"' style='white-space : pre-wrap;height: 100%'>"+
		 													"<p id='reply-"+value.id+"' style='margin-left:20px'>"+value.content+"</p>"+
		 												"</div>"+
		 											"</div>"+
		 										"</div>"+
		 									"</div>"+
		 								"</li>";
		 					}else if('${principal.authorities}' == "[ROLE_ADMIN]"){
		 						html += 
		 							"<li class='list-group-item'>"+
		 							 	"<div style='position: relative; height: 100%'>"+
		 							  		"<div>"+
		 										"<div>"+
		 											"<span style='margin-left:8px'>ㄴ"+value.nickname+"</span><span class='text-muted'> | <small>"+uxin_timestamp(value.regdate)+" 작성</small></span>";
		 									if(value.modifyDate != null){
		 										html +=
		 											"<span class='text-muted'><small> ᛫ "+uxin_timestamp(value.modifyDate)+" 수정</small></span>";
		 									}
		 									if(value.enabled != 0){
		 										html +=	
		 											"<div id='dropdownForm-"+value.id+"' style='float: right;'>"+
		 												"<a onClick='deleteConfirm("+value.id+")'>삭제</a>"+
		 											"</div>";
		 									}
		 									html += 
		 											"<div id='replyForm-"+value.id+"' style='white-space : pre-wrap;height: 100%'>"+
		 												"<p id='reply-"+value.id+"' style='margin-left:20px'>"+value.content+"</p>"+
		 											"</div>"+
		 										"</div>"+
		 									"</div>"+
		 								"</div>"+
		 							"</li>";
		 					} else{
		 						html +=
		 							"<li class='list-group-item'><span style='margin-left:8px'>ㄴ"+value.nickname+"</span><span class='text-muted'> | <small>"+uxin_timestamp(value.regdate)+" 작성</small></span>";
		 						if(value.modifyDate != null){
		 							html +=
		 								"<span class='text-muted'><small> ᛫ "+uxin_timestamp(value.modifyDate)+" 수정</small></span>";
		 						}
		 						html +=	
		 								"<div id='replyForm-"+value.id+"' style='white-space : pre-wrap;height: 100%'>"+
		 									"<p id='reply-"+value.id+"' style='margin-left:20px'>"+value.content+"</p>"+
		 								"</div>"+
		 							"</li>";
		 					}
		 				}
		 				
		 				html +=
		 					"<div id='updateForm-"+value.id+"' style='display: none;'>"+
		 						"<form method='post' action='/reply/"+value.id+"' onsubmit='return replyUpdate("+value.id+");'>"+
		 							"<input type='hidden' name='_method' value='PUT'>"+
		 							"<textarea id='replyContent-"+value.id+"' name='content' class='form-control z-depth-1' rows='3' maxlength='1000' placeholder='댓글을 입력해주세요.'>"+value.content+"</textarea>"+
		 							"<input type='submit' style='width:50%' class='btn btn-success' value='수정'>"+
		 							"<input type='button' style='width:50%' class='btn btn-primary' value='취소' onclick='replyForm("+value.id+")'>"+
		 							"</form>"+
		 					"</div>"+
		 					"<div id='replyToReplyForm-"+value.id+"' style='display: none;'>"+
		 						"<form method='post' action='/reply' onsubmit='return replyToReply("+value.id+","+value.replyGroup+");'>"+
		 							"<textarea id='replyToReply-"+value.id+"' name='content' class='form-control z-depth-1' rows='3' maxlength='1000' placeholder='답글을 입력해주세요.'></textarea>"+
		 							"<input type='submit' style='width:50%' class='btn btn-success' value='입력'>"+
		 							"<input type='button' style='width:50%' class='btn btn-primary' value='취소' onclick='replyForm("+value.id+")'>"+
		 						"</form>"+
		 					"</div>";
		 				document.getElementById('replyList').innerHTML = html;
		 			});
		 		},
		 		error:function(request,status,error){
		 			jsonValue = jQuery.parseJSON(request.responseText);
		 			code = jsonValue.code;
		 			console.log(code + ":"+jsonValue.message);
		 			if(code == 'R001'){
		 				$("#replyList").append(
		 					"<li class='list-group-item'>"+
		 						"<div>"+
		 							"<div>"+
		 								"<div>"+
		 									"<div id='replyForm'>"+
		 										"<span>"+ jsonValue.message +"</span>"+
		 									"</div>"+
		 								"</div>"+
		 							"</div>"+
		 						"</div>"+
		 					"</li>"
		 				);
		 			}
		 			
		 		}
		 	});
		 	return false;
		 }
		 //댓글 입력 폼 요청
		 function replyForm(id){
		 	if("${principal.id}" == ""){
		 		login();
		 		return;
		 	}
		 	var dropdownForm = $("#dropdownForm-"+id);
		 	var replyForm = $("#replyForm-"+id);
		 	var updateForm = $("#updateForm-"+id);
		 	var replyToReplyForm = $("#replyToReplyForm-"+id);
		 	
		 	updateForm.hide();
		 	replyToReplyForm.hide();
		 	replyForm.show();
		 	dropdownForm.show();
		 	$("#replyForm-"+id).focus();
		 }
		 // 댓글 업데이트 폼 요청
		 function replyUpdateForm(id){
		 	if("${principal.id}" == ""){
		 		login();
		 		return;
		 	}
		 	var dropdownForm = $("#dropdownForm-"+id);
		 	var replyForm = $("#replyForm-"+id);
		 	var updateForm = $("#updateForm-"+id);
		 	var replyToReplyForm = $("#replyToReplyForm-"+id);
		 	
		 	$("#replyContent-"+id).val($("#reply-"+id).text());
		 	replyForm.hide();
		 	dropdownForm.hide();
		 	replyToReplyForm.hide();
		 	updateForm.show();
		 	$("#replyContent-"+id).focus();
		 }
		 //대댓글 폼 요청
		 function replyToReplyForm(id){
		 	if("${principal.id}" == ""){
		 		login();
		 		return;
		 	}
		 	var dropdownForm = $("#dropdownForm-"+id);
		 	var replyForm = $("#replyForm-"+id);
		 	var updateForm = $("#updateForm-"+id);
		 	var replyToReplyForm = $("#replyToReplyForm-"+id);
		 	
		 	$("#replyToReply-"+id).val("");
		 	replyForm.show();
		 	dropdownForm.hide();
		 	updateForm.hide();
		 	replyToReplyForm.show();
		 	$("#replyToReply-"+id).focus();
		 }
		 //댓글 입력
		 function replyCreate(){
		 	var content = $("#replyContent").val().replace(/\s|/gi,'');
		 	
		 	if(content==""){
		 		alert("댓글을 입력해주세요.");
		 		$("#replyContent").val("");
		 		$("#replyContent").focus();
		 		return false;
		 	}
		 	
		 	var requestReplyCreateDto = {
		 			articleId : "${responseDto.id}",
		 			accountId : "${principal.id}",
		 			replyGroup : 0,
		 			content : $("#replyContent").val()
		 	}
		 	
		 	$.ajax({
		 		url:"/reply",
		 		type:"post",
		 		contentType : "application/json; charset=UTF-8",
		 		data: JSON.stringify(requestReplyCreateDto),
		 		success:function(data){
		 			replyList("${responseDto.id}");
		 		},
		 		error:function(request,status,error){
		 			jsonValue = jQuery.parseJSON(request.responseText);
		 			code = jsonValue.code;
		 			if(code == 'C003'){
		 				console.log(code +" : "+jsonValue.message);
		 				alert("댓글을 입력해주세요.");
		 				$("#replyContent").val("");
		 				$("#replyContent").focus();
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
		 //대댓글 입력 
		 function replyToReply(id,replyGroup){
		 	var content = $("#replyToReply-"+id).val().replace(/\s|/gi,'');
		 	
		 	if(content==""){
		 		alert("대댓글을 입력해주세요.");
		 		$("#replyToReplyForm").val("");
		 		$("#replyToReply-"+id).focus();
		 		return false;
		 	}
		 	
		 	var requestReplyCreateDto = {
		 			articleId : "${responseDto.id}",
		 			accountId : "${principal.id}",
		 			replyGroup : replyGroup,
		 			content : content
		 	}
		 	
		 	$.ajax({
		 		url:"/reply",
		 		type:"post",
		 		contentType : "application/json; charset=UTF-8",
		 		data: JSON.stringify(requestReplyCreateDto), 
		 		success:function(data){
		 			replyList("${responseArticleDto.id}");
		 		},
		 		error:function(request,status,error){
		 			jsonValue = jQuery.parseJSON(request.responseText);
		 			code = jsonValue.code;
		 			if(code == 'C003'){
		 				console.log(code +" : "+jsonValue.message);
		 				alert("대댓글을 입력해주세요." + id);
		 				$("#replyToReplyForm").val("");
		 				$("#replyToReply-"+id).focus();
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
		 // 댓글 업데이트 
		 function replyUpdate(id){
		 	
		 	var requestReplyUpdateDto = {
		 		id : id,
		 		articleId : "${responseDto.id}",	
		 		accountId : "${principal.id}",
		 		content : $("#replyContent-"+id).val()
		 	}
		 	
		 	$.ajax({
		 		url:"/reply/"+id,
		 		type:"patch",
		 		contentType : "application/json; charset=UTF-8",
		 		data: JSON.stringify(requestReplyUpdateDto), 
		 		success:function(data){
		 			replyList("${responseDto.id}");
		 		},
		 		error:function(request,status,error){
		 			jsonValue = jQuery.parseJSON(request.responseText);
		 			code = jsonValue.code;
		 			if(code == 'B001'){
		 				console.log(code +" : "+jsonValue.message);
		 				alert(jsonValue.message);
		 				history.back();
		 			}
		 			if(code == 'C003'){
		 				console.log(code +" : "+jsonValue.message);
		 				$("#replyContent-"+id).val("");
		 				$("#replyContent-"+id).focus();
		 			}
		 			if(code == 'R002'){
		 				console.log(code +" : "+jsonValue.message);
		 				alert(jsonValue.message);
		 				replyList("${responseDto.id}");
		 			}
		 		}
		 	});
		 	return false;
		 }
		 function deleteConfirm(id){
		 	if(confirm("댓글을 삭제하시겠습니까?")){
		 		replyDelete(id);
		 	}else{
		 		return;
		 	}
		 }
		 // 댓글 삭제 
		 function replyDelete(id){
		 	var requestReplyDeleteDto = {
		 		id : id,
		 		articleId : "${responseDto.id}",
		 		accountId : "${principal.id}",
		 	}
		 	
		 	$.ajax({
		 		url:"/reply/"+id,
		 		type:"delete",
		 		contentType : "application/json; charset=UTF-8",
		 		data: JSON.stringify(requestReplyDeleteDto),
		 		success:function(data){
		 			alert("댓글이 삭제되었습니다.");
		 			replyList("${responseDto.id}");
		 		},
		 		error:function(request,status,error){
		 			jsonValue = jQuery.parseJSON(request.responseText);
		 			code = jsonValue.code;
		 			if(code == 'B001'){
		 				console.log(code +" : "+jsonValue.message);
		 				alert(jsonValue.message);
		 				history.back();
		 			}
		 			if(code == 'R002'){
		 				console.log(code +" : "+jsonValue.message);
		 				alert(jsonValue.message);
		 				replyList("${responseDto.id}");
		 			}
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
