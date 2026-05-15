package com.cafe.cafe_store.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// @Controller
// 이 클래스가 웹 요청을 처리하는 Controller임을 나타낸다.
// 단, 반환값을 데이터가 아니라 "화면 이름(View Name)"으로 해석한다.
@Controller
public class ViewController {

    // /hello-page 주소로 GET 요청이 들어오면 실행된다.
    @GetMapping("/hello-page")
    public String helloPage() {

        System.out.println("helloPage() method called"); // 호출여부 확인을 위한 로그
            // "hello"를 반환하면 templates 폴더에서 hello.html 파일을 찾는다.
        return "hello";
    }
}
