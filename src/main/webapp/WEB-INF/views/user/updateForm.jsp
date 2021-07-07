<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@include file="../layout/header.jsp"%>

<div class="container">
	<!-- username=머시기 &password=머시기&email=머시기&address=머시기 -->
	<!-- 컨트롤러에 만들어놓은 http://localhost:8000/auth/join 로 POST 전달-->
	<form action="/user/${principal.id}" method="post">
		<div class="form-group">
			<label for="username">Username:</label> 
			<input value="${principal.username}" type="text" class="form-control" placeholder="Enter username" readonly="readonly"/>
		</div>
		<div class="form-group">
			<label for="password">Password:</label> 
			<input value="${principal.password}" type="password" class="form-control" placeholder="Enter password" name="password" required="required"/>
		</div>
		<div class="form-group">
			<label for="email">Email:</label> 
			<input value="${principal.email}" type="email" class="form-control" placeholder="Enter email" readonly="readonly"/>
		</div>

		<input class="btn btn-info" type="button" onClick="goPopup();" value="주소찾기" />
		<div class="form-group">
			<label for="address">Address:</label> 
			<input value="${principal.address}" type="text" class="form-control" placeholder="Enter address" name="address" id="address" readonly="readonly"/>
		</div>

		<button type="submit" class="btn btn-primary">회원정보변경</button>
	</form>
</div>
<br />
<!-- javaScript는 제일 하단에 코드놓기! -->
<script>
	function goPopup() {
		var pop = window.open("/juso", "pop",
				"width=570,height=420, scrollbars=yes, resizable=yes");
	}

	function jusoCallBack(roadFullAddr) {
		//alert("나 실행됨?  jusoCallBack");
		let addressEL=document.querySelector("#address");
		console.log(addressEL);
		addressEL.value = roadFullAddr;
	}
</script>
<%@include file="../layout/footer.jsp"%>

