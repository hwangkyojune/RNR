package com.example.Rnr.code;

import org.junit.jupiter.api.Test;

import java.util.UUID;

public class TestCode {
    @Test
    void UUID테스트(){
        String uuid = UUID.randomUUID().toString();
        System.out.println(uuid);
    }
}
