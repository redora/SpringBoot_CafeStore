package com.cafe.cafe_store.repository;


import com.cafe.cafe_store.model.Menu;

import java.util.List;
import java.util.Optional;

// 메뉴 저장소가 반드시 제공해야 하는 기능을 정의하는 인터페이스이다. 
// 이 인터페이스를 구현하는 클래스는 반드시 아래 메서드를 모두 구현해야 한다.
public interface MenuRepositoryInf {

    // 저장된 전체 메뉴 목록을 조회한다.
    List<Menu> findAll();

    // id로 특정 메뉴를 조회한다.
    // 결과가 없을 수도 있으므로 Optional로 감싸서 반환한다.
    Optional<Menu> findById(Long id);

    // 메뉴를 저장한다.
    // 새 메뉴 등록과 기존 메뉴 수정에 모두 사용할 수 있다.
    Menu save(Menu menu);

    // id로 특정 메뉴를 삭제한다.
    void deleteById(Long id);
}