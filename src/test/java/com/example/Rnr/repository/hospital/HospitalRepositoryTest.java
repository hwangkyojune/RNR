package com.example.Rnr.repository.hospital;


import com.example.Rnr.repository.drug.Drug;
import com.example.Rnr.repository.drug.DrugRepository;
import com.example.Rnr.repository.drug.DrugsFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class HospitalRepositoryTest {
    @Autowired
    HospitalRepository hospitalRepository;
    @Autowired
    DrugRepository drugRepository;
    @Autowired
    HospitalAmountRepository hospitalAmountRepository;

    @Test
    void Hospital가_제대로_저장되는지(){
        Hospital hospital = new Hospital();
        hospital.setId("shane77776");
        hospital.setPw("shane5885");
        hospital.setName("대구사랑병원");
        hospital.setContactInformation("010-4055-6415");

        hospitalRepository.save(hospital);

        Optional<Hospital> hospitalFromDB =
                hospitalRepository.findById("shane77776");

        Assertions.assertThat(hospitalFromDB.get().getName())
                .isEqualTo("대구사랑병원");
    }

    @Test
    @Transactional
    void 병원이_보유한_약물_영속성_컨텍스트에서_조회(){
        Drug drug= DrugsFactory.createDrug("프로포폴");

        Hospital hospital = HospitalsFactory.createHospital("shane5969");
        hospitalRepository.save(hospital);
        drugRepository.save(drug);

        Hospital foundHospital = hospitalRepository.findById("shane5969").get();
        Drug foundDrug = drugRepository.findById("프로포폴").get();

        HospitalAmount hospitalAmount = new HospitalAmount();
        hospitalAmount.setAmount(1000);
        hospitalAmount.setHospital(foundHospital);
        hospitalAmount.setDrug(foundDrug);

        hospitalAmountRepository.save(hospitalAmount);

        foundHospital = hospitalRepository.findById("shane5969").get();

        HospitalAmount foundHospitalAmount = hospitalAmountRepository
                .findById(new HospitalIdAndDrugName("shane5969","프로포폴")).get();

        System.out.println(foundHospitalAmount);

        Assertions.assertThat(foundHospital.getHospitalAmounts().get(0))
                .isEqualTo(foundHospitalAmount);
    }

    @Test
    @Transactional
    void 병원이_보유한_약물_DB에서_조회(){
        System.out.println(hospitalRepository.findById("shane777796").get().getHospitalAmounts().size());

    }


}