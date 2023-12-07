package com.example.Rnr.service;

import com.example.Rnr.repository.doctor.Doctor;
import com.example.Rnr.repository.doctor.DoctorRepository;
import com.example.Rnr.repository.doctor.ReservedDoctor;
import com.example.Rnr.repository.doctor.ReservedDoctorRepository;
import com.example.Rnr.repository.hospital.Hospital;
import com.example.Rnr.repository.hospital.HospitalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservedDoctorService{
    private final ReservedDoctorRepository reservedDoctorRepository;
    private final DoctorRepository doctorRepository;
    private final HospitalRepository hospitalRepository;

    public void enrollDoctor(String doctorId,String hospitalId){
        Doctor doctor = doctorRepository.findById(doctorId).get();
        Hospital hospital = hospitalRepository.findById(hospitalId).get();

        ReservedDoctor reservedDoctor = ReservedDoctor.builder().doctor(doctor)
                .hospital(hospital).build();

        reservedDoctorRepository.save(reservedDoctor);
    }

    public List<ReservedDoctor> getReservedDoctors(String hospitalId){
        return reservedDoctorRepository.findByHospital_Id(hospitalId);
    }

    public ReservedDoctor findReservedDoctor(String doctorId){
        ReservedDoctor reservedDoctor = reservedDoctorRepository.findByDoctor_Id(doctorId);

        return reservedDoctor;
    }

    public void deleteReservedDoctor(ReservedDoctor reservedDoctor){
        reservedDoctorRepository.delete(reservedDoctor);
    }

}
