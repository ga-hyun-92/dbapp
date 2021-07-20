<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../layout/header.jsp"%>

<div class="container">
	<form action="#" onsubmit="updatePost()">
		<div class="form-group">
			<label for="title">Title:</label> 
			<input id="title" value="${postEntity.title}" type="text" class="form-control" placeholder="Enter title" name="title" required="required"/>
		</div>
		<div class="form-group">
		<textarea id="content" rows="10" class="form-control" name="content">
			${postEntity.content}
		</textarea>
		</div>
		
		<button type="submit" class="btn btn-primary">수정 완료</button>
	</form>
</div>

<script>
    async function updatePost(){
    	
		console.log(event);
		//submit 동작을 막는다=>즉, 새로고침도 안하고 return true,false도 안함
		event.preventDefault();
		
		//document객체는 화면에 있는  element에 접근
		let title=document.querySelector("#title").value;
		let content=document.querySelector("#content").value;
		
		console.log(title);
		console.log(content);
		
		//변수
		let updateDto = {
				title :title,
				content : content
		};
		//fetch를 await를 하면??? 감싸고 있는 함수를 비동기 함수로async!
		let response=await fetch("/post/${postEntity.id}",{//뒷 주소만 적어도 된다. 백틱 `$` 표현을 쓰면 EL표현식과 겹친다
			method : "put",
			body: JSON.stringify(updateDto),
			headers: {
				"Content-Type" : "application/json; charset=utf-8"
			}
		});	
		let parseResponse=await response.text(); //json(), text() 리턴! 
		console.log(parseResponse);
		
		if(parseResponse==="ok"){
			location.href ="/post/${postEntity.id}"
		}
	}

      $('#content').summernote({
        placeholder: 'Hello Bootstrap 4',
        tabsize: 2,
        height: 400
      });
    </script>
<%@include file="../layout/footer.jsp"%>