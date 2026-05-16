package com.cafe.cafe_store.dto;

import com.cafe.cafe_store.entity.MenuEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 서버가 클라이언트(관리자)에게 메뉴 정보를 전달할 때 사용하는 데이터 양식입니다.
 * Menu 모델을 직접 노출하지 않고 이 DTO를 통해 필요한 정보만 전달합니다.
 * [주의] @Setter는 의도적으로 추가하지 않았습니다.
 *        응답 데이터는 한 번 생성된 후 변경되지 않아야 하므로,
 *        setter를 제공하지 않아 외부에서 값을 임의로 수정하는 것을 방지합니다.
 *        값 설정은 아래의 from() 메서드를 통해서만 이루어집니다.
  * [orderable 필드를 추가한 이유]
 * 클라이언트에서 "주문 가능 여부"를 판단하려면 closed와 stock을 모두 확인해야 합니다.
 * 서버에서 미리 계산하여 제공하면 클라이언트 로직이 단순해집니다. 
 */
@Getter
@NoArgsConstructor
public class MenuResponseDto {

    private Long id;          // 메뉴 고유 번호
    private String name;      // 메뉴 이름
    private int price;        // 가격
    private String category;  // 카테고리
    private int stock;          // 현재 남은 재고 수량
    private boolean closed;     // 마감 여부 (true = 마감, false = 판매 중)
    private boolean orderable;  // 주문 가능 여부 (closed가 false이고 stock이 1 이상일 때만 true)

    /**
     * Menu 객체를 MenuResponseDto로 변환하는 정적 팩토리 메서드입니다.
     * 사용법: MenuResponseDto dto = MenuResponseDto.from(menu);
     * "from"이라는 이름은 관례입니다. "어디로부터" 만들었는지를 표현합니다.
     */
    public static MenuResponseDto from(MenuEntity menu) {
        MenuResponseDto dto = new MenuResponseDto();
        dto.id = menu.getId();
        dto.name = menu.getName();
        dto.price = menu.getPrice();
        dto.category = menu.getCategory();
        dto.stock = menu.getStock(); 
        dto.closed = menu.isClosed(); 
        // 주문 가능 조건: 마감되지 않았고(closed=false) 재고가 1개 이상인 경우
        dto.orderable = !menu.isClosed() && menu.getStock() > 0; 
        return dto;
    }
}