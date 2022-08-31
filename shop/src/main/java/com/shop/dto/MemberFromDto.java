package com.shop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter //회원 정보
public class MemberFromDto {
    
    private String name;

    private String email;

    private String password;

    private String address;

    private String phone;
}
