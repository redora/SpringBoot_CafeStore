package com.cafe.cafe_store.controller;


import com.cafe.cafe_store.dto.OrderRequestDto;
import com.cafe.cafe_store.dto.OrderResponseDto;
import com.cafe.cafe_store.service.OrderServiceInf;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 주문 관련 API를 처리하는 컨트롤러입니다.
 *
 * [제공하는 API 목록]
 * GET   /api/orders              → 전체 주문 조회
 * GET   /api/orders/{id}         → 주문 단건 조회
 * POST  /api/orders              → 주문 등록 (재고 차감 포함)
 * PATCH /api/orders/{id}/cancel  → 주문 취소 (재고 복구 포함)
 */
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderServiceInf orderService;

    /**
     * 전체 주문 조회
     * GET /api/orders
     */
    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    /**
     * 주문 단건 조회
     * GET /api/orders/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    /**
     * 새 주문을 등록합니다.
     * POST /api/orders
     *
     * 서버 처리 순서:
     * 1. 각 메뉴의 존재 여부·마감 여부·재고 여부 검증
     * 2. 검증 통과 시 재고 차감
     * 3. 주문 저장 및 결과 반환
     */
    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto requestDto) {
        OrderResponseDto createdOrder = orderService.createOrder(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    /**
     * 주문을 취소합니다.
     * PATCH /api/orders/{id}/cancel
     *
     * 서버 처리 순서:
     * 1. 이미 취소된 주문인지 확인
     * 2. 주문 항목 수량만큼 재고 복구
     * 3. 주문 상태를 CANCELED로 변경
     */
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<OrderResponseDto> cancelOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.cancelOrder(id));
    }
}