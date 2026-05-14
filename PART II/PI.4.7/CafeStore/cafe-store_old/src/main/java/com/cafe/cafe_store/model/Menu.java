package com.cafe.cafe_store.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 카페 메뉴를 표현하는 Model 클래스입니다.
 * 현실의 "메뉴판에 적힌 메뉴 항목 하나"를 코드로 표현합니다.
 */

@Getter                  // 모든 필드의 getXxx() 메서드 자동 생성
@Setter                  // 모든 필드의 setXxx() 메서드 자동 생성
@NoArgsConstructor       // 기본 생성자 자동 생성: new Menu()
@AllArgsConstructor      // 전체 필드 생성자 자동 생성: new Menu(id, name, price, category)
public class Menu {
    private Long id;          // 메뉴 고유 번호 (DB의 PK 역할)
    private String name;      // 메뉴 이름 (예: "아메리카노")
    private int price;        // 가격 (예: 4500)
    private String category;  // 카테고리 (예: "coffee", "dessert")

}
