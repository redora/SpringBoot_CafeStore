package com.cafe.cafe_store.model;

// 클라이언트에게 메뉴 정보를 응답으로 전달하기 위한 DTO
public class MenuResponseDto {

    private String name; // 메뉴 이름 
    private int price; // 메뉴 가격

	// 객체를 만들 때 이름과 가격을 함께 전달받는다.
    public MenuResponseDto(String name, int price) {
        this.name = name;
        this.price = price;
    }

    // Spring이 객체를 JSON으로 변환할 때 getter 메서드를 사용한다. 
    // getName() → "name" 키로 JSON에 포함된다.
    public String getName() {
        return name;
    }
    
	// getPrice() → "price" 키로 JSON에 포함된다.
    public int getPrice() {
        return price;
    }
}