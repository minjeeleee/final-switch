var token = "bd5667d9-4a6f-41c9-b7ba-67ad50ff8479"
var header = "X-CSRF-TOKEN"


function createExchangeHistory(value) {
    var result = '';
   	var memberIdx= $('input[type="hidden"]').val();
	
    result +=  '<tr><td>'+value.eh.exchangeDate+'</td>'
    if(memberIdx == value.eh.requestedMemIdx){
		 result += '<td>'+value.requestedCardName+'</td>'
		 result += '<td>'+value.requestCardName+'</td>'
	}
	if(memberIdx == value.eh.requestMemIdx){
		 result += '<td>'+value.requestCardName+'</td>'
		 result += '<td>'+value.requestedCardName+'</td>'
	}
    result += '<td>'+value.crl.propBalance+'</td>'
    result += '<td>'+value.opponentNickList+'</td>'
    if(value.isRate == 0){
		result += ' <td><a href="#" class="btn btn-danger btn-circle btn-sm"> <i class="far fa-star"></i> </a> </td></tr>'
	}else{
		result += ' <td><a href="#" class="btn btn-success btn-circle btn-sm"> <i class="far fa-star"></i> </a> </td></tr>'
	}
    return result
}

function createFreeHistory(value) {
    var result = '';
   	var memberIdx= $('input[type="hidden"]').val();

    result +=  '<tr><td>'+value.eh.exchangeDate+'</td>'
    if(memberIdx == value.eh.requestedMemIdx){
		 result += '<td>'+value.requestedCardName+'</td>'
		  result += '<td> X </td>'
	}
   if(memberIdx == value.eh.requestMemIdx){
		 result += '<td> X </td>'
		 result += '<td>'+value.requestedCardName+'</td>'
	}

    result += '<td> 0 </td>'
    result += '<td>'+value.opponentNickList+'</td>'
    if(value.isRate == 0){
		result += '<td><a href="#" class="btn btn-danger btn-circle btn-sm"> <i class="far fa-star"></i> </a></td>/tr>'
	}else{
		result += '<td><a href="#" class="btn btn-success btn-circle btn-sm"> <i class="far fa-star"></i> </a></td></tr>'
	}
    return result
}

$(() =>{
   exchangeHistory()
})

$("input[name='type']:radio").change(function search() {
    let category= $("input[name='type']:checked").val();
                
     $('.historyList *').remove();
	if(category == 'exchange'){
	   exchangeHistory();
    }
    
    if(category == 'freeSharing'){
	    $.ajax({
	        type: 'get',
	        url: "/mypage/free-history",
	        dataType: "json",
	        async: false,
	
	        success: (data) => {
	
	            var result = '';
	
	            $.each(data, (index, value) => {
	                result += createFreeHistory(value)
	            })
	
	            $('.historyList').append(result);
	        },
	
	        error: (err) => {
	            console.log(err)
	        }
	
	    })
    }
})

function exchangeHistory(){
	 $.ajax({
	        type: 'get',
	        url: "/mypage/exchange-history",
	        dataType: "json",
	        async: false,
	
	        success: (data) => {
	
	            var result = '';
	
	            $.each(data, (index, value) => {
	                result += createExchangeHistory(value)
	            })
	            $('.historyList').append(result);
	        },
	        error: (err) => {
	            console.log(err)
	        }
	
	    })
}





