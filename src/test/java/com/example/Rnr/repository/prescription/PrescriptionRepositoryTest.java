package com.example.Rnr.repository.prescription;

import com.example.Rnr.repository.doctor.Doctor;
import com.example.Rnr.repository.doctor.DoctorFactory;
import com.example.Rnr.repository.doctor.DoctorRepository;
import com.example.Rnr.repository.drug.Drug;
import com.example.Rnr.repository.drug.DrugRepository;
import com.example.Rnr.repository.drug.DrugsFactory;
import com.example.Rnr.repository.hospital.Hospital;
import com.example.Rnr.repository.patient.Patient;
import com.example.Rnr.repository.patient.PatientFactory;
import com.example.Rnr.repository.patient.PatientRepository;
import com.example.Rnr.repository.user.User;
import com.example.Rnr.repository.user.UserFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PrescriptionRepositoryTest {
    private final static String patientId = "shsh111";
    private final static String DoctorId = "dsav125";

    @Autowired
    PrescriptionRepository prescriptionRepository;
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    DrugRepository drugRepository;
    @Test
    void test(){
        Drug drug = drugRepository.findById("프로포폴").get();
        Doctor doctor = doctorRepository.findById("sssss111").get();
        Patient patient = patientRepository.findById("ddddd111").get();

        Date date = new Date(2023,12,3,15,5,12);

        Prescription prescription = Prescription.builder()
                .prescriptionTimeStamp(date)
                .patient(patient)
                .doctor(doctor)
                .drug(drug)
                .amount(100).build();

        Prescription prescriptionPersist = prescriptionRepository.save(prescription);

        PrescriptionId prescriptionId = PrescriptionId.builder()
                .patient("sssss111")
                .drug("프로포폴")
                .prescriptionTimeStamp(date).build();

//        Prescription foundPrescription = prescriptionRepository.findById(prescriptionId).get();

        System.out.println(prescriptionPersist.getPrescriptionTimeStamp().getClass().getName());
        System.out.println(date.getClass().getName());
    }

}