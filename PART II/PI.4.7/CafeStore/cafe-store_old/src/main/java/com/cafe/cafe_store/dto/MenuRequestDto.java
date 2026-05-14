package com.cafe.cafe_store.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 클라이언트(손님)가 메뉴 등록 시 서버로 전송하는 데이터 양식입니다.
 * POST /api/menus 요청의 Body에 담겨 들어옵니다.
 *
 * 주의: id는 서버가 자동 생성하므로 포함하지 않습니다.
 */
@Getter
@Setter
@NoArgsConstructor
public class MenuRequestDto {
    
    private String name;      // 등록할 메뉴 이름
    private int price;        // 등록할 메뉴 가격
    private String category;  // 등록할 메뉴 카테고리
}
