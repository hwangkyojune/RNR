package com.example.Rnr.repository.hospital;

import com.example.Rnr.repository.drug.Drug;
import jakarta.transaction.Transactional;
import lombok.Setter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class HospitalAmountRepositoryTest {

    @Autowired
    HospitalAmountRepository hospitalAmountRepository;
    @Autowired
    HospitalRepository hospitalRepository;
    @Test
    void 병원의_약물_보유_추가(){
        Hospital hospital = Hospital.builder()
                .id("ahen").pw("vdsvdsvdsw").contactInformation("010-4055-6415").build();

        Drug drug = Drug.builder().drugName("프로포폴").description("수면마취").build();

        HospitalAmount hospitalAmount = HospitalAmount.builder()
                .hospital(hospital).drug(drug).amount(1000).build();

        hospitalAmountRepository.save(hospitalAmount);

        HospitalIdAndDrugName hospitalIdAndDrugName = new HospitalIdAndDrugName();

        hospitalIdAndDrugName.setDrug("프로포폴");
        hospitalIdAndDrugName.setHospital("ahen");

        HospitalAmount foundHospitalAmount = hospitalAmountRepository.findById(hospitalIdAndDrugName).get();

        String drugName = foundHospitalAmount.getDrug().getDrugName();
        Assertions.assertThat(drugName).isEqualTo("프로포폴");
    }

}