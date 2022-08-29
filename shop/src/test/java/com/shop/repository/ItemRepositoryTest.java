package com.shop.repository;

import com.shop.constant.ItemSellStatus;
import com.shop.entity.Item;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAQuery;
import com.shop.entity.QItem;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;

import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties") // 테스트 설정 우선
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @PersistenceContext
    EntityManager em; // 영속성 컨텍스트 사용위해 빈 주입

    @Test
    @DisplayName("상품 저장 테스트")
    public void createItemTest() {
        for (int i = 1; i <= 10; i++) {
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            Item savedItem = itemRepository.save(item);
            System.out.println(savedItem.toString());
        }

    }

    @Test
    @DisplayName("상품명 조회 테스트")
    public void findByItemNmTest() {
        this.createItemTest();
        List<Item> itemList = itemRepository.findByItemNm("테스트 상품1");
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("상품명, 상품 상세조건 or 테스트")
    public void findByItemNmOrItemDetailTest() {
        this.createItemTest();

        List<Item> itemList = itemRepository.findByItemNmOrItemDetail("테스트 상품1", "테스트 상품 상세 설명5");
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("가격 LessThan test")
    public void findByPriceLessThanTest() {
        this.createItemTest();

        List<Item> itemList = itemRepository.findByPriceLessThan(1004);
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("가격 내림차순 test")
    public void findByPriceLessThanOrderByPriceDescTest() {
        this.createItemTest();

        List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(10005);
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("@Query 사용 상품 데이터 조회")
    public void findByItemDetailTest() {
        this.createItemTest();

        List<Item> itemList = itemRepository.findByItemDetail("테스트 상품 상세 설명");
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("nativeQuery 사용 상품 데이터 조회")
    public void findByItemDetailByNative() {
        this.createItemTest();

        List<Item> itemList = itemRepository.findByItemDetailByNative("테스트 상품 상세 설명");
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("Querydsl 조회 테스트1")
    public void queryDslTest() {
        this.createItemTest();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em); // 쿼리 동적 생성
        QItem qItem = QItem.item; // Querydsl 사용을 위해 객체 생성
        // SQL문과 유사하게 코드 작성
        JPAQuery<Item> query = queryFactory.selectFrom(qItem).where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
                .where(qItem.itemDetail.like("% " + "테스트 상품 상세 설명" + "%"))
                .orderBy(qItem.price.desc());

        List<Item> itemList = query.fetch(); // 쿼리 결과를 리스트로 반환 .fetch()

        for (Item item : itemList) {
            System.out.println(item.toString());
        }

    }

    public void createItemList2() {
        for (int i = 1; i <= 5; i++) {
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            itemRepository.save(item);
        }

        for (int i = 6; i <= 10; i++) {
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SOLD_OUT);
            item.setStockNumber(0);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            itemRepository.save(item);
        }
    }

    @Test
    @DisplayName("Querydsl 조회 테스트2")
    public void queryDslTest2() {
        this.createItemList2();
        BooleanBuilder booleanBuilder = new BooleanBuilder(); // 쿼리 조건 빌더 생성
        QItem item = QItem.item; // Querydsl 사용을 위해 객체 생성

        String itemDetail = "테스트 상품 상세 설명";
        int price = 10003;
        String itemSellStat = "SELL"; // 조건

        booleanBuilder.and(item.itemDetail.like("%" + itemDetail + "%")); // 조건 추가 (판매 상태가 SELL 일떄만)
        booleanBuilder.and(item.price.gt(price)); // gt -> 인수보다 큰 값 조건 거는거

        if (StringUtils.equals(itemSellStat, ItemSellStatus.SELL)) {
            booleanBuilder.and(item.itemSellStatus.eq(ItemSellStatus.SELL));
        }

        Pageable pageable = PageRequest.of(0, 5); 
        // 데이터 페이징 조회위해 Pageable 객체 생성 (조회 페이지 번호, 페이지당 데이터 갯수)

        Page<Item> itemPagingResult = itemRepository.findAll(booleanBuilder, pageable); // 조건에 맞는 데이터를 Page 객체로 받아옴
        System.out.println("total elements : " + itemPagingResult.getTotalElements());

        List<Item> resultItemList = itemPagingResult.getContent();
        for (Item resultItem : resultItemList) {
            System.out.println(resultItem.toString());
        }
    }
}
