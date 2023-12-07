package com.example.Rnr.repository.prescription;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrescriptionRepository extends JpaRepository<Prescription,PrescriptionId> {
    public List<Prescription> findByPatient_Id(String patientId);
}
