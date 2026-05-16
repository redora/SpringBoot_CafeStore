package com.cafe.cafe_store.service;

import com.cafe.cafe_store.dto.MenuRequestDto;
import com.cafe.cafe_store.dto.MenuResponseDto;
import com.cafe.cafe_store.entity.MenuEntity;
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
 *           Controller가 전달한 요청을 실제 업무 규칙에 맞게 처리합니다.
 *           Repository를 이용해 데이터를 읽고 씁니다.
 *           유효성 검사와 예외 처리를 담당합니다.
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

    /**
     * 특정 메뉴를 단건 조회합니다.
     * 존재하지 않는 ID로 조회하면 NotFoundException(404)을 던집니다.
     */
    @Override
    public MenuResponseDto getMenuById(Long id) {
        /*
         * orElseThrow() 사용 이유:
         * Optional에서 값을 꺼낼 때, 없으면 바로 예외를 던집니다.
         * 예외가 발생하면 스프링이 자동으로 500 에러를 반환합니다.
         * (8장에서 이를 더 예쁜 404 응답으로 바꾸는 방법을 배웁니다)
         */
        MenuEntity menu = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "메뉴를 찾을 수 없습니다. 요청한 id: " + id
                ));

        return MenuResponseDto.from(menu);
    }

    /**
     * 새 메뉴를 등록합니다.
     * 등록 전에 유효성 검사를 먼저 수행합니다.
     * 새 메뉴는 기본적으로 마감되지 않은(closed=false) 상태로 생성됩니다.
     */
    @Override
    public MenuResponseDto createMenu(MenuRequestDto requestDto) {
        /*
         * 처리 흐름:
         * 1. 유효성 검사 (validateMenuRequest)
         * 2. RequestDto(클라이언트 요청)를 Menu(내부 모델)로 변환
         * 3. 저장소에 저장 (이때 id가 자동 부여됨)
         * 4. 저장된 Menu를 ResponseDto로 변환하여 반환
         */
        
        // 유효성 검사
        validateMenuRequest(requestDto);

        // DTO → Model 변환
        MenuEntity menu = new MenuEntity();
        menu.setName(requestDto.getName());
        menu.setPrice(requestDto.getPrice());
        menu.setCategory(requestDto.getCategory());
        // id는 설정하지 않습니다 → Repository에서 자동 부여

        // 저장소에 저장
        MenuEntity savedMenu = menuRepository.save(menu);

        // 저장된 결과(id 포함)를 DTO로 변환하여 반환
        return MenuResponseDto.from(savedMenu);
    }

    /**
     * 메뉴 재고를 수정합니다.
     */
    @Override
    public MenuResponseDto updateStock(Long id, int stock) {
        if (stock < 0) {
            throw new RuntimeException("재고는 0 이상이어야 합니다.");
        }

        MenuEntity menu = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("메뉴를 찾을 수 없습니다. id=" + id));

        menu.setStock(stock);
        MenuEntity savedMenu = menuRepository.save(menu);
        return MenuResponseDto.from(savedMenu);
    }

    /**
     * 메뉴 마감 처리를 수행합니다.
     */
    @Override
    public MenuResponseDto closeMenu(Long id) {
        MenuEntity menu = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("메뉴를 찾을 수 없습니다. id=" + id));

        menu.setClosed(true);
        MenuEntity savedMenu = menuRepository.save(menu);
        return MenuResponseDto.from(savedMenu);
    }

    /**
     * 메뉴 마감 해제를 수행합니다.
     */
    @Override
    public MenuResponseDto openMenu(Long id) {
        MenuEntity menu = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("메뉴를 찾을 수 없습니다. id=" + id));

        menu.setClosed(false);
        MenuEntity savedMenu = menuRepository.save(menu);
        return MenuResponseDto.from(savedMenu);
    }

    /**
     * 메뉴 등록 시 입력 값의 유효성을 검사하는 내부 메서드입니다.
     * private으로 선언하여 이 클래스 내부에서만 사용합니다.
     *
     * [검사 항목]
     * - 이름 : null이거나 공백만 있으면 안 됩니다.
     * - 가격 : 음수이면 안 됩니다.
     * - 재고 : 음수이면 안 됩니다.
     */
    private void validateMenuRequest(MenuRequestDto requestDto) {
        if (requestDto.getName() == null || requestDto.getName().isBlank()) {
            throw new RuntimeException("메뉴 이름은 필수입니다.");
        }

        if (requestDto.getPrice() < 0) {
            throw new RuntimeException("가격은 0 이상이어야 합니다.");
        }

        if (requestDto.getStock() < 0) {
            throw new RuntimeException("재고는 0 이상이어야 합니다.");
        }
    }
}
