package com.korea.dbapp.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.korea.dbapp.domain.user.User;
import com.korea.dbapp.domain.user.UserRepository;
import com.korea.dbapp.util.Script;

@Controller
public class UserController {

	private final UserRepository userRepository;
	private final HttpSession session;
	
	//의존성 주입(DI)
	public UserController(UserRepository userRepository, HttpSession session) {
		this.userRepository = userRepository;
		this.session = session;
	}
	
	
	
	//회원가입 페이지로 가는 함수!
	//데이터 돌려주는게 아니라, 파일 응답해주는 !
	//클라이언트는 버튼 클릭해서 페이지이동할거다
	@GetMapping("/auth/joinForm") // "/auth"로 들어오면 필터에서 들여보내주게 설계!
	public String joinForm(User user) {
	
		return "auth/joinForm";
	}
	

	@GetMapping("/auth/loginForm") // "/auth"로 들어오면 필터에서 들여보내주게 설계!
	public String loginForm() {
		return "auth/loginForm";
	}
	
	
	@PostMapping("/auth/join")  //auth 관련은 굳이 규칙 안지키고 주소 넣어도 됨
	public String join(User user) {
		userRepository.save(user);
		return "redirect:/auth/loginForm"; //위에 만들어놓은 함수로 선언된 로그인페이지로 redirect
	}
	
	//로그인 실패했을 때, alert뜨게 해보자!!! 그래서 데이터 리턴 코드로 하기 위해
	//RestController 사용한다.
	@PostMapping("/auth/login")
	public @ResponseBody String login(User user) {	//@ResponseBody 넣으면? RestController 로 된다!
		User userEntity=userRepository.mLogin(user.getUsername(),user.getPassword());
		
		if(userEntity==null) {
			return Script.back("로그인 fail");
			
		} else {
			//key 값: principal 인증주체 =>setAttribute(키, Object);
			//session으로 로그인한 사용자인지 확인하려고!
			session.setAttribute("principal", userEntity);
			
			return Script.href("/");
		}
	}
	
	//로그아웃 함수
	@GetMapping("/user/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/";
	}
	
	//업데이트 페이지
	@GetMapping("/user/updateForm")
	public String updateForm() {
		return "updateForm";
	}
	
	//업데이트 함수
	@PutMapping("/user/update")
	public String update(User user) {
		//  1. 세션 아이디값으로 SELECT  하기
		User principal=(User)session.getAttribute("principal");
		User userEntity=userRepository.findById(principal.getId()).get();
		
		// 2. 전달받은 값으로 변경하고
		userEntity.setUsername(user.getUsername());
		userEntity.setPassword(user.getPassword());
		userEntity.setAddress(user.getAddress());
		userEntity.setEmail(user.getEmail());
		
		// 3. SAVE
		userRepository.save(userEntity);						//새로 저장된 데이터로 다시 저장! update
		
		// 4. 세션변경
		session.setAttribute("principal", userEntity);	//update하고  session값을 바꿔야한다.
		
		// 5.정보변경 완료 후 메인 페이지 이동
		return "redirect:/";
	}
	
	@GetMapping("/juso")
	public String jusoRequest() {
		return "juso/jusoPopup";
	}
	
	@PostMapping("/juso")
	public String jusoResponse(String roadFullAddr,String inputYn, Model model) {
		System.out.println("주소:"+roadFullAddr);
		model.addAttribute("roadFullAddr",roadFullAddr);
		model.addAttribute("inputYn",inputYn);
		return "juso/jusoPopup";
	}
	
}




