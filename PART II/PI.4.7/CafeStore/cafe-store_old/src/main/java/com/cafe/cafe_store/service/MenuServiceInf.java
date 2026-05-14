package com.cafe.cafe_store.service;

import com.cafe.cafe_store.dto.MenuRequestDto;
import com.cafe.cafe_store.dto.MenuResponseDto;

import java.util.List;

/**
 * 메뉴 관련 비즈니스 로직의 "계약서"입니다.
 * Controller는 항상 이 인터페이스를 통해서만 Service를 사용합니다.
 */
public interface MenuServiceInf {

    /** 전체 메뉴 목록을 반환합니다. */
    List<MenuResponseDto> getAllMenus();

    /**
     * 특정 ID의 메뉴를 반환합니다.
     * 존재하지 않는 ID면 예외를 발생시킵니다.
     */
    MenuResponseDto getMenuById(Long id);

    /**
     * 새 메뉴를 등록합니다.
     * @param requestDto 클라이언트가 보낸 메뉴 정보
     * @return 저장된 메뉴 정보 (id 포함)
     */
    MenuResponseDto createMenu(MenuRequestDto requestDto);

}
