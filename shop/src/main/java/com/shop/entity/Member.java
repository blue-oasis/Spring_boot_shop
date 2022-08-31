package com.shop.entity;

import com.shop.constant.Role;
import com.shop.dto.MemberFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name="member")
@Getter @Setter
@ToString
public class Member {
    
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String address;

    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role;
// 회원생성 메소드
    public static Member creatMember(MemberFormDto memberFromDto, PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setName(memberFromDto.getName());
        member.setEmail(memberFromDto.getEmail());
        member.setAddress(memberFromDto.getAddress());
        member.setPhone(memberFromDto.getPhone());
        String password = passwordEncoder.encode(memberFromDto.getPassword()); //비밀번호 암호화
        member.setPassword(password);
        member.setRole(Role.USER);
        return member;
    }

}
