package com.cafe.cafe_store.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cafe.cafe_store.dto.MenuRequestDto;
import com.cafe.cafe_store.dto.MenuResponseDto;

@RestController
public class MenuController {

    @GetMapping("/menu")
    public MenuResponseDto getMenu() {
        return new MenuResponseDto("아메리카노", 4500);
    }

    @GetMapping("/menus/{id}")
    public MenuResponseDto getMenuById(@PathVariable Long id) {
        if (id == 1) {
            return new MenuResponseDto("아메리카노", 4500);
        } else if (id == 2) {
            return new MenuResponseDto("카페라떼", 5000);
        } else {
            return new MenuResponseDto("없는 메뉴", 0);
        }
    }

    @GetMapping("/search")
    public String searchMenu(@RequestParam String category, @RequestParam int maxPrice) {
        return "카테고리: " + category + ", 최대 가격: " + maxPrice + "원 이하 메뉴를 검색합니다.";
    }

    @PostMapping("/menus")
    public MenuResponseDto createMenu(@RequestBody MenuRequestDto request) {
        return new MenuResponseDto(request.getName(), request.getPrice());
    }
}
