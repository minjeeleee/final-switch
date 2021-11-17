let confirmEmail = '';
$('#btnEmailCheck').click(()=>{
	   
	let email = $('#email').val();
	   
	if(!email){
		$('#email').html('이메일을 입력하지 않았습니다.');
		return;
	}
	   
	fetch("/member/email-check?memberEmail=" + email)
	.then(response =>{
		if(response.ok){
			return response.text()
		}else{
			throw new Error(response.status);
		}
	})
	.then(text => {
		if(text == 'available'){
			confirmEmail = email;
			$('#emailCheck').html('사용 가능한 이메일 입니다.');
		}else{
			$('#emailCheck').html('사용 불가능한 이메일 입니다.');
		}
	})
	.catch(error => {
		$('#emailCheck').html('응답에 실패했습니다. 상태코드 : ' + error);
	})
});
   
let confirmNickname = '';
$('#btnNickCheck').click(()=>{
	   
	let nickname = $('#nickname').val();
	   
	if(!nickname){
		$('#nickname').html('닉네임을 입력하지 않았습니다.');
		return;
	}
	   
	fetch("/member/nickname-check?memberNick=" + nickname)
	.then(response =>{
		if(response.ok){
			return response.text()
		}else{
			throw new Error(response.status);
		}
	})
	.then(text => {
		if(text == 'available'){
			confirmNickname = nickname;
			$('#nickCheck').html('사용 가능한 닉네임 입니다.');
		}else{
			$('#nickCheck').html('사용 불가능한 닉네임 입니다.');
		}
	})
	.catch(error => {
		$('#nickCheck').html('응답에 실패했습니다. 상태코드 : ' + error);
	})
});
   

$('#frm_join').submit(e=>{
	let email = $('#email').val();
	let nickname = $('#nickname').val();
		
	if(!confirmEmail && confirmEmail != email){
		$('#emailCheck').html('이메일 중복 검사를 하지 않았습니다.');
		$('#email').focus();
		e.preventDefault();
	}
		
	if(!confirmNickname && confirmNickname != nickname){
		$('#nickCheck').html('닉네임 중복 검사를 하지 않았습니다.');
		$('#nickname').focus();
		e.preventDefault();
	}
})
   
$('#btn_cancel').click(()=>{
   	history.back();
})