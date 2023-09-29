package com.example.Rnr.Member;

import com.example.Rnr.mokitotest.MockitoPractice;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository ;

    @Test
    @Transactional()
    @Rollback(false)
    public void testMemberRepository(){
        Member member = new Member();
        member.setPassword("dsc");
        memberRepository.save(member);

    }

    @Test
    public void findByLoginId(){
        LoginDto ld = new LoginDto();

        ld.setLoginId("shane");
        ld.setPassword("1234");
        Optional<Member> oMember = memberRepository.findByLoginId(ld.getLoginId());

        Member member = oMember.get();
        System.out.println(member.getLoginId()+member.getPassword());
    }

    @Test
    public void existsByName(){
        boolean re = memberRepository.existsByName("김개똥");
        System.out.println(re);
    }

}
