package com.korea.dbapp.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.korea.dbapp.domain.comment.Comment;
import com.korea.dbapp.domain.comment.CommentRepository;
import com.korea.dbapp.domain.post.Post;
import com.korea.dbapp.domain.post.PostRepository;
import com.korea.dbapp.domain.user.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // final붙어있는 애들을 전부 생성자 만들어준다!!
@Controller
public class PostController {
	// @GetMapping("/")
	// public String index() {
	// return "redirect:/post";
	// }

	private final PostRepository postRepository;
	private final HttpSession session;
	private final CommentRepository commentRepository;

	// DI이다!!!!!!!!! Dependency Injection
	// IoC에 DI하면? 어떤 장점이 있어요>???


	@GetMapping("/download")
	public @ResponseBody String download() {
		return "사과";
	}
	// page에 대한 test 함수!!!!!!
	// findAll(PageRequest.of(page, 3)) 이 함수를 사용하면? Page<T>로 return 해주는 걸 확인
	// 화면에 데이터 받으려면? @ResponseBody 해줘야함. 타입은? Page<Post>
	// 그리고 몇 페이지인지는?? 쿼리스트링으로 하려면? page(int page)
	@GetMapping("/test/page")
	public @ResponseBody Page<Post> page(int page) {

		return postRepository.findAll(PageRequest.of(page, 3));
	}

	@GetMapping({ "/", "/post" })
	public String list(Model model, Integer page) { // model=request
		if (page == null) {
			page = 0;
		}
		model.addAttribute("postsEntity", postRepository.findAll(PageRequest.of(page, 4)));
		return "post/list"; // ViewResolver도움!!!+RequestDispatcher(request유지기법)
	}

	// 상세보기
	@GetMapping("/post/{id}")
	public String detail(@PathVariable int id, Model model) {
		Post postEntity = postRepository.findById(id).get();
		model.addAttribute("postEntity", postEntity);

		List<Comment> commentsEntity = commentRepository.mFindAllByPostId(id);
		model.addAttribute("commentsEntity", commentsEntity);
		return "post/detail";
	}

	// 게시글 삭제하기
	@DeleteMapping("/post/{id}") // id는?? postEntity의 id이다!!!!!
	public @ResponseBody String deleteById(@PathVariable int id) { // @ResponseBody사용하면??? MessageConverter를 타고간다
		// 1.권한체크(post id를 통해 user id를 찾아서 id와 session의 id를 비교한다)
		// 예를 들어, 2번게시글을 쓴 user의 id를 찾아야한다.

		// 해당 게시글의 id값으로 DB에서 SELECT해오기(post의 user_id찾기)
		Post postEntity = postRepository.findById(id).get();
		// id=10일때, 해당 값이 없으니까 nullpointException이 일어난다
		// Post postEntity=postRepository.findById(10).get();
		// id=7일때, 로그인은 ssar(id=1)로 하고 ssar이 작성한 게시글에 들어가서 삭제하면?
		// id=7넣어놓아서! 7번인 post글의 userid와 비교하면 다르니까! "삭제실패"가 뜬다!
		int postUserId = postEntity.getUser().getId();

		// 인증된 사용자 : session에 저장된 User객체 들고오기
		User principal = (User) session.getAttribute("principal");
		int userId = principal.getId();

		if (postUserId == userId) {
			// 2.{id}값으로 삭제!!
			postRepository.deleteById(id);

			// redirect안하면??? 기존에 만들어놓은게 삭제된다!!!
			// 예를들어, post/list로 가라고 해놓으면? postsEntity값이 없어서 글목록 못뿌림
			// return "redirect:/";
			return "ok"; // 이 요청을 브라우저가 받아서 해석한다! alert띄우고 href 페이지 이동
		} else {

			return "fail";
		}
		// return "redirect:/auth/loginForm";
	}// end of deleteById

	@GetMapping("/post/saveForm")
	public String saveForm() {
		// 1. 인증 체크-세션이 있는사람만!(숙제)-잘못된 접근이면! Script.back으로 하는게..
		return "post/saveForm"; // 파일을 호출
	}

	// 글쓰기 save!!!!!!!!
	// title,content, principal을 user객체에 넣고.. post의 id는 principal에서???
	@PostMapping("/post")
	public String save(Post post) {
		User principal = (User) session.getAttribute("principal");
		// 인증안된 사용자는 쫓아내면 된다!
		if (principal == null) {
			return "redirect:/auth/loginForm";// 주소를 호출, 인증안된 사용자 접근에 대해선 친절히 alert안해줘도 된다
		}
		// 현재 principal에 password도 없고, User의 변수인 List<Post>도 없다.그런데 상관없다
		// DB구조상, DB에 Post가 저장될때에는 user_id만 필요하다!!!!!!!
		post.setUser(principal);
		postRepository.save(post);
		return "redirect:/";
	}

	@GetMapping("/post/{id}/updateForm") // MVC패턴으로 where절이 필요하니까!! {id}를 사용
	public String updateForm(@PathVariable int id, Model model) {

		// 인증,권한 부여해야한다!!!!!(각각 하지 말고 한번에 가능)
		User principal = (User) session.getAttribute("principal");
		int loginId = principal.getId();

		Post postEntity = postRepository.findById(id).get();// post안에 User담겨져 있다! 왜?? 기본 전략이 Eager이니까!!!
		int postOwnerId = postEntity.getUser().getId();

		if (loginId == postOwnerId) {
			model.addAttribute("postEntity", postEntity);
			return "post/updateForm";
		}
		// 정상적으로는 절대 들어올수 없는 페이지이니까, 친절히 alert를 띄울 필요 없이 로그인페이지로 보냄
		return "redirect:/auth/loginForm";
	}

	// 게시글 업데이트 함수
	@PutMapping("/post/{id}")
	public @ResponseBody String update(@PathVariable int id, @RequestBody Post post) {
		// 0.인증 및 권한!
		User principal = (User) session.getAttribute("principal");
		int loginId = principal.getId();

		// 1.{id}로 해당 게시글에 대한 id가 있으니, id로 기존 DB에 저장된 post정보 select해오기
		Post postEntity = postRepository.findById(id).get();
		int postOwnerId = postEntity.getUser().getId();

		if (loginId == postOwnerId) {
			// 2.수정된 데이터 받아온 걸로 다시 저장하기!
			postEntity.setTitle(post.getTitle());
			postEntity.setContent(post.getContent());

			// 3.수정된 게시글post을 다시 DB에 저장하기
			postRepository.save(postEntity);
			return "ok";
		} else {
			return "redirect:/auth/login";
		}

	}
}
