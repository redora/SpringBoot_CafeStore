package com.cafe.cafe_store.model;

// 카페 메뉴 한 건을 표현하는 클래스이다. 
// 애플리케이션 내부에서 데이터를 주고받을 때 사용하며, 
// 나중에 데이터베이스 테이블과 직접 매핑되는 Entity 역할을 하게 된다.
public class Menu {

    private Long id;              // 메뉴를 구분하기 위한 고유 번호
    private String name;          // 메뉴 이름
    private int price;            // 메뉴 가격
    private String category;      // 메뉴 분류 (예: coffee, dessert)

    // 빈 객체를 먼저 생성할 때 사용하는 기본 생성자이다.
    public Menu() {
    }

    // 전체 필드를 한 번에 초기화하는 생성자
    public Menu(Long id, String name, int price, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
    }
	
	// 아래 getter / setter 메서드는
    // 각 필드 값을 읽거나 수정할 때 사용한다.
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
