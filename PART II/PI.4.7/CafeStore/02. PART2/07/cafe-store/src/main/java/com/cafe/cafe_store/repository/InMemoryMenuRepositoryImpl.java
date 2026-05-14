package com.cafe.cafe_store.repository;

import com.cafe.cafe_store.model.Menu;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * MenuRepository의 메모리 기반 구현체입니다.
 * DB 없이 자바의 HashMap을 이용해 데이터를 저장합니다.
 *
 * @Repository: "나는 저장소입니다"라고 Spring에게 알리는 어노테이션
 *              Spring이 이 클래스를 자동으로 Bean으로 등록해줍니다.
 */

@Repository
public class InMemoryMenuRepositoryImpl implements MenuRepositoryInf {

    private final Map<Long, Menu> store = new HashMap<>();
    private Long sequence = 0L;

    public InMemoryMenuRepositoryImpl() {
        save(new Menu(null, "아메리카노", 4500, "coffee"));
        save(new Menu(null, "카페라떼", 5000, "coffee"));
        save(new Menu(null, "바닐라라떼", 5500, "coffee"));
        save(new Menu(null, "치즈케이크", 6500, "dessert"));
    }

    @Override
    public List<Menu> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<Menu> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Menu save(Menu menu) {
        if (menu.getId() == null) {
            menu.setId(++sequence);
        }
        store.put(menu.getId(), menu);
        return menu;
    }

    @Override
    public void deleteById(Long id) {
        store.remove(id);
    }
}
