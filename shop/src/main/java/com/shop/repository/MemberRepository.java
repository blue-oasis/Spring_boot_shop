package com.shop.repository;

import com.shop.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    
    Member findByEmail(String email); //중복가입 확인 위해 이메일로 검사

}
