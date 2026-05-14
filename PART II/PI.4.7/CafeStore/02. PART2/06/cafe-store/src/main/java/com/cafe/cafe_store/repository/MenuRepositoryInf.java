package com.cafe.cafe_store.repository;

import com.cafe.cafe_store.model.Menu;

import java.util.List;
import java.util.Optional;

public interface MenuRepositoryInf {

    List<Menu> findAll();

    Optional<Menu> findById(Long id);

    Menu save(Menu menu);

    void deleteById(Long id);
}
