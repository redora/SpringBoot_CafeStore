package com.cafe.cafe_store.dto;

import com.cafe.cafe_store.entity.OrderEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 주문 전체의 응답 DTO입니다.
 * 주문 생성, 단건 조회, 취소 API 모두 이 DTO를 반환합니다.
 *
 * [cancelable 필드를 추가한 이유]
 * 클라이언트(화면)에서 "취소 버튼을 표시할지 여부"를 판단할 때
 * status 값을 직접 비교하는 로직을 클라이언트에 두지 않아도 되도록
 * 서버에서 미리 계산하여 제공합니다.
 */
@Getter
@NoArgsConstructor
public class OrderResponseDto {

    private Long id;                          // 주문 번호
    private List<OrderItemResponseDto> items; // 주문 항목 목록
    private int totalPrice;                   // 주문 전체 금액
    private LocalDateTime orderedAt;          // 주문 접수 시각
    private String status;                    // 주문 상태 ("ORDERED" 또는 "CANCELED")
    private LocalDateTime canceledAt;         // 취소 시각 (취소 전이면 null)
    private boolean cancelable;               // 취소 가능 여부 (ORDERED 상태일 때만 true)

	/**
     * Order Entity를 OrderResponseDto로 변환하는 정적 팩토리 메서드입니다.
     *
     * [stream 변환 흐름]
     * 1. order.getItems()                      → OrderItem 리스트를 가져옵니다.
     * 2. .stream()                             → 리스트를 스트림으로 변환합니다.
     * 3. .map(OrderItemResponseDto::from)      → 각 OrderItem을 DTO로 변환합니다.
     * 4. .collect(Collectors.toList())         → 다시 List로 수집합니다.
     */
    public static OrderResponseDto from(OrderEntity order) {
        OrderResponseDto dto = new OrderResponseDto();
        dto.id = order.getId();
        dto.items = order.getItems()
                .stream()
                .map(OrderItemResponseDto::from)
                .collect(Collectors.toList());
        dto.totalPrice = order.getTotalPrice();
        dto.orderedAt = order.getOrderedAt();
        dto.status = order.getStatus();
        dto.canceledAt = order.getCanceledAt();

        // 아직 취소되지 않은 주문이면 다시 취소 가능
        dto.cancelable = "ORDERED".equals(order.getStatus());
        return dto;
    }
}