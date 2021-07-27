package com.korea.dbapp.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.korea.dbapp.domain.comment.Comment;
import com.korea.dbapp.domain.comment.CommentRepository;
import com.korea.dbapp.domain.post.Post;
import com.korea.dbapp.domain.post.PostRepository;
import com.korea.dbapp.domain.user.User;
import com.korea.dbapp.web.dto.CMRespDto;
import com.korea.dbapp.web.dto.CommentSaveReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class CommentController {

	private final CommentRepository commentRepository;
	private final HttpSession session; // 나중에 aop로!!! 분리해야한다!!!
	private final PostRepository postRepository;

	// 1.save 댓글쓰기 -Post-Data Return
	@PostMapping("/comment")
	public @ResponseBody CMRespDto<Comment> save(@RequestBody CommentSaveReqDto dto) {

		Comment comment = new Comment();
		comment.setText(dto.getText());

		Post postEntity = postRepository.findById(dto.getPostId()).get();
		postEntity.setId(dto.getPostId());
		comment.setPost(postEntity);

		User principal = (User) session.getAttribute("principal");
		comment.setUser(principal);

		// 인증된 사용자만 댓글쓸수 있게!
		if (principal == null) {
			return new CMRespDto<>(-1, "댓글쓰기 실패", null);
		}

		Comment commentEntity = commentRepository.save(comment);
		return new CMRespDto<>(1, "댓글쓰기 성공", commentEntity);
	}

	// 2.delete댓글 삭제 -Delete-Data Return
	// :fetch(비동기함수) 즉, ajax로 부분만 변경하자!!! <->form태그로 했으면,페이지 전체 다시 그리게 됨(reload)
	@DeleteMapping("/comment/{id}")
	public @ResponseBody String deleteById(@PathVariable int id) {
		User principal = (User) session.getAttribute("principal");
		int userId = principal.getId();

		Comment commentEntity = commentRepository.findById(id).get();
		int commentUserId = commentEntity.getUser().getId();

		if (userId == commentUserId) {
			commentRepository.deleteById(id);
			return "ok";
		} else {
			return "fail"; // 권한이 없어서!!!
		}
	}

	// 3.findAllByPostId 해당 게시글에 대한 댓글이 뿌려지게!!! -Get ->이 페이지 말고 !! PostController의
	// 상세보기 페이지에서!!!

}
