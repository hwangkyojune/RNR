package com.example.Rnr.code;

import com.example.Rnr.Utility;
import com.example.Rnr.repository.doctor.Doctor;
import com.example.Rnr.repository.doctor.DoctorRepository;
import com.example.Rnr.repository.drug.Drug;
import com.example.Rnr.repository.drug.DrugRepository;
import com.example.Rnr.repository.hospital.*;
import com.example.Rnr.repository.patient.Patient;
import com.example.Rnr.repository.patient.PatientRepository;
import com.example.Rnr.repository.prescription.Prescription;
import com.example.Rnr.repository.prescription.PrescriptionFactory;
import com.example.Rnr.repository.prescription.PrescriptionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@SpringBootTest
public class InsertBatchHospital {
    private HospitalRepository hospitalRepository;
    private DrugRepository drugRepository;
    private PatientRepository patientRepository;
    private DoctorRepository doctorRepository;
    private PrescriptionRepository prescriptionRepository;
    private Utility utility = new Utility();
    @Autowired
    private HospitalAmountRepository hospitalAmountRepository;

    @Autowired
    public InsertBatchHospital(HospitalRepository hospitalRepository,DrugRepository drugRepository
    ,PatientRepository patientRepository,DoctorRepository doctorRepository,PrescriptionRepository prescriptionRepository){
        this.hospitalRepository = hospitalRepository;
        this.drugRepository = drugRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.prescriptionRepository = prescriptionRepository;
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void hospitalInsertBatch(){
        for(int i=0;i<50;i++){
            hospitalRepository.saveAll(HospitalsFactory.createHospitalsAsBatch(1000));
        }
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void insertDrug(){
        List<String> drugName = List.of("암페타민","케타민","벤조디아제핀");
        Utility utility = new Utility();
        List<Drug> drugs = drugName.stream()
                .map(name->Drug.builder().drugName(name).description(utility.createRandomString(5)).build())
                .collect(Collectors.toList());

        drugRepository.saveAll(drugs);
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void enrollHospital(){
        List<Doctor> doctors = doctorRepository.findAll();
        List<Hospital> hospitals = hospitalRepository.findAll();

        Random random = new Random();

        for(Doctor doctor:doctors){
            int index = random.nextInt(hospitals.size());
            doctor.setHospital(hospitals.get(index));
        }
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void insertPrescription(){
        Utility utility = new Utility();

        List<Patient> patients = patientRepository.findAll();
        List<Doctor> doctors = doctorRepository.findAll();
        List<Drug> drugs = drugRepository.findAll();

        Random random = new Random(System.currentTimeMillis());

        for(int i=0;i<100;i++){
            List<Prescription> prescriptions = new ArrayList<>();
            for(int j=0;j<1000;j++){
                Patient patient = patients.get(random.nextInt(0, patients.size()));
                Doctor doctor = doctors.get(random.nextInt(0,doctors.size()));
                Drug drug = drugs.get(random.nextInt(0, drugs.size()));

                PrescriptionFactory.createPrescription(drug,doctor,patient);
            }
        }

    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void insertHospitalAmount(){
        Random random = new Random();
        List<Hospital> hospitals = hospitalRepository.findAll();
        List<Drug> drugs = drugRepository.findAll();

        for(Hospital hospital : hospitals){
            int categoryNum = random.nextInt(0, drugs.size());
            for(int i=0;i<categoryNum;i++){
                List<Integer> numbers = utility.randomNumbers(random.nextInt(0,drugs.size()),0,drugs.size());
                List<HospitalAmount> hospitalAmounts = new ArrayList<>();
                for (Integer number : numbers){
                    hospitalAmounts.add(HospitalAmount.builder().hospital(hospital)
                            .drug(drugs.get(number))
                            .amount(random.nextInt(1,10)*100).build());
                }
                hospitalAmountRepository.saveAll(hospitalAmounts);
            }
        }

    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void insertPrescriptionOne(){
        Utility utility = new Utility();

        Patient patients = patientRepository.findById("0001053c-1ccb-4661-bdae-184492b1e2e4").get();
        Doctor doctors = doctorRepository.findById("0215a329-5f93-4658-b473-6a7996124e51").get();
        Drug drugs = drugRepository.findById("프로포폴").get();

        Random random = new Random(System.currentTimeMillis());

        Prescription prescription = PrescriptionFactory.createPrescription(drugs,doctors,patients);

    }
}
