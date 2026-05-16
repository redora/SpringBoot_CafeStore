package com.cafe.cafe_store.controller;

import com.cafe.cafe_store.dto.MenuRequestDto;
import com.cafe.cafe_store.dto.MenuResponseDto;
import com.cafe.cafe_store.dto.MenuStockUpdateRequestDto;
import com.cafe.cafe_store.service.MenuServiceInf;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 메뉴 관련 HTTP 요청을 처리하는 Controller입니다.
 *
 * @RestController: @Controller + @ResponseBody의 합성 어노테이션
 *                  이 클래스의 모든 메서드 반환값을 JSON으로 직렬화합니다.
 *
 * @RequestMapping("/api/menus"): 이 Controller의 모든 URL 앞에
 *                                "/api/menus"가 공통으로 붙습니다.
 *
 * @RequiredArgsConstructor: final 필드(menuService)에 대한 생성자 주입 자동 생성
 */

@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
public class MenuController {
    
    /**
     * MenuService를 주입받습니다.
     * Controller는 Service 인터페이스만 알면 됩니다.
     * ServiceImpl이 어떻게 구현되어 있는지 알 필요가 없습니다.
     */
    private final MenuServiceInf menuService;

    
    // =========================================================
    // GET /api/menus
    // 전체 메뉴 목록 조회
    // =========================================================

    /**
     * 전체 메뉴 목록을 조회합니다.
     *
     * ResponseEntity<T>를 사용하는 이유:
     * HTTP 상태코드(200, 404 등)를 직접 지정할 수 있습니다.
     * ResponseEntity.ok(body) = 200 OK + body 반환
     */
    @GetMapping
    public ResponseEntity<List<MenuResponseDto>> getAllMenus() {
        List<MenuResponseDto> menus = menuService.getAllMenus();
        return ResponseEntity.ok(menus); // HTTP 200 OK
    }


    // =========================================================
    // GET /api/menus/{id}
    // 특정 메뉴 단건 조회
    // =========================================================

    /**
     * @PathVariable: URL 경로의 {id} 부분을 메서드 파라미터로 받습니다.
     * 예: GET /api/menus/1 → id = 1L
     */
    @GetMapping("/{id}")
    public ResponseEntity<MenuResponseDto> getMenuById(@PathVariable Long id) {
        MenuResponseDto menu = menuService.getMenuById(id);
        return ResponseEntity.ok(menu); // HTTP 200 OK
    }


    // =========================================================
    // POST /api/menus
    // 새 메뉴 등록
    // =========================================================

    /**
     * @RequestBody: HTTP 요청 Body의 JSON 데이터를 MenuRequestDto 객체로 변환합니다.
     * 예: Body에 {"name":"카푸치노","price":5000,"category":"coffee"} →
     *     requestDto.getName() = "카푸치노"
     *
     * HTTP 201 Created를 반환하는 이유:
     * RESTful API 관례에 따라 새 리소스가 생성될 때는
     * 200 OK가 아닌 201 Created를 반환합니다.
     */
    @PostMapping
    public ResponseEntity<MenuResponseDto> createMenu(@RequestBody MenuRequestDto requestDto) {
        MenuResponseDto createdMenu = menuService.createMenu(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMenu); // HTTP 201 Created
    }

    /**
     * 재고 수정
     * PATCH /api/menus/{id}/stock
     * PATCH를 사용하는 이유:
     * 전체 메뉴 정보를 교체(PUT)하는 것이 아니라
     * 재고 수량 하나만 부분 수정하기 때문입니다.
     */
    @PatchMapping("/{id}/stock")
    public ResponseEntity<MenuResponseDto> updateStock(
            @PathVariable Long id,
            @RequestBody MenuStockUpdateRequestDto requestDto
    ) {
        return ResponseEntity.ok(menuService.updateStock(id, requestDto.getStock()));
    }

    /**
     * 메뉴 마감 처리
     * PATCH /api/menus/{id}/close
     */
    @PatchMapping("/{id}/close")
    public ResponseEntity<MenuResponseDto> closeMenu(@PathVariable Long id) {
        return ResponseEntity.ok(menuService.closeMenu(id));
    }

    /**
     * 메뉴 마감 해제
     * PATCH /api/menus/{id}/open
     */
    @PatchMapping("/{id}/open")
    public ResponseEntity<MenuResponseDto> openMenu(@PathVariable Long id) {
        return ResponseEntity.ok(menuService.openMenu(id));
    }
}
