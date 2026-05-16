package com.cafe.cafe_store.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 주문 항목 1건의 요청 DTO입니다.
 * "어떤 메뉴를 몇 개 주문하겠다"는 정보 한 줄을 나타냅니다.
 */
@Getter
@Setter
@NoArgsConstructor
public class OrderItemRequestDto {

    private Long menuId;   // 주문할 메뉴의 ID (필수)
    private int quantity;  // 주문 수량
}