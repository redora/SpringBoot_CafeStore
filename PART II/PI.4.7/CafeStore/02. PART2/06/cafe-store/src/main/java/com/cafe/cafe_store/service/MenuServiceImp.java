package com.cafe.cafe_store.service;

import com.cafe.cafe_store.dto.MenuResponseDto;
import com.cafe.cafe_store.model.Menu;
import com.cafe.cafe_store.repository.MenuRepositoryInf;
import java.util.Optional;

public class MenuServiceImp implements MenuServiceInf {

    private final MenuRepositoryInf menuRepository;

    public MenuServiceImp(MenuRepositoryInf menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public MenuResponseDto getDefaultMenu() {     
        
        Optional<Menu> optionalMenu = menuRepository.findById(1L);

        if (!optionalMenu.isPresent()) {
            throw new RuntimeException("기본 메뉴를 찾을 수 없습니다.");
        }

        Menu menu = optionalMenu.get();
        MenuResponseDto responseDto = new MenuResponseDto(menu.getName(), menu.getPrice());
        return responseDto;

        /*Menu menu = menuRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("기본 메뉴를 찾을 수 없습니다."));
        return new MenuResponseDto(menu.getName(), menu.getPrice());
        */
    }

    @Override
    public MenuResponseDto getMenuById(Long id) {
        Optional<Menu> optionalMenu = menuRepository.findById(1L);

        if (!optionalMenu.isPresent()) {
            throw new RuntimeException("해당 메뉴를 찾을 수 없습니다. id=" + id);
        }

        Menu menu = optionalMenu.get();
        MenuResponseDto responseDto = new MenuResponseDto(menu.getName(), menu.getPrice());
        return responseDto;

        /*(Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 메뉴를 찾을 수 없습니다. id=" + id));
        return new MenuResponseDto(menu.getName(), menu.getPrice());
        */
    }


}
