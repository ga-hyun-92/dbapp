package com.korea.dbapp.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

//목적은? Data를 가져와서 자바 오브젝트로 만드는 것!
//왜 인터페이스로 만들었나? 규칙이다.
//user테이블에 접근해서 user오브젝트를 만드는 애다! ID는 primary키의 타입!
//JpaRepository는 데이터 access오브젝트다!! db에 접근해서 데이터 가져온다.
@Repository //이렇게 안붙어도 얘는 예외라서 메모리에 띄워진다!!
public interface UserRepository extends JpaRepository<User, Integer>{

	//:username 여기에  내가 입력한 ssar이 들어가서 쿼리가 날려지고 해당 객체가 반환된다.
	@Query(value="SELECT *FROM user WHERE username= :username",nativeQuery = true)
	User mFindByUsername(String username);
	
	@Query(value="SELECT id,username,address,email,null password FROM user WHERE username= :username AND password= :password",nativeQuery = true)
	User mLogin(String username,String password);
}
