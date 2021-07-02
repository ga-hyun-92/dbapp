<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="layout/header.jsp"%>

<div class="container">

	<form action="/user/update" method="post">
		<div class="form-group">
			<label for="username">Username:</label> 
			<input type="text" class="form-control" placeholder="Enter username" name="username">
		</div>
		<div class="form-group">
			<label for="password">Password:</label> 
			<input type="password" class="form-control" placeholder="Enter password" name="password">
		</div>
		<div class="form-group">
			<label for="email">Email:</label> 
			<input type="email" class="form-control" placeholder="Enter email" name="email">
		</div>
		<div class="form-group">
			<label for="address">Address:</label> 
			<input type="text" class="form-control" placeholder="Enter address" name="address">
		</div>
		
		<button type="submit" class="btn btn-primary">회원정보수정</button>
	</form>
</div>
<br/>

<%@include file="layout/footer.jsp"%>
