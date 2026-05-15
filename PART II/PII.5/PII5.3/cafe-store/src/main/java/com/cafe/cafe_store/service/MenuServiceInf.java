package com.cafe.cafe_store.service;

import com.cafe.cafe_store.model.MenuResponseDto;

public interface MenuServiceInf {
    // 기본 메뉴를 조회한다.
    MenuResponseDto getDefaultMenu();

    // 메뉴 ID로 메뉴를 조회한다.
    MenuResponseDto getMenuById(Long id);
}
