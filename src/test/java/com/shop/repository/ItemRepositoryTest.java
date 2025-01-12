package com.shop.repository;

import com.shop.constant.ItemSellStatus;
import com.shop.entitiy.Item;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Log4j2
class ItemRepositoryTest {
    @Autowired
    ItemRepository itemRepository;

    @Test
    @DisplayName("상품 저장 테스트")
    public void createItemTest(){
        Item item = new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("테스트 상품 상세 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());
        Item savedItem = itemRepository.save(item);
        System.out.println(savedItem.toString());
    }

    @Test
    public void oneTest() {
        // Optional은 null이냐? 아니냐?
        // Optional은 null을 포함하지 않는다. 조회 결과가 있을 경우에는 해당 객체를 감싸고,
        // 결과가 없을 경우에는 빈 상태를 나타내는 컨테이너이다.
        // Optional 객체를 사용하여 조회한 결과를 안전하게 처리할 수 있다.
        // Optional의 orElseThrow 메소드를 사용하여 조회 결과가 없을 경우에는 예외를 발생시킬 수 있다.
        Optional<Item> result = itemRepository.findById(1L);
        Item item = result.orElseThrow();
        log.info(item);
    }

    public void createItemList(){
        for(int i=1;i<=10;i++){
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            Item savedItem = itemRepository.save(item);
        }
    }

    @Test
    @DisplayName("상품명 조회 테스트")
    public void findByItemNmTest(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemNm("테스트 상품1");
        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("가격이 10005보다 큰 값만 추출")
    public void testPrice(){
        List<Item> items = itemRepository.findByPriceGreaterThan(10005);

        for(Item item : items){
            log.info(item.toString());
        }
    }
    
    
    
    @Test
    @DisplayName("상품평 or  상품상세설명 테스트")
    public void findByItemOrItemDetailTest(){
        this.createItemList();
        List<Item> itemList  = itemRepository.findByItemNmOrItemDetail("테스트상품1", "테스트 상품 상세 설명 5");
        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("가격이 특정가격보다 작은상품 조회 테스트")
    public void findByPriceLessThanTest(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByPriceLessThan(10005);
        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("가격 내림차순 조회 테스트")
    public void findByPriceLessThanOrderByPriceDesc(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(10005);
        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("@Query 설명 내림차순 조회 테스트")
    public void findByItemDetailOrderByPriceDesc(){
        List<Item> lists = itemRepository.findByItemDetail("설명");
        lists.forEach((list)-> log.info(list.toString()));
    }


    @Test
    @DisplayName("@Query를 이용한 상품 조회 테스트")
    public void findByItemDetailTest(){
           List<Item> itemList = itemRepository.findByItemDetail("테스트 상품 상세 설명");
           for(Item item : itemList){
               System.out.println(item.toString());
           }
    }

    @Test
    @DisplayName("nativeQuery 속성을 이용한 상품 조회 테스트")
    public void findByItemDetailByNative(){
        List<Item> itemList = itemRepository.findByItemDetailByNative("테스트 상품 상세 설명");
        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }



}




