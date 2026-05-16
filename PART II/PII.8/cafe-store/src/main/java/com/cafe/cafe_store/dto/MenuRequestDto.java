package com.cafe.cafe_store.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 클라이언트(관리자)가 메뉴 등록 시 서버로 전송하는 데이터 양식입니다.
 * POST /api/menus 요청의 Body에 담겨 들어옵니다.
 *
 * 주의: id는 서버가 자동 생성하므로 포함하지 않습니다.
 * 외부 요청 형식과 내부 DB 구조를 분리하기 위해서입니다. 
 * 나중에 Menu 엔티티에 내부용 필드가 늘어나도, 클라이언트는 필요한 값만 보내면 됩니다.
 */
@Getter
@Setter
@NoArgsConstructor
public class MenuRequestDto {
    
     private String name;      // 등록할 메뉴 이름
    private int price;        // 등록할 메뉴 가격
    private String category;  // 등록할 메뉴 카테고리
    private int stock;        // 주문 가능 수량

}
