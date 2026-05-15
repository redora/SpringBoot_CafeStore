package com.cafe.cafe_store.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 카페 메뉴를 표현하는 Model 클래스입니다.
 * 현실의 "메뉴판에 적힌 메뉴 항목 하나"를 코드로 표현합니다.
 *
 * [Lombok 어노테이션 설명]
 * @Getter          : 모든 필드의 getXxx() 메서드를 자동으로 생성합니다.
 *                    예) getId(), getName(), getPrice(), getCategory()
 * @Setter          : 모든 필드의 setXxx() 메서드를 자동으로 생성합니다.
 *                    예) setId(), setName(), setPrice(), setCategory()
 * @NoArgsConstructor : 파라미터가 없는 기본 생성자를 자동으로 생성합니다.
 *                    예) new Menu()
 * @AllArgsConstructor : 모든 필드를 파라미터로 받는 생성자를 자동으로 생성합니다.
 *                    예) new Menu(1L, "아메리카노", 4500, "coffee")
 *
 * Lombok이 없다면 위 4가지를 개발자가 직접 작성해야 합니다.
 * Lombok을 사용하면 코드가 훨씬 짧고 깔끔해집니다.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Menu {

    private Long id;              // 메뉴를 구분하기 위한 고유 번호
    private String name;          // 메뉴 이름
    private int price;            // 메뉴 가격
    private String category;      // 메뉴 분류 (예: coffee, dessert)   
}
