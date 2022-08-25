package com.shop.repository;

import com.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// 레파지토리에 검색 메소드 추가 시 키워드 활용하여 이름 지정. findBy + 컬럼명 + 조건..

public interface ItemRepository extends JpaRepository<Item, Long> {
    
    List<Item> findByItemNm(String itemNm); // 상품명으로 조회 메소드

    List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail); // 상품 이름 or 설명 조건검색

    List<Item> findByPriceLessThan(Integer price);

    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price); // 상품 가격 높은 순으로 조회
}
