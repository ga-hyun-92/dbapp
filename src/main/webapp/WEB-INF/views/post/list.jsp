<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@include file="../layout/header.jsp"%>

<div class="container">
	<!-- var이 들어가는건 pageScope에 들어간다 -->
	<!-- for문이 돌때마다 post라는 변수에 들어간다. 즉, 메모리 영역 중에 pageScope에 들어감 -->
	<c:forEach var="post" items="${postsEntity}">
		<div class="card">
			<div class="card-body">
				<h4 class="card-title">${post.title}</h4>
				<a href="/post/${post.id}" class="btn btn-primary">상세보기</a>
			</div>
		</div>
		<!-- End Of Card -->
		<br/>
	</c:forEach>

</div>
<!-- End Of Container -->


<%@include file="../layout/footer.jsp"%>