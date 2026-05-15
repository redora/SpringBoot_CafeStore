package com.cafe.cafe_store.service;

import com.cafe.cafe_store.dto.MenuResponseDto;

public class MenuServiceImp implements MenuServiceInf {

    @Override
    public MenuResponseDto getDefaultMenu() {
        return new MenuResponseDto("아메리카노", 4000);
    }

    @Override
    public MenuResponseDto getMenuById(Long id) {
        if (id == 1) {
            return new MenuResponseDto("아메리카노", 4500);
        } else if (id == 2) {
            return new MenuResponseDto("카페라떼", 5000);
        } else if (id == 3) {
            return new MenuResponseDto("바닐라라떼", 5500);
        } else {
            return new MenuResponseDto("없는 메뉴", 0);
        }
    }
}
