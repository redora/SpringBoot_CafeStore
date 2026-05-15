package com.cafe.cafe_store.service;

import com.cafe.cafe_store.model.MenuResponseDto;
import org.springframework.stereotype.Service;

// @Service 
// 이 클래스가 "비즈니스 로직을 처리하는 서비스 클래스"라는 뜻. 
// Spring은 이 클래스를 자동으로 관리할 수 있는 객체(Bean)로 등록합니다.
@Service 
public class MenuServiceImpl implements MenuServiceInf {

    // 기본 메뉴 1개를 반환하는 메서드입니다. 
    // Controller가 이 메서드를 호출해서 결과를 받아갑니다.
    public MenuResponseDto getDefaultMenu() {
        return new MenuResponseDto("아메리카노", 4500);
    }

    // 메뉴 ID에 따라 해당 메뉴 정보를 반환한다.
    public MenuResponseDto getMenuById(Long id) {
        if (id == 1L) {
            return new MenuResponseDto("아메리카노", 4500);
        } else if (id == 2L) {
            return new MenuResponseDto("카페라떼", 5000);
        } else if (id == 3L) {
            return new MenuResponseDto("바닐라라떼", 5500);
        } else {
            return new MenuResponseDto("없는 메뉴", 0);
        }
    }
}
