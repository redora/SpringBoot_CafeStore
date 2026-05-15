package com.cafe.cafe_store.controller;

import com.cafe.cafe_store.model.MenuResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MenuController {
    
    // /menu 주소로 GET 요청이 들어오면 메뉴 객체를 반환한다. 
	// Spring이 MenuResponseDto 객체를 자동으로 JSON 형식으로 변환하여 응답한다.
    @GetMapping("/menu")
    public MenuResponseDto getMenu() {
        return new MenuResponseDto("아메리카노", 4500);
    }
}
