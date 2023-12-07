package com.example.Rnr.repository.patient;

import com.example.Rnr.repository.user.User;
import com.example.Rnr.repository.user.UserFactory;
import com.example.Rnr.repository.user.UserRepository;
import com.example.Rnr.repository.user.UserType;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class PatientTest {
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    UserRepository userRepository;
    UserFactory userFactory = new UserFactory();
    @Test
    @Transactional
    @Rollback()
    void 의사번호를_환자번호로_입력하고_db에_저장(){
        UserType userType = userFactory.createUserType(1);
        User user = userFactory.createUser(userType);

//        userRepository.save(user);

//        User foundUser = userRepository.findById(user.getId()).get();

        Patient patient = new Patient();
        patient.setUser(user);
        patientRepository.save(patient);

        Assertions.assertThat(patientRepository.findById(patient.getId()).get().getId())
                .isEqualTo("shane5969");
    }
}