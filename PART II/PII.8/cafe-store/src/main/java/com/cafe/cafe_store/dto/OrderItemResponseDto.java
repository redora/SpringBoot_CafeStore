package com.cafe.cafe_store.dto;

import com.cafe.cafe_store.entity.OrderItemEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문 항목 응답 DTO
 *   
 * [OrderItem을 직접 반환하지 않는 이유]
 * 내부 Entity가 변경되더라도 외부 API 응답 형식을 독립적으로 유지할 수 있습니다.
 */
@Getter
@NoArgsConstructor
public class OrderItemResponseDto {

    private Long menuId;       // 주문한 메뉴 ID
    private String menuName;   // 주문 당시 메뉴 이름
    private int unitPrice;     // 주문 당시 단가
    private int quantity;      // 주문 수량
    private int totalPrice;    // 항목 합계 금액 (unitPrice × quantity)

	/**
     * OrderItem Entity를 OrderItemResponseDto로 변환하는 정적 팩토리 메서드입니다.
     * 사용 예: OrderItemResponseDto dto = OrderItemResponseDto.from(orderItem);
     */
    public static OrderItemResponseDto from(OrderItemEntity item) {
        OrderItemResponseDto dto = new OrderItemResponseDto();
        dto.menuId = item.getMenuId();
        dto.menuName = item.getMenuName();
        dto.unitPrice = item.getUnitPrice();
        dto.quantity = item.getQuantity();
        dto.totalPrice = item.getTotalPrice();
        return dto;
    }
}
