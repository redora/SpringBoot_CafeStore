package com.cafe.cafe_store.repository;

import com.cafe.cafe_store.entity.OrderEntity;

import java.util.List;
import java.util.Optional;

/**
 * 주문 저장소 인터페이스입니다.
 *
 * [인터페이스를 사용하는 이유]
 * 현재는 메모리(InMemoryOrderRepository)에 저장하지만,
 * JPA + DB 저장 방식으로 교체시 Service는 이 인터페이스만 알면 되므로, 
 * 저장소 구현체가 변경되더라도 Service 코드를 수정할 필요가 없습니다.
 * 이를 "의존성 역전 원칙(DIP, Dependency Inversion Principle)"이라고 합니다.
 *
 * 현재 구현체 : InMemoryOrderRepository (메모리 저장)
 * 다음 구현체 : JpaOrderRepository      (DB 저장)
 */
public interface OrderRepositoryInf {

    List<OrderEntity> findAll();              // 전체 주문 목록 조회
    Optional<OrderEntity> findById(Long id);  // 특정 주문 단건 조회 (없으면 Optional.empty() 반환)
    OrderEntity save(OrderEntity order);      // 주문 저장 (신규 생성 또는 수정)
}