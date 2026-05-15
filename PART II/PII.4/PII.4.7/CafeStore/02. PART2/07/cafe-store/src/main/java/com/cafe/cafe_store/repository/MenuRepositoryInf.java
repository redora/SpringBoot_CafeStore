package com.cafe.cafe_store.repository;

import com.cafe.cafe_store.model.Menu;

import java.util.List;
import java.util.Optional;

/**
 * 메뉴 저장소의 "계약서"입니다.
 * 어떤 저장소든 이 인터페이스를 구현하면 Service는 동일한 방식으로 사용할 수 있습니다.
 *
 * 현재: InMemoryMenuRepository (메모리 저장)
 * 나중: JpaMenuRepository (Oracle DB 저장) ← 9장에서 교체 예정
 */
public interface MenuRepositoryInf {

    /** 모든 메뉴를 조회합니다. */
    List<Menu> findAll();

    /**
     * ID로 특정 메뉴를 조회합니다.
     *
     * Optional을 쓰는 이유: 해당 ID의 메뉴가 없을 수도 있기 때문입니다.
     * null을 직접 반환하면 NullPointerException 위험이 있어 Optional로 감쌉니다.
     */
    Optional<Menu> findById(Long id);

    /**
     * 메뉴를 저장합니다. (신규 등록 & 수정 모두 사용)
     * - id가 없으면: 새로 생성
     * - id가 있으면: 해당 메뉴 덮어쓰기 (수정)
     */
    Menu save(Menu menu);

    /** ID로 특정 메뉴를 삭제합니다. */
    void deleteById(Long id);
}
