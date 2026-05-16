package com.cafe.cafe_store.service;

import java.util.List;

import com.cafe.cafe_store.dto.MenuRequestDto;
import com.cafe.cafe_store.dto.MenuResponseDto;

/**
 * 메뉴 서비스의 기능 목록을 정의하는 인터페이스입니다.
 *
 * Controller는 이 인터페이스만 참조합니다.
 * 실제 구현 클래스(MenuServiceImpl)가 어떻게 동작하는지 알 필요가 없으므로,
 * 나중에 구현 방식이 변경되더라도 Controller 코드는 수정할 필요가 없습니다.
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

    /*
     * 메뉴 재고를 수정합니다.
     */
    MenuResponseDto updateStock(Long id, int stock);

    /*
     * 메뉴 마감 처리를 수행합니다.
     */
    MenuResponseDto closeMenu(Long id);

    /*
     * 메뉴 마감 해제를 수행합니다.
     */
    MenuResponseDto openMenu(Long id);
}
