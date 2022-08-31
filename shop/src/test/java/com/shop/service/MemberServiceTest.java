package com.shop.service;

import com.shop.dto.MemberFromDto;
import com.shop.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional // 테스트 실행 후 롤백 처리로 반복테스트 가능
@TestPropertySource(locations = "classpath:application-test.properties")
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Member creatMember() {
        MemberFromDto memberFromDto = new MemberFromDto();
        memberFromDto.setEmail("test@email.com");
        memberFromDto.setName("홍길동");
        memberFromDto.setAddress("서울시 마포구 합정동");
        memberFromDto.setPassword("1234");
        memberFromDto.setPhone("01012345678");
        return Member.creatMember(memberFromDto, passwordEncoder);
    }

    @Test
    @DisplayName("회원가입 테스트")
    public void saveMemberTest() {
        Member member = creatMember();
        Member savedMember = memberService.saveMember(member);

        assertEquals(member.getEmail(), savedMember.getEmail());
        assertEquals(member.getName(), savedMember.getName());
        assertEquals(member.getAddress(), savedMember.getAddress());
        assertEquals(member.getPassword(), savedMember.getPassword());
        assertEquals(member.getRole(), savedMember.getRole());
        assertEquals(member.getPassword(), savedMember.getPassword());

    }

    @Test
    @DisplayName("중복 회원 가입 테스트")
    public void saveDuplicateMemberTest() {
        Member member1 = creatMember();
        Member member2 = creatMember();
        memberService.saveMember(member1);
                                    // 발생할 예외타입 입력
        Throwable e = assertThrows(IllegalStateException.class, () -> { //예외 처리 테스트
            memberService.saveMember(member2);});

        assertEquals("이미 가입된 회원입니다.", e.getMessage()); //예외 메시지가 예상 결과와 같은지 검증   
        
    }

}