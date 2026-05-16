package com.cafe.cafe_store.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 메뉴 재고 수정 요청 DTO
 * {
 *   "stock": 20
 * }
 */
@Getter
@Setter
@NoArgsConstructor
public class MenuStockUpdateRequestDto {
    private int stock;
}
