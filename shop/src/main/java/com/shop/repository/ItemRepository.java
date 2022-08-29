package com.shop.repository;

import com.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;

// 레파지토리에 검색 메소드 추가 시 키워드 활용하여 이름 지정. findBy + 컬럼명 + 조건..

public interface ItemRepository extends JpaRepository<Item, Long>, QuerydslPredicateExecutor<Item> {
    
    List<Item> findByItemNm(String itemNm); // 상품명으로 조회 메소드

    List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail); // 상품 이름 or 설명 조건검색

    List<Item> findByPriceLessThan(Integer price);

    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price); // 상품 가격 높은 순으로 조회
    
    //JPQL 쿼리 itemdetail 키워드 검색해서 가격순 내림차순
    @Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc") 
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail); // JPQL 활용 상품 데이터 조회 Param 사용 전달하는 파라미터 명시

    @Query(value = "select * from item i where i.item_detail like %:itemDetail% order by i.price desc", nativeQuery = true)
    List<Item> findByItemDetailByNative(@Param("itemDetail") String itemDetail);
}
