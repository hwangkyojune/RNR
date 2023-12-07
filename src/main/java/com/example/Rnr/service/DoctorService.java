package com.example.Rnr.service;

import com.example.Rnr.repository.doctor.Doctor;
import com.example.Rnr.repository.doctor.DoctorRepository;
import com.example.Rnr.repository.doctor.ReservedDoctor;
import com.example.Rnr.repository.doctor.ReservedDoctorRepository;
import com.example.Rnr.repository.hospital.Hospital;
import com.example.Rnr.repository.hospital.HospitalAmount;
import com.example.Rnr.repository.hospital.HospitalRepository;
import com.example.Rnr.repository.user.User;
import com.example.Rnr.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class DoctorService {
    private final UserService userService;
    private final DoctorRepository doctorRepository;
    private final HospitalRepository hospitalRepository;
    private final UserRepository userRepository;
    private final ReservedDoctorRepository reservedDoctorRepository;

    public void joinAsDoctor(JoinDto joinDto){
        User joinedUser = userService.joinUser(joinDto);
        Doctor doctor = Doctor.builder()
                .user(joinedUser).build();

        doctorRepository.save(doctor);
    }


    public ReservedDoctor reserve(String doctorId,String hospitalId){
        Optional<Hospital> optionalHospital = hospitalRepository.findById(hospitalId);

        if(optionalHospital.isEmpty()){
            return null;
        }
        Hospital hospital = optionalHospital.get();
        Doctor doctor = doctorRepository.findById(doctorId).get();

        ReservedDoctor reservedDoctor = ReservedDoctor.builder()
                .hospital(hospital)
                .doctor(doctor)
                .build();

        ReservedDoctor savedReservedDoctor = reservedDoctorRepository.save(reservedDoctor);

        return savedReservedDoctor;
    }

    public List<IdNameDto> mapToIdNameDto(List<Doctor> doctors){
        List<User> users = doctors.stream()
                .map(Doctor::getUser).collect(Collectors.toList());

        return userService.mapToIdNameDto(users);
    }

    public Hospital getHospital(String doctorId){
        Optional<Doctor> optionalDoctor= doctorRepository.findById(doctorId);
        if(optionalDoctor.isEmpty()){
            return null;
        }
        Doctor doctor = optionalDoctor.get();

        Hospital hospital = doctor.getHospital();

        if(hospital==null){
            return null;
        }
        return hospital;
    }

    public Doctor findDoctor(String doctorId){
        Optional<Doctor> optionalDoctor= doctorRepository.findById(doctorId);
        if(optionalDoctor.isEmpty()){
            return null;
        }
        Doctor doctor = optionalDoctor.get();
        return doctor;
    }

    public String getHospitalId(String doctorId){
        String hospitalId = getHospital(doctorId).getId();
        return hospitalId;
    }


}
