package com.korea.dbapp.web.dto;

import lombok.Data;


//1. web패키지에 dto패키지를 만들고 CommentSaveReqDto클래스로 오브젝트 만든다
@Data
public class CommentSaveReqDto {
	private String text;
	private int postId;
}
