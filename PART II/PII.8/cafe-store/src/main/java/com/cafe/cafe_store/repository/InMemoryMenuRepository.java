package com.cafe.cafe_store.repository;

import com.cafe.cafe_store.entity.MenuEntity;

import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * MenuRepositoryInf 인터페이스의 메모리 기반 구현체입니다.
 * 데이터베이스 없이 자바의 HashMap을 이용하여 데이터를 저장합니다.
 *
 * @Repository: "나는 저장소입니다"라고 Spring에게 알리는 어노테이션
 *              Spring이 이 클래스를 자동으로 Bean으로 등록하여 관리합니다.
 *
 * [주의] 현재 구현은 단일 스레드 환경을 가정합니다.
 *        동시에 여러 요청이 들어오는 멀티스레드 환경에서는
 *        HashMap 대신 ConcurrentHashMap을 사용해야 합니다. .
 */
@Repository // 스프링이 저장소 역할의 빈으로 등록한다.
public class InMemoryMenuRepository implements MenuRepositoryInf {

    /**
     * 실제 데이터 저장소 역할을 하는 Map입니다.
     * Key: 메뉴 ID (Long)
     * Value: 메뉴 객체 (Menu)
     *
     * 마치 서랍장처럼: 서랍 번호(Key)로 내용물(Menu)을 꺼낼 수 있습니다.
     *
     * final로 선언하는 이유:
     * Map 자체(store 변수)가 다른 Map으로 교체되는 것을 방지합니다.
     * Map 안의 내용물(메뉴 데이터)은 자유롭게 추가·수정·삭제할 수 있습니다.
     */
    private final Map<Long, MenuEntity> store = new LinkedHashMap<>();

    /** 새 메뉴 ID를 자동으로 증가시키는 시퀀스 번호 */
    private Long sequence = 0L;

    /**
     * 생성자: 서버 시작 시 샘플 데이터를 미리 넣어둡니다.
     * DB로 바꾸기 전까지 테스트용으로 사용합니다.
     */
    public InMemoryMenuRepository() {

        // 커피 메뉴들
        save(new MenuEntity( null, "아메리카노", 4500, "coffee", 20, false));
        save(new MenuEntity( null, "카페라떼",   5000, "coffee", 15, false));
        save(new MenuEntity( null, "바닐라라떼", 5500, "coffee", 10, false));
        save(new MenuEntity( null,"아이스 아메리카노", 4500, "coffee", 25, false));
        // 디저트 메뉴들
        save(new MenuEntity( null, "치즈케이크", 6500, "dessert", 8, false));
        save(new MenuEntity( null, "쇼콜릿 케이크", 7000, "dessert", 5, false));
        save(new MenuEntity( null, "마카롱",     3500, "dessert", 12, false));
    }
	
	// 저장된 모든 메뉴를 리스트로 반환한다.
    @Override
    public List<MenuEntity> findAll() {
        // Map의 모든 값(Menu 객체)을 List로 변환해서 반환합니다.
        return new ArrayList<>(store.values());
    }

	// id에 해당하는 메뉴를 Optional로 감싸서 반환한다. 
    @Override
    public Optional<MenuEntity> findById(Long id) {
        // 해당 id가 없으면 null이 나올 수 있으므로
        // Optional.ofNullable()로 감싸서 안전하게 반환한다.
        return Optional.ofNullable(store.get(id));
    }

	// 메뉴를 저장한다.
    @Override
    public MenuEntity save(MenuEntity menu) {
        // id가 없으면 새 메뉴로 판단한다.
        if (menu.getId() == null) {
            // sequence 값을 1 증가시켜 id로 부여한다.
            menu.setId(++sequence);
        }

        // 이미 같은 id가 있으면 수정,
        // 없으면 새로 저장하는 효과가 난다.
        store.put(menu.getId(), menu);

        return menu;
    }
	
	// 해당 id의 메뉴를 저장소에서 삭제한다.
    @Override
    public void deleteById(Long id) {
        // 해당 id의 메뉴를 삭제한다.
        store.remove(id);
    }
}