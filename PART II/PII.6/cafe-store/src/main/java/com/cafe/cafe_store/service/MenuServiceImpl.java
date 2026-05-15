package com.cafe.cafe_store.service;

import com.cafe.cafe_store.model.Menu;
import com.cafe.cafe_store.model.MenuResponseDto;
import com.cafe.cafe_store.repository.MenuRepositoryInf;

import java.util.Optional;
import org.springframework.stereotype.Service;

// @Service 
// 이 클래스가 "비즈니스 로직을 처리하는 서비스 클래스"라는 뜻. 
// Spring은 이 클래스를 자동으로 관리할 수 있는 객체(Bean)로 등록합니다.
@Service 
public class MenuServiceImpl implements MenuServiceInf {

    // 서비스는 데이터를 직접 보관하지 않고
    // Repository에 요청해서 가져온다.
    private final MenuRepositoryInf menuRepository;

	// 생성자 주입 방식으로 Repository를 전달받는다. 
	// Spring이 이 클래스를 생성할 때 적합한 MenuRepositoryInf 구현체를 자동으로 넣어 준다.
    public MenuServiceImpl(MenuRepositoryInf menuRepository) {
        this.menuRepository = menuRepository;
    }

    // 기본 메뉴 1개를 반환하는 메서드입니다. 
    // Controller가 이 메서드를 호출해서 결과를 받아갑니다.
   @Override
    public MenuResponseDto getDefaultMenu() {     
    
        // id가 1인 메뉴를 기본 메뉴로 조회한다.        
        Optional<Menu> optionalMenu = menuRepository.findById(1L);
		
		// 메뉴가 없으면 RuntimeException을 발생시킨다.
        if (!optionalMenu.isPresent()) {
            throw new RuntimeException("기본 메뉴를 찾을 수 없습니다.");
        }

        // Model을 그대로 반환하지 않고
        // 응답용 DTO로 변환해서 반환한다.
        Menu menu = optionalMenu.get();
        MenuResponseDto responseDto = new MenuResponseDto(menu.getName(), menu.getPrice());
        return responseDto;

        /*Menu menu = menuRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("기본 메뉴를 찾을 수 없습니다."));
        return new MenuResponseDto(menu.getName(), menu.getPrice());
        */
    }

    // 전달받은 id로 메뉴를 조회한다. 
	// 메뉴가 없으면 RuntimeException을 발생시킨다.
    @Override
    public MenuResponseDto getMenuById(Long id) {
	    
        Optional<Menu> optionalMenu = menuRepository.findById(1L);

        if (!optionalMenu.isPresent()) {
            throw new RuntimeException("해당 메뉴를 찾을 수 없습니다. id=" + id);
        }

        Menu menu = optionalMenu.get();
        MenuResponseDto responseDto = new MenuResponseDto(menu.getName(), menu.getPrice());
        return responseDto;
		
		// 위 함수를 간단하게 줄이면 다음 코드로 요약할 수 있다.
        /*(Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 메뉴를 찾을 수 없습니다. id=" + id));
        return new MenuResponseDto(menu.getName(), menu.getPrice());
        */
    }
}
