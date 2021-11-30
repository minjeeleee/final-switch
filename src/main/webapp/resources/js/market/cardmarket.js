	/* 비동기 통신시 header는 headerObj를 가져와서 사용하고 추가로 header를 지정해야할 때도 여기에 추가해서 사용 */
    let getCsrfHeader = () =>{
        let headerObj = new Headers();
        let csrfHeader = "[(${_csrf.headerName})]";
        let csrfToken = "[(${_csrf.token})]";
        headerObj.append(csrfHeader,csrfToken);
        return headerObj;
    }
   	
        $('.detail-img').slick({
            autoplay:true,
            infinite:true,
            dots:false,
            arrows: false,
        });

        window.onload = ()=> {
            $.ajax({

                type:'get',
                url:"http://192.168.219.103:8090/market/getcard",
                dataType: "json",
                async:false,

                success:(data) => {

                    var result = '';
                    var lastindex = '';

                    $.each(data, (index,value) => {

                    result += '<div class="card-container">'
                    result += '<input type="hidden" id="cardIdx" name="cardIdx" value="'+value.cardIdx+'">'
                    result += '<div class="card-name">'
                    result += '<p>'+value.name+'</p>'
                    result += '</div>'
                    result += '<div class="grade-container">'
                    result += '<div class="icon">'
                    result += '<i class="fas fa-bolt"></i>'
                    result += '</div>'
                    result += '<div class="user-grade">'
                    result += '<p>사용자 평점</p>'
                    result += '<div class="star">'
                    result += '<i class="fas fa-star"></i>'
                    result += '<i class="fas fa-star"></i>'
                    result += '<i class="fas fa-star"></i>'
                    result += '<i class="fas fa-star"></i>'
                    result += '<i class="fas fa-star"></i>'
                    result += '</div>'
                    result += '</div>'
                    result += '<div class="item-grade">'
                    result += '<p>물건 평점</p>'
                    result += '<div class="star">'
                    result += '<i class="fas fa-star"></i>'
                    result += '<i class="fas fa-star"></i>'
                    result += '<i class="fas fa-star"></i>'
                    result += '<i class="fas fa-star"></i>'
                    result += '<i class="fas fa-star"></i>'
                    result += '</div>'
                    result += '</div>'
                    result += '</div>'
                    result += '<div class="address">'
                    result += '<p>지역 <span>'+value.region+'</span><span>'+value.regionDetail+'</span></p>'
                    result += '</div>'
                    result += '<div class="card">'
                    result += '<img src="../resources/img/ddongCard.png" alt="">'
                    result += '<div class="card-img">'
                    result += '<img src="../resources/img/iphone.png" alt="">'
                    result += '</div>'
                    result += '</div>'
                    result += '</div>'
                   

                    lastindex = index+1;
                    
                })

                $('.card-list').append(result);
                $('.last-card').text(lastindex);

                },

                error:(err) => {
                    console.log(err)
                }

                })

                $('.card-container').click(() => {
                    $('.popup-detail').removeClass('hide')
                })

                $('.close-button').click(() => {
                    $('.popup-detail').addClass('hide')
                })

        }
        
        $('.searchbox').change(() => {
        	let category={"category" : $('select[name="category"]').val()
                      ,"region" : $('select[name="location"]').val()
                      ,"content" : $('input[name="content"]').val()}
            console.log(category);
        	
        	 if($('select[name="category"]').val() != '') {
                 $('.sc-category').text($('select[name="category"]').val())
             } else {
                 $('.sc-category').text('전체검색')
             }

			 if($('select[name="location"]').val() != '') {
			     $('.sc-location').removeClass('hide')
			     $('.sc-location').text($('select[name="location"]').val())
			 } else {
			     $('.sc-location').addClass('hide')
			 }

            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            console.log(token);
            console.log(header);
            
            $.ajax({

                type: 'post',
                url: "http://192.168.219.103:8090/market/category",
                dataType: "json",
                async: false,
                data:  JSON.stringify(category),
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header,token);
                },
                contentType: 'application/json',

                success: (data) => {
                	$(".card-list").empty();
                	
                    var result = '';
                    var lastindex = '';

                    $.each(data, (index, value) => {

                        result += '<div class="card-container">'
                        result +=
                            '<input type="hidden" id="cardIdx" name="cardIdx" value="' +
                            value.cardIdx + '">'
                        result += '<div class="card-name">'
                        result += '<p>' + value.name + '</p>'
                        result += '</div>'
                        result += '<div class="grade-container">'
                        result += '<div class="icon">'
                        result += '<i class="fas fa-bolt"></i>'
                        result += '</div>'
                        result += '<div class="user-grade">'
                        result += '<p>사용자 평점</p>'
                        result += '<div class="star">'
                        result += '<i class="fas fa-star"></i>'
                        result += '<i class="fas fa-star"></i>'
                        result += '<i class="fas fa-star"></i>'
                        result += '<i class="fas fa-star"></i>'
                        result += '<i class="fas fa-star"></i>'
                        result += '</div>'
                        result += '</div>'
                        result += '<div class="item-grade">'
                        result += '<p>물건 평점</p>'
                        result += '<div class="star">'
                        result += '<i class="fas fa-star"></i>'
                        result += '<i class="fas fa-star"></i>'
                        result += '<i class="fas fa-star"></i>'
                        result += '<i class="fas fa-star"></i>'
                        result += '<i class="fas fa-star"></i>'
                        result += '</div>'
                        result += '</div>'
                        result += '</div>'
                        result += '<div class="address">'
                        result += '<p>지역 <span>' + value.region + '</span><span>' + value
                            .regionDetail + '</span></p>'
                        result += '</div>'
                        result += '<div class="card">'
                        result += '<img src="../resources/img/ddongCard.png" alt="">'
                        result += '<div class="card-img">'
                        result += '<img src="../resources/img/iphone.png" alt="">'
                        result += '</div>'
                        result += '</div>'
                        result += '</div>'

                        lastindex = index + 1;

                    })

                    $('.card-list').append(result);
                    console.log(data.length)
                    if(data.length == 0) {
                        $('.last-card').text('0');
                    } else {
                        $('.last-card').text(lastindex);
                    }

                },

                error: (err) => {
                    console.log(err)
                }

            })

            $('.card-container').click(() => {
                $('.popup-detail').removeClass('hide')
            })

            $('.close-button').click(() => {
                $('.popup-detail').addClass('hide')
            })

        })
        
          function search() {
            let category={"category" : $('select[name="category"]').val()
                        ,"region" : $('select[name="location"]').val()
                        ,"content" : $('input[name="content"]').val()}
            console.log(category);
            
            if($('input[name="content"]').val() != '') {
                $('.sc-input').removeClass('hide')
                $('.sc-input').text($('input[name="content"]').val())
            } else {
                $('.sc-input').addClass('hide')
            }

            var token = "bd5667d9-4a6f-41c9-b7ba-67ad50ff8479"
            var header = "X-CSRF-TOKEN"

            $.ajax({

                type: 'post',
                url: "http://192.168.219.103:8090/market/category",
                dataType: "json",
                async: false,
                data: JSON.stringify(category),
                contentType: 'application/json',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header,token);
                },

                success: (data) => {
                    $(".card-list").empty();
                    
                    var result = '';
                    var lastindex = '';

                    $.each(data, (index, value) => {

                        result += '<div class="card-container">'
                        result +=
                            '<input type="hidden" id="cardIdx" name="cardIdx" value="' +
                            value.cardIdx + '">'
                        result += '<div class="card-name">'
                        result += '<p>' + value.name + '</p>'
                        result += '</div>'
                        result += '<div class="grade-container">'
                        result += '<div class="icon">'
                        result += '<i class="fas fa-bolt"></i>'
                        result += '</div>'
                        result += '<div class="user-grade">'
                        result += '<p>사용자 평점</p>'
                        result += '<div class="star">'
                        result += '<i class="fas fa-star"></i>'
                        result += '<i class="fas fa-star"></i>'
                        result += '<i class="fas fa-star"></i>'
                        result += '<i class="fas fa-star"></i>'
                        result += '<i class="fas fa-star"></i>'
                        result += '</div>'
                        result += '</div>'
                        result += '<div class="item-grade">'
                        result += '<p>물건 평점</p>'
                        result += '<div class="star">'
                        result += '<i class="fas fa-star"></i>'
                        result += '<i class="fas fa-star"></i>'
                        result += '<i class="fas fa-star"></i>'
                        result += '<i class="fas fa-star"></i>'
                        result += '<i class="fas fa-star"></i>'
                        result += '</div>'
                        result += '</div>'
                        result += '</div>'
                        result += '<div class="address">'
                        result += '<p>지역 <span>' + value.region + '</span><span>' + value
                            .regionDetail + '</span></p>'
                        result += '</div>'
                        result += '<div class="card">'
                        result += '<img src="../resources/img/ddongCard.png" alt="">'
                        result += '<div class="card-img">'
                        result += '<img src="../resources/img/iphone.png" alt="">'
                        result += '</div>'
                        result += '</div>'
                        result += '</div>'

                        lastindex = index + 1;

                    })

                    $('.card-list').append(result);
                    console.log(data.length)
                    if(data.length == 0) {
                        $('.last-card').text('0');
                    } else {
                        $('.last-card').text(lastindex);
                    }

                },

                error: (err) => {
                    console.log(err)
                }

            })

            $('.card-container').click(() => {
                $('.popup-detail').removeClass('hide')
            })

            $('.close-button').click(() => {
                $('.popup-detail').addClass('hide')
            })
        }