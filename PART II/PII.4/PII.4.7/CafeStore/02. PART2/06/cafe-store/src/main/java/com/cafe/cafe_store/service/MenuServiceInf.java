package com.cafe.cafe_store.service;

import com.cafe.cafe_store.dto.MenuResponseDto;

public interface MenuServiceInf {
    MenuResponseDto getDefaultMenu();

    MenuResponseDto getMenuById(Long id);

}
