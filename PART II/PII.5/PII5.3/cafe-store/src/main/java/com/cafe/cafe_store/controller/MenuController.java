package com.cafe.cafe_store.controller;

import com.cafe.cafe_store.model.MenuRequestDto;
import com.cafe.cafe_store.model.MenuResponseDto;
import com.cafe.cafe_store.service.MenuServiceInf;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


// 이 클래스는 "메뉴 관련 요청"을 처리하는 컨트롤러이다.
// 사용자가 브라우저나 앱에서 특정 주소(URL)로 요청을 보내면,
// 아래 메서드들이 그 요청을 받아서 응답을 돌려준다.
@RestController
public class MenuController {
    
    // Service 객체를 주입받아 사용한다.
    private final MenuServiceInf menuService;

    public MenuController(MenuServiceInf menuService) {
        this.menuService = menuService;
    }

    // GET 요청이 /menu 로 들어오면 실행된다.
   // 기본 메뉴 조회는 Service에 위임한다.
    @GetMapping("/menu")
    public MenuResponseDto getMenu() {
        return menuService.getDefaultMenu();
    }

	 // URL 경로에 있는 값을 읽어오는 방식입니다. (Path Variable)
    // /menus/{id} 에서 {id} 부분이 실제 값으로 바뀐다.
    @GetMapping("/menus/{id}")
    public MenuResponseDto getMenuById(@PathVariable Long id) {
	    // @PathVariable
        // URL에 들어있는 값을 변수 id에 담아줍니다.
        // URL 경로에서 추출한 id를 Service에 전달한다.
        return menuService.getMenuById(id);
    }

    // 쿼리 파라미터(Query Parameter)를 사용하는 예제.
    // 예: /search?category=coffee&maxPrice=5000
    // 위 요청이 들어오면
    // category = "coffee"
    // maxPrice = 5000    
    @GetMapping("/search")
    public String searchMenu(@RequestParam String category, @RequestParam int maxPrice) {
        // @RequestParam
        // URL 뒤에 붙는 ?key=value 형태의 데이터를 읽어옵니다.
        // 예: /search?category=coffee&maxPrice=5000
        // category 변수에는 "coffee"
        // maxPrice 변수에는 5000 이 들어갑니다.
        return "카테고리: " + category + ", 최대 가격: " + maxPrice + "원 이하 메뉴를 검색합니다.";
    }

    // @PostMapping : POST 방식으로 /menus 주소에 요청이 들어오면 이 메서드를 실행한다. 
    // @RequestBody : 요청 본문에 담긴 JSON 데이터를 MenuRequestDto 객체로 변환한다.
    @PostMapping("/menus")
    public MenuResponseDto createMenu(@RequestBody MenuRequestDto request) {
        return new MenuResponseDto(request.getName(), request.getPrice());
    }

}
