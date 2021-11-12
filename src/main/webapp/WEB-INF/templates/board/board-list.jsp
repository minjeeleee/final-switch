<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/resources/css/board/board.css">
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
.section *{
	font-size: 13px;
}
.section{
	margin-top: 30px;
	width: 1200px;
	display: flex;
	justify-content: center;
	margin-bottom: 60px;
}
.wrapper{
	width: 80%;
	height: 100%;
}
.sub_title{
	width: 100%;
	height: 46px;
	display: flex;
	align-items: center;
	justify-content: space-between;
	border-bottom: solid;
	border-width: 1px;
}
.sub_title>h2{
	font-size: 22px;
	font-weight: 700;
}
.btn_area{
	height: 36px;
	display: flex;
	justify-content: flex-end;
	align-items: center;
}
.btn_area>a{
	width: 70px;
	height: 30px;
	font-size: 15px;
	background-color: #eeeeee;
	line-height: 28px;
	text-align: center;
	margin-left: 10px;
	color: black;
}
.row{
	margin-top: 14px;
}
.row,.table{
	width: 100%;
}
th{
	background-color: #dddddd;
	line-height: 30px;
}
td{
	text-align: center;
	border-bottom: 1px solid #eeeeee;
	border-top: 1px solid #eeeeee;
}
.post_title{
	text-align: left;
	padding-left: 10px;
	padding-right: 10px;
}

.footer{
	width: 100%;
	margin-top: 10px;
}
.total_page_info{
	margin-bottom: 10px;
}
.search_bar_wrap{
	height: 30px;
	display: flex;
	align-items: center;
}
.search_bar_wrap select{
	width: 100px;
	height: 31px;
	text-align: center;
}
.search_bar_wrap input{
	width: 300px;
	height: 25px;
}
.search_bar_wrap button{
	height: 31px;
	border: 1px solid;
	background-color: #eeeeee;
}
.btn{
	border-color: transparent;
	border-radius:5px;
}
#btnWrite{
	background-color: var(--point-color);	
	color: #fff;
}
.paging_box{
	display: flex;
	justify-content: center;
	align-items: center;
	padding-top: 20px;
}
.paging_box>span>i,.paging_box>a{
	margin: 0 10px;
}
.paging_box>ul{
	 display: flex;
	 justify-content: center;
}
.paging_box>ul>li{
	 display: inline-block;
	 margin: 0 5px;
}
.color{
	color: rgb(39,174,96);
}
.bold{
	font-weight: bold;
}
</style>
</head>
<body>

  <section>
    <div class="container">
    
		<div class='section'>
			<div class='wrapper'>
				<div class="sub_title">
					<h2>정보공유게시판</h2>
						<form class="search_bar_wrap">
							<input type="text" name="q" placeholder="검색어를 입력하세요." />
							<button onclick="submit">검색</button>
						</form>
					</div>
				
				<div class="row">
					<table class="table" style="border: 1px solid #dddddd">
						<thead>
							<tr style="height: 30px">
								<th style="width: 20%">No</th>
								<th style="width: 50%">제목</th>
								<th style="width: 30%">작성일</th>
								
							</tr>
						</thead>
						<tbody>	
							<tr style="height: 30px; line-height: 30px;">
								<td align="center">3</td>
								<td><a href="Board_View.jsp">게시판 글입니다 3</a></td>
								<td align="center">2021/11/23</td>
							</tr>
							<tr style="height: 30px; line-height: 30px;">
								<td align="center">2</td>
								<td><a href="Board_View.jsp">게시판 글입니다 2</a></td>
								<td align="center">2021/11/23</td>
							</tr>
							<tr style="height: 30px; line-height: 30px;">
								<td align="center">1</td>
								<td><a href="Board_View.jsp">게시판 글입니다 1</a></td>
								<td align="center">2021/11/23</td>
							</tr>
								
							
							
							<tr style="height: 30px; line-height: 30px;">
								<td rowspan="3" style="text-align: center">게시글이 없습니다.</td>

							</tr>
							
							
						</tbody>
					</table>
				</div>
				
					
				<div class="footer">

					
					<div class="paging_box">
						<h1>test</h1>
						
					</div>
					
					
				</div>
				
			</div>
		</div>
		
	</div>
  </section>

</body>
</html>