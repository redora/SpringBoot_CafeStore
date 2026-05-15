package com.cafe.cafe_store.service;

import com.cafe.cafe_store.dto.MenuRequestDto;
import com.cafe.cafe_store.dto.MenuResponseDto;
import com.cafe.cafe_store.entity.Menu;
import com.cafe.cafe_store.repository.MenuRepositoryInf;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

/**
 * MenuService 인터페이스의 실제 구현 클래스입니다.
 *
 * @Service: "나는 서비스 계층입니다"라고 Spring에게 알리는 어노테이션
 *           Spring이 이 클래스를 자동으로 Bean으로 등록해줍니다.
 *
 * @RequiredArgsConstructor: final 필드를 파라미터로 받는 생성자를 자동 생성합니다.
 *                           이를 통해 Spring이 의존성을 자동 주입(DI)합니다.
 */
@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuServiceInf {

    /**
     * MenuRepository 의존성 주입
     *
     * final로 선언하는 이유:
     * 1. @RequiredArgsConstructor가 생성자 주입 코드를 자동 생성합니다
     * 2. 한 번 주입된 후 변경되지 않음을 보장합니다
     * 3. 테스트 시 Mock 객체로 교체하기 쉽습니다
     */
    private final MenuRepositoryInf menuRepository;

	@Override
    public List<MenuResponseDto> getAllMenus() {
        /*
         * 처리 흐름:
         * 1. menuRepository.findAll() → List<Menu> 반환
         * 2. stream()으로 리스트를 스트림으로 변환
         * 3. map(MenuResponseDto::from) → 각 Menu를 MenuResponseDto로 변환
         * 4. collect(Collectors.toList()) → 다시 List로 수집
         */
        return menuRepository.findAll()
                .stream()
                .map(MenuResponseDto::from)   // Menu → MenuResponseDto 변환
                .collect(Collectors.toList());
    }

    @Override
    public MenuResponseDto getMenuById(Long id) {
        /*
         * orElseThrow() 사용 이유:
         * Optional에서 값을 꺼낼 때, 없으면 바로 예외를 던집니다.
         * 예외가 발생하면 스프링이 자동으로 500 에러를 반환합니다.
         * (8장에서 이를 더 예쁜 404 응답으로 바꾸는 방법을 배웁니다)
         */
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "메뉴를 찾을 수 없습니다. 요청한 id: " + id
                ));

        return MenuResponseDto.from(menu);
    }

    @Override
    public MenuResponseDto createMenu(MenuRequestDto requestDto) {
        /*
         * 처리 흐름:
         * 1. RequestDto(클라이언트 요청)를 Menu(내부 모델)로 변환
         * 2. 저장소에 저장 (이때 id가 자동 부여됨)
         * 3. 저장된 Menu를 ResponseDto로 변환하여 반환
         */

        // ① DTO → Model 변환
        Menu menu = new Menu();
        menu.setName(requestDto.getName());
        menu.setPrice(requestDto.getPrice());
        menu.setCategory(requestDto.getCategory());
        // id는 설정하지 않습니다 → Repository에서 자동 부여

        // ② 저장소에 저장
        Menu savedMenu = menuRepository.save(menu);

        // ③ 저장된 결과(id 포함)를 DTO로 변환하여 반환
        return MenuResponseDto.from(savedMenu);
    }
}
