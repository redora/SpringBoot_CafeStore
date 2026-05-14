package com.cafe.cafe_store.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import com.cafe.cafe_store.model.Menu;

@Repository
public class InMemoryMenuRepository implements MenuRepositoryInf {

    private final Map<Long, Menu> store = new HashMap<>();
    private Long sequence = 0L;

    public InMemoryMenuRepository() {
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
