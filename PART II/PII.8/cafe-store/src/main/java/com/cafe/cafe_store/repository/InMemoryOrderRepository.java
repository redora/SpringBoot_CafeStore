package com.cafe.cafe_store.repository;

import com.cafe.cafe_store.entity.OrderEntity;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * InMemoryOrderRepository 메모리 기반 주문 저장소
 * DB 없이 자바의 HashMap을 이용해 데이터를 저장합니다.
 */
@Repository
public class InMemoryOrderRepository implements OrderRepositoryInf {

	/**
     * 실제 데이터 저장소 역할을 하는 Map입니다.
     * Key: 주문 ID (Long)
     * Value: 주문 객체 (Order)
     */     
    private final Map<Long, OrderEntity> store = new LinkedHashMap<>();
    
    /**
     * 자동 증가하는 ID 시퀀스입니다.
     * save() 호출 시 신규 주문이면 ++idSequence로 ID를 부여합니다.
     */
    private Long idSequence = 0L;

	/**
     * 저장된 모든 주문을 반환합니다.
     * new ArrayList<>()로 감싸는 이유: 외부에서 원본 컬렉션을 직접 수정하지 못하도록 복사본을 반환합니다.
     */
    @Override
    public List<OrderEntity> findAll() {
        return new ArrayList<>(store.values());
    }
	
	/**
     * ID로 특정 주문을 조회합니다.
     * store.get(id)가 null을 반환할 수 있으므로,
     * null 여부를 명시적으로 처리하도록 Optional로 감쌉니다.
     * 호출하는 쪽에서 .orElseThrow()로 예외를 던지거나 .isPresent()로 존재 여부를 확인할 수 있습니다.
     */
    @Override
    public Optional<OrderEntity> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }
    
    /**
    * 주문을 저장합니다.
    * - id가 없는 신규 주문 → ID를 자동 부여하고 저장합니다.
    * - id가 있는 기존 주문 → 동일한 ID로 덮어씁니다(수정).
    */
    @Override
    public OrderEntity save(OrderEntity order) {
        if (order.getId() == null) {
            order.setId(++idSequence);
        }

        store.put(order.getId(), order);
        return order;
    }
}
