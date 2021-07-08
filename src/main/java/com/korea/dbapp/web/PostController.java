package com.korea.dbapp.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.korea.dbapp.domain.post.Post;
import com.korea.dbapp.domain.post.PostRepository;
import com.korea.dbapp.domain.user.User;
import com.korea.dbapp.util.Script;

@Controller
public class PostController {
//	@GetMapping("/")
//	public String index() {
//		return "redirect:/post";
//	}
	
	private final PostRepository postRepository;
	private final HttpSession session;

	//DI이다!!!!!!!!! Dependency Injection
	//IoC에 DI하면? 어떤 장점이 있어요>???
	public PostController(PostRepository postRepository, HttpSession session) {
		this.postRepository = postRepository;
		this.session = session;
	}

	@GetMapping({"/","/post"})
	public String list(Model model) {	//model=request
		
		model.addAttribute("postsEntity",postRepository.findAll());
		return "post/list";	//ViewResolver도움!!!+RequestDispatcher(request유지기법)
	}
	
	//상세보기
	@GetMapping("/post/{id}")
	public String detail(@PathVariable int id, Model model) {
		Post postEntity=postRepository.findById(id).get();
		model.addAttribute("postEntity",postEntity);
		return "post/detail";
	}
	
	//게시글 삭제하기
	@PostMapping("/post/{id}")	//id는?? postEntity의 id이다!!!!! 
	public @ResponseBody String deleteById(@PathVariable int id) {	//@ResponseBody사용하면??? MessageConverter를 타고간다
		//1.권한체크(post id를 통해 user id를 찾아서 id와 session의 id를 비교한다)
		//예를 들어, 2번게시글을 쓴 user의 id를 찾아야한다. 
		
		//해당 게시글의 id값으로 DB에서 SELECT해오기(post의 user_id찾기)
		Post postEntity=postRepository.findById(id).get();	
		//id=10일때, 해당 값이 없으니까 nullpointException이 일어난다
		//Post postEntity=postRepository.findById(10).get();
		//id=7일때, 로그인은 ssar(id=1)로 하고 ssar이 작성한 게시글에 들어가서 삭제하면?
		//id=7넣어놓아서! 7번인 post글의 userid와 비교하면 다르니까! "삭제실패"가 뜬다!
		int postUserId=postEntity.getUser().getId();
		
		//인증된 사용자 : session에 저장된 User객체 들고오기
		User principal=(User)session.getAttribute("principal");
		int userId=principal.getId();
		
		if(postUserId==userId) {
			//2.{id}값으로 삭제!!
			postRepository.deleteById(id);
			
			//redirect안하면??? 기존에 만들어놓은게 삭제된다!!! 
			//예를들어, post/list로 가라고 해놓으면? postsEntity값이 없어서 글목록 못뿌림
			//return "redirect:/";	
			return Script.href("/","삭제 완료");		//이 요청을 브라우저가 받아서 해석한다! alert띄우고 href 페이지 이동
		} else {
			
			return Script.back("삭제실패");
		}
		
		//return "redirect:/auth/loginForm";
		
	}//end of deleteById
	
	@GetMapping("/post/saveForm")
	public String saveForm() {
		//1. 인증 체크-세션이 있는사람만!(숙제)-잘못된 접근이면! Script.back으로 하는게.. 
		return "post/saveForm";
	}
	
	//글쓰기 save!!!!!!!!
	//title,content, principal을 user객체에 넣고.. post의 id는 principal에서??? 
}
