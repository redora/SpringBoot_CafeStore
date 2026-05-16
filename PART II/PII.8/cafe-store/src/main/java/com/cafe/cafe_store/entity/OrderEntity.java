package com.cafe.cafe_store.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Order 클래스는 주문 1건 전체를 표현합니다.
 * 한 주문 안에는 여러 개의 OrderItem이 들어갈 수 있습니다.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {

    private Long id;                      // 주문 번호
    private List<OrderItemEntity> items;  // 주문에 포함된 상품 목록
    private int totalPrice;               // 주문 전체 금액
    private LocalDateTime orderedAt;      // 주문 시각
    private String status;                // 주문 상태(ORDERED  : 정상 주문됨, CANCELED : 주문 취소됨)
    private LocalDateTime canceledAt;     // 주문 취소 시각 (취소되지 않았다면 null)
}
