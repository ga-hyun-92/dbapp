package com.korea.dbapp.test;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.korea.dbapp.domain.comment.Comment;
import com.korea.dbapp.domain.post.Post;
import com.korea.dbapp.domain.post.PostRepository;

@RestController
public class PostApiControllerTest {
	
	private final PostRepository postRepository;

	//생성자 만들면? DI된다.
	public PostApiControllerTest(PostRepository postRepository) {
		super();
		this.postRepository = postRepository;
	}
	
	@GetMapping("/test/post")
	public List<Post> findAll(){
		// SELECT *FROM post 라는 쿼리문!!
		return postRepository.findAll();
		
	}
	
	@GetMapping("/test/post/{id}")
	public String findById(@PathVariable int id) {
		Post postEntity=postRepository.findById(id).get();
		System.out.println("1");
		postEntity.getUser().getUsername();
		System.out.println("2");
		return "ok";
	}
	
}
