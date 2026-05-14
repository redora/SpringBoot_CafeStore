package com.cafe.cafe_store.dto;

public class MenuResponseDto {
    private String name;
    private int price;

    public MenuResponseDto(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
