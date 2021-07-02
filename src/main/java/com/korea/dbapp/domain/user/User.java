package com.korea.dbapp.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
	//primary key표시
	@Id																						
	//auto increament설정 -IDENTITY는?정체성을 따라가라
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	private int id;							//프라이머리키(기본키)
	
	@Column(unique=true, length =20)			//username에 unique걸어주면 ? 똑같은 아이디로 회원가입 안됨
																				//길이를 20자로 제한
	private String username;								//id로 사용할 예정
	private String password;
	private String email;
	private String address;
	
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
