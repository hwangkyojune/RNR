package com.example.Rnr.service;

import com.example.Rnr.repository.patient.Patient;
import com.example.Rnr.repository.patient.PatientRepository;
import com.example.Rnr.repository.user.User;
import com.example.Rnr.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PatientService{
    private final UserService userService;
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;

    public void joinAsPatient(JoinDto joinDto){
        User joinedUser = userService.joinUser(joinDto);
        Patient patient = Patient.builder()
                .user(joinedUser).build();

        patientRepository.save(patient);
    }

}
