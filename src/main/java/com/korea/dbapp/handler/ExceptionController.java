package com.korea.dbapp.handler;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice	//이거 걸어두면 모든 exception발생하면 다 여기로 들어온다! 
									//그리고 무슨 함수를 때릴지 찾아간다!
@Controller //View 리턴
public class ExceptionController {

	@ExceptionHandler(Exception.class)	//Exception의 자식Exception이 발생하면 다 이 함수 때려준다
	public String test1(Exception e,Model model) {
//		return "에러발생: "+e.getMessage();
		model.addAttribute("msg", e.getMessage());
		return "error";
	}
}
