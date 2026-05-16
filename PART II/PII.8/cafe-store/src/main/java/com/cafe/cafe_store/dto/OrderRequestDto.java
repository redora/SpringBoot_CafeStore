package com.cafe.cafe_store.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * 주문 요청 DTO
 * 여러 상품을 한 번에 주문할 수 있도록 List 구조를 사용합니다.
 */
@Getter
@Setter
@NoArgsConstructor
public class OrderRequestDto {

    private List<OrderItemRequestDto> items;
}