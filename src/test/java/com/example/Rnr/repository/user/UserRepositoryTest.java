package com.example.Rnr.repository.user;

import com.example.Rnr.repository.patient.Patient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;
    @Test
    void User가_db에_제대로_저장되는지(){
        User user = new User();
        user.setId("shane5969");
        user.setPw("shane5885");
        user.setUserName("황교준");
        user.setMail("shane5969@naver.com");
        user.setLastRNN(1188);
        user.setBirthOfDate(new Date());
        user.setUserTypeId(1);


        userRepository.save(user);

    }

    @Test
    void Patient가_db에_제대로_저장되는지(){
        User user = new User();
        user.setId("shane5969");
        user.setPw("shane5885");
        user.setUserName("황교준");
        user.setMail("shane5969@naver.com");
        user.setLastRNN(1188);
        user.setBirthOfDate(new Date());
        user.setUserTypeId(1);


        userRepository.save(user);

        Patient patient = new Patient();
        patient.setUser(user);


    }

}