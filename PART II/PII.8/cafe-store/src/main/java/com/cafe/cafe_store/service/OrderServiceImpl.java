package com.cafe.cafe_store.service;


import com.cafe.cafe_store.dto.OrderItemRequestDto;
import com.cafe.cafe_store.dto.OrderRequestDto;
import com.cafe.cafe_store.dto.OrderResponseDto;
import com.cafe.cafe_store.entity.MenuEntity; 
import com.cafe.cafe_store.entity.OrderEntity; 
import com.cafe.cafe_store.entity.OrderItemEntity;
import com.cafe.cafe_store.repository.MenuRepositoryInf;
import com.cafe.cafe_store.repository.OrderRepositoryInf;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 주문 관련 비즈니스 로직을 처리하는 서비스 구현체입니다.
 *
 * [이 클래스의 핵심 역할]
 * 1. 주문 생성 시 재고 차감
 * 2. 주문 취소 시 재고 복구
 *
 * [중요 원칙: 검증 먼저, 변경 나중]
 * 재고 차감과 같은 데이터 변경 작업은 모든 검증이 완료된 후에만 수행합니다.
 * 중간에 실패하면 일부만 차감되는 "데이터 불일치" 상태가 발생하기 때문입니다.
 * 이 문제는 9장에서 @Transactional을 도입하면 더 안전하게 처리할 수 있습니다.
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderServiceInf {

    private final MenuRepositoryInf menuRepository;
    private final OrderRepositoryInf orderRepository;
	
	/**
     * 주문을 생성합니다.
     *
     * [처리 순서]
     * 1단계. 기본 유효성 검사 (항목 비어 있는지, menuId·수량이 올바른지)
     * 2단계. 동일 메뉴 중복 주문 수량 합산 (같은 menuId가 여러 번 오면 수량을 합칩니다)
     * 3단계. 모든 메뉴에 대해 존재 여부 / 마감 여부 / 재고 부족 여부 검증
     * 4단계. 검증 통과 후 OrderItem 목록 생성 및 총액 계산
     * 5단계. 재고 차감
     * 6단계. 주문 저장 및 응답 반환
     */     
    @Override
    public OrderResponseDto createOrder(OrderRequestDto requestDto) {
        // 1단계: 기본 유효성 검사
        if (requestDto.getItems() == null || requestDto.getItems().isEmpty()) {
            throw new RuntimeException("주문 항목이 비어 있습니다.");
        }

		// 2단계: 동일 메뉴 수량 합산
        /**
         * 같은 메뉴가 한 주문 안에 여러 번 들어오는 경우를 대비해서
         * menuId별 총 수량을 먼저 계산합니다.
         *
         * 예:
         * - menuId=1, quantity=2
         * - menuId=1, quantity=3
         *
         * => 실제로는 menuId=1이 총 5개 필요합니다.
         */
        Map<Long, Integer> totalQtyByMenuId = new HashMap<>();

        for (OrderItemRequestDto item : requestDto.getItems()) {
            if (item.getMenuId() == null) {
                throw new RuntimeException("menuId는 필수입니다.");
            }

            if (item.getQuantity() <= 0) {
                throw new RuntimeException("주문 수량은 1 이상이어야 합니다.");
            }

            totalQtyByMenuId.merge(item.getMenuId(), item.getQuantity(), Integer::sum);
        }
        
        // 3단계: 모든 메뉴 검증 (존재 여부 / 마감 여부 / 재고 부족)
        /**
         *    - 메뉴 존재 여부
         *    - 마감 여부
         *    - 재고 부족 여부
         *
        * 메뉴 A 재고 차감 → 메뉴 B 재고 부족으로 실패하면
         * 메뉴 A의 재고는 이미 줄어든 상태가 됩니다. (데이터 불일치)
         * "모두 OK이면 차감, 하나라도 실패하면 아무것도 차감하지 않는" 방식을 사용합니다.
         */
        Map<Long, MenuEntity> menuMap = new HashMap<>();

        for (Map.Entry<Long, Integer> entry : totalQtyByMenuId.entrySet()) {
            Long menuId = entry.getKey();
            int totalQty = entry.getValue();

            MenuEntity menu = menuRepository.findById(menuId)
                    .orElseThrow(() -> new RuntimeException("메뉴를 찾을 수 없습니다. id=" + menuId));

            if (menu.isClosed()) {
                throw new RuntimeException("해당 메뉴는 현재 마감되었습니다. menuId=" + menuId);
            }

            if (menu.getStock() < totalQty) {
                throw new RuntimeException(
                        "재고가 부족합니다. menuId=" + menuId
                                + ", 현재 재고=" + menu.getStock()
                                + ", 요청 수량=" + totalQty
                );
            }

            menuMap.put(menuId, menu);   // 검증을 통과한 메뉴를 보관
        }

        // 4단계: OrderItem 목록 생성 및 총액 계산
        List<OrderItemEntity> orderItems = new ArrayList<>();
        int totalPrice = 0;

        for (OrderItemRequestDto item : requestDto.getItems()) {
            MenuEntity menu = menuMap.get(item.getMenuId());
            int itemTotalPrice = menu.getPrice() * item.getQuantity();

            OrderItemEntity orderItem = new OrderItemEntity(
                    menu.getId(),        // 메뉴 ID
                    menu.getName(),      // 주문 당시 메뉴 이름 (스냅샷)
                    menu.getPrice(),     // 주문 당시 단가     (스냅샷)
                    item.getQuantity(),  // 주문 수량
                    itemTotalPrice       // 항목 합계 금액
            );

            orderItems.add(orderItem);
            totalPrice += itemTotalPrice; // 주문 전체 금액 누산
        }

        // 5단계: 재고 차감 
        for (Map.Entry<Long, Integer> entry : totalQtyByMenuId.entrySet()) {
            MenuEntity menu = menuMap.get(entry.getKey());
            int orderedQty = entry.getValue();

            menu.setStock(menu.getStock() - orderedQty);  // 재고 차감
            menuRepository.save(menu);                    // 변경 사항 저장
        }

        // 6단계: 주문 저장 및 응답 반환
        OrderEntity order = new OrderEntity();
        order.setItems(orderItems);
        order.setTotalPrice(totalPrice);
        order.setOrderedAt(LocalDateTime.now());
        order.setStatus("ORDERED"); // 신규 주문 상태
        order.setCanceledAt(null);  // 취소 시각은 아직 없음

        OrderEntity savedOrder = orderRepository.save(order);
        return OrderResponseDto.from(savedOrder);
    }

    @Override
    public List<OrderResponseDto> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(OrderResponseDto::from)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponseDto getOrderById(Long id) {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("주문을 찾을 수 없습니다. id=" + id));

        return OrderResponseDto.from(order);
    }

	/**
	 * 주문 취소 로직
	 *
	 * 1. 주문이 존재하는지 확인
	 * 2. 이미 취소된 주문인지 확인
	 * 3. 주문 항목 수량만큼 재고 복구
	 * 4. 주문 상태를 CANCELED로 변경 및 저장
	 */
    @Override
    public OrderResponseDto cancelOrder(Long id) {
		
		// 1단계: 주문 존재 여부 확인
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("주문을 찾을 수 없습니다. id=" + id));

		// 2단계: 이미 취소된 주문인지 확인
        if ("CANCELED".equals(order.getStatus())) {
            throw new RuntimeException("이미 취소된 주문입니다. id=" + id);
        }

        // 3단계: 재고 복구
        for (OrderItemEntity item : order.getItems()) {
            MenuEntity menu = menuRepository.findById(item.getMenuId())
                    .orElseThrow(() -> new RuntimeException(
                            "주문에 포함된 메뉴를 찾을 수 없습니다. menuId=" + item.getMenuId()
                    ));

            menu.setStock(menu.getStock() + item.getQuantity());
            menuRepository.save(menu);
        }

        // 4단계: 주문 상태 변경 및 저장
        order.setStatus("CANCELED");
        order.setCanceledAt(LocalDateTime.now());

        OrderEntity savedOrder = orderRepository.save(order);
        return OrderResponseDto.from(savedOrder);
    }
}