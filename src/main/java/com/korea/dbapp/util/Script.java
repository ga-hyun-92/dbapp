package com.korea.dbapp.util;

public class Script {

	public static String  back(String msg) {
		StringBuilder sb=new StringBuilder();
		sb.append("<script>");
		sb.append("alert('"+msg+"');");
		sb.append("history.back()");						//자바스크립트가 들고있는 함수 history.back()
		sb.append("</script>");
		
		//Object가  들고 있는 toString함수 사용! 객체의 주소를 return해준다.
		//String이 toString을 오버라이딩해서 this로 return 해준다. 즉, String으로 return
		return sb.toString();
	}
	
	public static String href(String uri) {
		StringBuilder sb=new StringBuilder();
		sb.append("<script>");
		sb.append("location.href='"+uri+"';");						
		sb.append("</script>");
		
		return sb.toString();
	}
}
