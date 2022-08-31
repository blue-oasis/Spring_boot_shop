package com.shop.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

// Data Transfer Object 데이터 전달용 객체
@Getter
@Setter
public class ItemDto {
    
    private Long id;

    private String itemNm;

    private Integer price;

    private String itemDetail;

    private String sellStatCd;

    private LocalDateTime regTime;

    private LocalDateTime updateTime;

    private Integer categoryId;
    
    private String artist;

}
