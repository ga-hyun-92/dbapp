package com.korea.dbapp.domain.comment;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.korea.dbapp.domain.post.Post;
import com.korea.dbapp.domain.user.User;

import lombok.Data;

@Data	//getter,setter와 toString()을 만들어줌
@Entity
public class Comment {
	@Id																						
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;				//프라이머리키(기본키)-자동으로 데이터가 정리되어 들어가서 인덱스가 만들어짐
	private String text;
	
	@JsonIgnoreProperties({"posts"})	//무한참조방지
	@JoinColumn(name="user_id")	//fk키를 user_id로 해!!
	@ManyToOne
	private User user;
	
	@JsonIgnoreProperties({"user"})	//무한참조방지
	@JoinColumn(name="post_id")
	@ManyToOne
	private Post post;
	
}
