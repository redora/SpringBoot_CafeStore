package com.cafe.cafe_store.dto;

import com.cafe.cafe_store.model.Menu;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 서버가 클라이언트(손님)에게 메뉴 정보를 전달할 때 사용하는 데이터 양식입니다.
 * Menu 모델을 직접 노출하지 않고 이 DTO를 통해 필요한 정보만 전달합니다.
 */
@Getter
@NoArgsConstructor
public class MenuResponseDto {

    private Long id;          // 메뉴 고유 번호 (DB의 PK 역할)  
    private String name;      // 등록할 메뉴 이름
    private int price;        // 등록할 메뉴 가격
    private String category;  // 등록할 메뉴 카테고리

    /**
     * Menu 객체를 MenuResponseDto로 변환하는 정적 팩토리 메서드입니다.
     *
     * 사용법: MenuResponseDto dto = MenuResponseDto.from(menu);
     *
     * "from"이라는 이름은 관례입니다. "어디로부터" 만들었는지를 표현합니다.
     */
    public static MenuResponseDto from(Menu menu) {
        MenuResponseDto dto = new MenuResponseDto();
        dto.id = menu.getId();
        dto.name = menu.getName();
        dto.price = menu.getPrice();
        dto.category = menu.getCategory();
        return dto;
    }
}
