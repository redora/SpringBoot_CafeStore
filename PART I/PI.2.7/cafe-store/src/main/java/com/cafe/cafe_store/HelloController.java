package com.cafe.cafe_store;

// 웹 요청을 처리할 때 사용하는 어노테이션들이다.
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// 이 클래스가 HTTP 요청을 처리하는 컨트롤러라는 뜻이다. 
// @Controller와 비슷하지만, 여기서는 문자열 자체를 바로 응답 본문으로 보낸다.
@RestController
public class HelloController {
    // 브라우저에서 http://localhost:8080/ 로 접속했을 때 실행된다.
    @GetMapping("/")
    public String hello() {
        return "Welcome to Cafe Store!";// 화면에 단순 문자열을 그대로 반환한다.
    }
    // 브라우저에서 http://localhost:8080/health 로 접속했을 때 실행된다.
    @GetMapping("/health")
    public String health() {
        return "Application is running!";// 화면에 단순 문자열을 그대로 반환한다.
    }
}