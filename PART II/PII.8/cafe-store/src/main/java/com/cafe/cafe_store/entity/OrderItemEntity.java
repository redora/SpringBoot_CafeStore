package com.cafe.cafe_store.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 주문 안에 들어 있는 "개별 상품 한 줄"을 표현하는 클래스입니다.
 *
 * 예를 들어 "아메리카노 2잔, 녹차케이크 1개" 주문이라면
 * OrderItem이 2개 생성됩니다.
 *
 * [주의] menuName과 unitPrice를 OrderItem에 저장하는 이유:
 * 나중에 메뉴 이름이나 가격이 변경되더라도 주문 당시의 정보가 유지되어야 합니다.
 * 이를 "주문 시점 스냅샷(snapshot)"이라고 합니다.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemEntity {

    private Long menuId;       // 어떤 메뉴를 주문했는지 나타내는 메뉴 ID 
    private String menuName;   // 주문 당시의 메뉴 이름    
    private int unitPrice;     // 주문 당시의 단가    
    private int quantity;      // 주문 수량    
    private int totalPrice;    // 이 항목의 총 금액 = unitPrice * quantity
}