package com.example.Rnr.repository.doctor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservedDoctorRepository extends JpaRepository<ReservedDoctor,ReservedDoctorId> {
    public List<ReservedDoctor> findByHospital_Id(String id);

    public ReservedDoctor findByDoctor_Id(String doctorId);
}
