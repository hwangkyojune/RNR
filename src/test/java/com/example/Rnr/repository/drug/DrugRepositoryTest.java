package com.example.Rnr.repository.drug;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class DrugRepositoryTest {
    @Autowired DrugRepository drugRepository;

    @Test
    @Transactional
    @Rollback
    void 약물이_제대로_저장되는지(){
        Drug drug = Drug.builder().drugName("프로포폴")
                .description("수면 마취를 위한 약").build();

        drugRepository.save(drug);
    }

}