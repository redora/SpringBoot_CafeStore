package com.cafe.cafe_store.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 클라이언트(관리자)가 메뉴 등록 시 서버로 전송하는 데이터 양식입니다.
 * POST /api/menus 요청의 Body에 담겨 들어옵니다.
 *
 * [설계 의도]
 * - id는 포함하지 않습니다.
 *   id는 서버(Repository)가 자동으로 부여하는 값이므로
 *   클라이언트가 직접 지정하지 않아도 됩니다.
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class MenuRequestDto {
    
    private String name;      // 등록할 메뉴 이름
    private int price;        // 등록할 메뉴 가격
    private String category;  // 등록할 메뉴 카테고리

}
