package com.example.Rnr.service;

import com.example.Rnr.repository.doctor.Doctor;
import com.example.Rnr.repository.doctor.DoctorRepository;
import com.example.Rnr.repository.hospital.Hospital;
import com.example.Rnr.repository.patient.PatientRepository;
import com.example.Rnr.repository.user.User;
import com.example.Rnr.repository.user.UserRepository;
import com.example.Rnr.repository.user.UserType;
import com.example.Rnr.repository.user.UserTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserTypeRepository userTypeRepository;
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public User createUser(JoinDto joinDto){
        UserType userType = userTypeRepository
                .findById(joinDto.getUserTypeId())
                .get();

        User user =User.builder().id(joinDto.getId())
                .pw(joinDto.getPw())
                .mail(joinDto.getMail())
                .birthOfDate(joinDto.getBirthOfDate())
                .lastRNN(joinDto.getLastRNN())
                .userName(joinDto.getUserName())
                .userType(userType).build();

        return  user;
    }

    protected User joinUser(JoinDto joinDto){

        User user = createUser(joinDto);

        User savedUser = userRepository.save(user);
        return savedUser;
    }

    public boolean checkIdDuplicated(String id){
        return userRepository.existsById(id);
    }

    public User login(IdPwDto idPwDto){
        Optional<User> oUser = userRepository.findById(idPwDto.getId());

        if(oUser.isEmpty()){
            return null;
        }

        User user = oUser.get();
        if(!user.getPw().equals(idPwDto.getPw())){
            return null;
        }

        return user;
    }

    public User getUser(String id){
        if(id==null){
            return null;
        }
        Optional<User> oUser = userRepository.findById(id);
        if(oUser.isEmpty()){
            return null;
        }
        return oUser.get();
    }

    public String getHospitalIdIfDoctor(User user){
        if(user.getUserTypeId()==1){
            return null;
        }
        Doctor doctor = doctorRepository.findById(user.getId()).get();
        Hospital hospital = doctor.getHospital();

        if(hospital==null){
            return null;
        }

        return hospital.getId();
    }

    public List<IdNameDto> mapToIdNameDto(List<User> users){
        List<IdNameDto> idNameDtos = new ArrayList<>();

        for(User user:users){
            IdNameDto idNameDto = IdNameDto.builder().id(user.getId())
                    .name(user.getUserName()).build();
            idNameDtos.add(idNameDto);
        }

        return idNameDtos;
    }
}
