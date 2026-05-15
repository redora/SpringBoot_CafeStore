package com.cafe.cafe_store.model;

public class MenuRequestDto {
    private String name;      // 등록할 메뉴 이름
    private int price;        // 등록할 메뉴 가격
    private String category;  // 등록할 메뉴 카테고리
	
	// Spring이 JSON을 객체로 변환할 때 기본 생성자가 필요하다.
    public MenuRequestDto() {
    }

	// 채워진 값을 꺼낼 때 getter 메서드를 사용한다.
    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }
    
	// Spring이 JSON 값을 객체 필드에 채워 넣을 때 setter를 사용한다.
    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
