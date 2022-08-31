package com.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shop.dto.ItemDto;

import java.time.LocalDateTime;

@Controller
@RequestMapping(value = "/thymeleaf") // 요청 주소 매핑
public class ThymeleafExController {

    @GetMapping(value = "/ex01")
    public String thymeleafExample01(Model model) {
        model.addAttribute("data", "타임리프 예제 입니다.");
        return "thymeleafEx/thymeleafEx01"; // templates 폴더 기준 뷰의 위치와 이름 반환
        // localhost 80임
    }

    @GetMapping(value = "/ex02")
    public String thymeleafExample02(Model model) {
        ItemDto itemDto = new ItemDto();
        itemDto.setItemDetail("상품 상세 설명");
        itemDto.setItemNm("테스트 상품1");
        itemDto.setPrice(100000);
        itemDto.setRegTime(LocalDateTime.now());
        itemDto.setArtist("artist");
        
        model.addAttribute("itemDto", itemDto);
        return "thymeleafEx/thymeleafEx02";

    }

}
