<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>
<div class="container">
	<!-- 수정,삭제  인증만 확인할때!! -->
	<%-- <c:if test="${!empty sessionScope.principal}"> --%>
	<!-- 권한 부여!! 해당 글을 작성한 user의 id와 세션(인증주체)에 있는 user의 id랑 같으면! -->
	<c:if test="${sessionScope.principal.id==postEntity.user.id}">
		<a href="#" class="btn btn-warning">수정</a>
		<form action="/post/${postEntity.id}" method="post" style="display: inline-block">
			<button id="btn-delete" class="btn btn-danger" type="submit">삭제</button>
		</form>
	</c:if>
	<br />
	<br />
	<div>
		<span>글 번호 : ${postEntity.id}</span> 작성자 : <span><i>${postEntity.user.username} </i></span>
	</div>
	<br />
	<div>
		<h3>${postEntity.title}</h3>
	</div>
	<hr />
	<div>
		<div>${postEntity.content}</div>
	</div>
	<hr />

	<div class="card">
		<form>
			<div class="card-body">
				<textarea id="reply-content" class="form-control" rows="1"></textarea>
			</div>
			<div class="card-footer">
				<button type="button" id="btn-reply-save" class="btn btn-primary">등록</button>
			</div>
		</form>
	</div>
	<br />

	<div class="card">
		<div class="card-header">
			<b>댓글 리스트</b>
		</div>
		<ul id="reply-box" class="list-group">
			<li id="reply-1" class="list-group-item d-flex justify-content-between">
				<div>댓글입니다</div>
				<div class="d-flex">
					<div class="font-italic">작성자 : 홍길동 &nbsp;</div>
					<button class="badge">삭제</button>
				</div>
			</li>
		</ul>
	</div>
	<br />
</div>

<%@ include file="../layout/footer.jsp"%>