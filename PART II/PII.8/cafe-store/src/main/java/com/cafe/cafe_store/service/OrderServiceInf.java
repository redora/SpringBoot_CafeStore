package com.cafe.cafe_store.service;

import com.cafe.cafe_store.dto.OrderRequestDto;
import com.cafe.cafe_store.dto.OrderResponseDto;

import java.util.List;

/**
 * 주문 서비스 인터페이스
 */
public interface OrderServiceInf {

    /*
     * 주문 생성 (재고 차감 포함)
     */
    OrderResponseDto createOrder(OrderRequestDto requestDto);
    /*
     * 전체 주문 목록 조회
     */
    List<OrderResponseDto> getAllOrders();
    /*
     * 특정 주문 단건 조회
     */
    OrderResponseDto getOrderById(Long id);
    /*
     * 주문 취소 (재고 복구 포함)
     */
    OrderResponseDto cancelOrder(Long id);
}