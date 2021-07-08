<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@include file="../layout/header.jsp"%>

<div class="container">
	<!-- var이 들어가는건 pageScope(해당 페이지에만 쓸 수 있는 것!)에 들어간다 -->
	<!-- for문이 돌때마다 post라는 변수에 들어간다. 즉, 메모리 영역 중에 pageScope에 들어감 -->
	<c:forEach var="post" items="${postsEntity}"> <!--postsEntity를 request.getAttribute로 해당 값을찾음!! -->
		<div class="card">
			<div class="card-body">
				<h4 class="card-title">${post.title}</h4> <!-- 원래는 이렇게 표현해야함${pageScope.post.title} -->
				<a href="/post/${post.id}" class="btn btn-primary">상세보기</a>
			</div>
		</div><!-- End Of Card -->
		<br/>
	</c:forEach>
</div><!-- End Of Container -->
<%@include file="../layout/footer.jsp"%>