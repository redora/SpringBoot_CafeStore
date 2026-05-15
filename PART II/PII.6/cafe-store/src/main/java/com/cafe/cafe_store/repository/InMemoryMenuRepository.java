package com.cafe.cafe_store.repository;

import org.springframework.stereotype.Repository;

import com.cafe.cafe_store.model.Menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository // 스프링이 저장소 역할의 빈으로 등록한다.
public class InMemoryMenuRepository implements MenuRepositoryInf {

    // 실제 데이터를 저장하는 메모리 공간
    // key: 메뉴 id, value: Menu 객체
    private final Map<Long, Menu> store = new HashMap<>();

    // 새 메뉴 id를 1씩 증가시키기 위한 변수
    private Long sequence = 0L;

    // 생성자에서 기본 메뉴 데이터를 미리 저장해 둔다. 
    // 서버가 시작되면 아래 4개의 메뉴가 자동으로 메모리에 등록된다.
    public InMemoryMenuRepository() {
        save(new Menu(null, "아메리카노", 4500, "coffee"));
        save(new Menu(null, "카페라떼", 5000, "coffee"));
        save(new Menu(null, "바닐라라떼", 5500, "coffee"));
        save(new Menu(null, "치즈케이크", 6500, "dessert"));
    }
	
	// 저장된 모든 메뉴를 리스트로 반환한다.
    @Override
    public List<Menu> findAll() {
        // Map에 저장된 모든 값을 List 형태로 반환한다.
        return new ArrayList<>(store.values());
    }

	// id에 해당하는 메뉴를 Optional로 감싸서 반환한다. 
    @Override
    public Optional<Menu> findById(Long id) {
        // 해당 id가 없으면 null이 나올 수 있으므로
        // Optional.ofNullable()로 감싸서 안전하게 반환한다.
        return Optional.ofNullable(store.get(id));
    }

	// 메뉴를 저장한다.
    @Override
    public Menu save(Menu menu) {
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