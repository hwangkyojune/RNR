package com.example.Rnr.Member;

import com.mysql.cj.log.Log;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class MemberServiceTest {
    @Autowired
    MemberService memberService ;

    @Test
    public void join(){
        JoinDto jd = JoinDto.builder().loginId("hello").password("1234").name("김개똥").build();

        memberService.join(jd);
    }

    @Test
    public void login(){
        LoginDto ld = new LoginDto();
        ld.setLoginId("shane");
        ld.setPassword("1234");

        Member member = memberService.login(ld);
        System.out.println(member.getLoginId()+member.getPassword());

    }
}
