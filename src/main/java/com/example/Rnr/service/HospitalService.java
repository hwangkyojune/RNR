package com.example.Rnr.service;

import com.example.Rnr.repository.doctor.ReservedDoctor;
import com.example.Rnr.repository.doctor.ReservedDoctorRepository;
import com.example.Rnr.repository.hospital.Hospital;
import com.example.Rnr.repository.hospital.HospitalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class HospitalService {
    private final HospitalRepository hospitalRepository;
    private final ReservedDoctorRepository reservedDoctorRepository;

    public void join(HospitalJoinDto hospitalJoinDto){
        Hospital hospital = Hospital.builder()
                .id(hospitalJoinDto.getId())
                .pw(hospitalJoinDto.getPw())
                .name(hospitalJoinDto.getName())
                .contactInformation(hospitalJoinDto.getContactInfo()).build();

        hospitalRepository.save(hospital);
    }

    public boolean checkIdExists(String id){
        return hospitalRepository.existsById(id);
    }

    public Hospital getHospitalWhenExists(String id){
        Optional<Hospital> oHospital = hospitalRepository.findById(id);
        if(oHospital.isEmpty()){
            return null;
        }
        Hospital hospital = oHospital.get();
        return hospital;
    }
    public Hospital login(IdPwDto idPwDto){
        Optional<Hospital> oHospital = hospitalRepository.findById(idPwDto.getId());

        if(oHospital.isEmpty()){
            return null;
        }
        Hospital hospital = oHospital.get();

        if(!idPwDto.getPw().equals(hospital.getPw())){
            return null;
        }

        return hospital;
    }

    public Hospital getLoginHospital(String id){
        if(id==null){
            return null;
        }
        Optional<Hospital> oHospital = hospitalRepository.findById(id);
        if(oHospital.isEmpty()){
            return null;
        }
        return oHospital.get();

    }

    public List<ReservedDoctor> getReservedDoctors(String id){
        List<ReservedDoctor> reservedDoctors = reservedDoctorRepository.findByHospital_Id(id);

        return reservedDoctors;
    }


}
