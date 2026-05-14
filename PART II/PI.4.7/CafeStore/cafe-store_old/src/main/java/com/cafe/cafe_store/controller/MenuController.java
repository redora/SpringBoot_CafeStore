package com.cafe.cafe_store.controller;

import com.cafe.cafe_store.dto.MenuRequestDto;
import com.cafe.cafe_store.dto.MenuResponseDto;
import com.cafe.cafe_store.service.MenuServiceInf;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
@RequestMapping("/api/menus") // MenuController의 공통 URL 경로 설정
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
    public ResponseEntity<MenuResponseDto> getMenuById(@PathVariable("id") Long id) {
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
}
