/**
 * 
 */
 (()=>{
	  let cofirmPw = "";
	  let cofirmNick = document.querySelector('#memberNick').value;
	  
	document.querySelector('#btnNickCheck').addEventListener('click', ()=>{
		  
		   const nickName = document.querySelector('#memberNick').value;
		   const password = document.querySelector('#memberPass').value;
		   const memberIdx = document.querySelector('#memberIdx').value;
		   
		   if(nickName){
				   fetch("/admin/nick-check?nickName="+nickName)
				   .then(response =>{
						if(response.ok){
							return response.text()
						}else{
							throw new Error(response.status);
						}
					})
				   .then(text => {
					console.dir(text);
					   if(text == 'available'){
						   cofirmNick = nickName;
						   document.querySelector('#nickNameCheck').innerHTML = '사용 가능한 닉네임 입니다';
					   }else{
						   document.querySelector('#nickNameCheck').innerHTML = '사용 불가능한 닉네임 입니다';
					   }
				   })
					.catch(error=>{
						 document.querySelector('#nickNameCheck').innerHTML ='응답에 실패했습니다  상태코드 : '+error;
					})
				}
	   });
	   
	   
	   
	   
	   
	   document.querySelector('#frm_modify').addEventListener('submit',e=>{
		   
		   
		   const pwReg = /(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[^a-zA-Zㄱ-힣0-9])(?=.{8,})/;
		   const nickReg = /.*[0-9a-zA-Z가-힣]{2,}"/;
		   
		   if(cofirmNick != nickName && nickName != null){
				console.dir("닉네임 접근");
			   alert('닉네임 중복 검사를 하지 않았습니다');
			   document.querySelector('#memberNick').focus();
			   e.preventDefault();
		   }
		   console.log("접근준비");
		   
		   if(password){
			   if(!pwReg.test(password)){
					alert('땡');
					console.log("접근");
					e.preventDefault();
					document.querySelector('#pwCheck').innerHTML = '비밀번호는 숫자, 영문자, 특수문자 조합의 8글자 이상인 문자열 입니다.';
				}
		   }
			fetch("/admin/member-profile-edit-success?memberIdx="+memberIdx)
				.then(response =>{
					if(response.ok){
						return response.text()
					}else{
						throw new Error(response.status);
					}
				})
				.then(text =>{
					if(text == 'success'){
						alert('회원정보가 수정되었습니다.');
						location.href("admin/member-profile-edit?memberIdx"+memberIdx);
					}else{
						alert('에러');
					}
				})
				.catch(error =>{
					alert('실패');
				})
	   })
  })();