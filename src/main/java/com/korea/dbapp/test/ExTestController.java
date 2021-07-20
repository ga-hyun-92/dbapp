package com.korea.dbapp.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ExTestController {
	@GetMapping("/ex/test1/{no}")
	public String exTest1(@PathVariable int no) throws Exception {
		if(no==1) {
			throw new Exception("가짜에러");
		} else {
			return "test/Sample";
		}
	}

}
