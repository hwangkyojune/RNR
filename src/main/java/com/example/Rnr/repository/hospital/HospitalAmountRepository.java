package com.example.Rnr.repository.hospital;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HospitalAmountRepository extends JpaRepository<HospitalAmount,HospitalIdAndDrugName> {
    List<HospitalAmount> findByHospital_Id(String id);

    List<HospitalAmount> findByAmount(int amount);

    boolean existsById(HospitalIdAndDrugName hospitalIdAndDrugName);
}
