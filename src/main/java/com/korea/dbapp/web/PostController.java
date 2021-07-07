package com.korea.dbapp.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.korea.dbapp.domain.post.PostRepository;

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
	public String list(Model model) {
		
		model.addAttribute("postsEntity",postRepository.findAll());
		return "post/list";
	}
}
