package com.korea.dbapp.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data 
public class CMRespDto<T> { //bean이라고  한다.  자료형만 선언된!!!
	//통신할때 사용하는 데이터 Dto!!!!!!!!!!!!!!!!!
	//데이터 베이스 테이블과 같으면?? model이라고함=> repository에서 만들어진다
	//서버쪽으로 들어오는 데이터 requestDto
	//서버가 응답하는 데이터 responseDto를 제네릭으로 만든다!! 그래서 하나만 만들어둔다
	private int code;
	private String msg;
	private T data;
}
