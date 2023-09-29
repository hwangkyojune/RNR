package com.example.Rnr.Member;

import org.junit.jupiter.api.Test;

import java.util.Optional;

public class MyTest {
    @Test
    public void optionalTest(){
        Optional<Member> oMember = Optional.of(new Member());
        if(oMember ==null){
            System.out.println("go");
        }
    }
}
