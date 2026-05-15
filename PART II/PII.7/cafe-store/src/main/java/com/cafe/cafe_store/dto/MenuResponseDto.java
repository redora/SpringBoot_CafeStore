package com.cafe.cafe_store.dto;

import com.cafe.cafe_store.entity.Menu;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 서버가 클라이언트(관리자)에게 메뉴 정보를 전달할 때 사용하는 데이터 양식입니다.
 * Menu 모델을 직접 노출하지 않고 이 DTO를 통해 필요한 정보만 전달합니다.
 * [주의] @Setter는 의도적으로 추가하지 않았습니다.
 *        응답 데이터는 한 번 생성된 후 변경되지 않아야 하므로,
 *        setter를 제공하지 않아 외부에서 값을 임의로 수정하는 것을 방지합니다.
 *        값 설정은 아래의 from() 메서드를 통해서만 이루어집니다.
 */
@Getter
@NoArgsConstructor
public class MenuResponseDto {

    private Long id;          // 메뉴 고유 번호
    private String name;      // 메뉴 이름
    private int price;        // 가격
    private String category;  // 카테고리

	/**
     * Menu 객체를 MenuResponseDto로 변환하는 정적 팩토리 메서드입니다.
     *
     * [사용법]
     *   MenuResponseDto dto = MenuResponseDto.from(menu);
     *
     * [from() 이라는 이름의 의미]
     *   "어디로부터(from) 만들었는가"를 나타내는 관례적인 이름입니다.
     *   즉, Menu 객체로부터 MenuResponseDto를 만든다는 의미입니다.
     *
     * [왜 정적(static) 메서드인가요?]
     *   MenuResponseDto 객체를 먼저 생성하지 않아도 바로 호출할 수 있습니다.
     *   MenuResponseDto.from(menu) 처럼 클래스 이름으로 직접 호출합니다.
     *
     * [필드에 직접 접근하는 이유]
     *   @Setter를 추가하지 않았으므로, 같은 클래스 내부에서
     *   dto.id = menu.getId() 처럼 필드에 직접 접근하여 값을 설정합니다.
     *   이는 Java의 접근 제어 규칙상 같은 클래스 내부에서는 허용됩니다.
     *
     * @param menu  변환할 Menu 객체
     * @return      Menu 정보가 담긴 MenuResponseDto 객체
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