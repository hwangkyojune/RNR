package com.example.Rnr.repository.user;

import com.example.Rnr.repository.patient.Patient;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserTypeRepository userTypeRepository;
    UserFactory userFactory = new UserFactory();
    @Test
    void User가_db에_제대로_저장되는지(){
        UserType userType = userFactory.createUserType(1);
        userTypeRepository.save(userType);
        User user = userFactory.createUser(userType);

        userRepository.save(user);
    }


//    @Test
//    void 허용되지_않는_user_type_id를_입력할_떄_예외(){
//        Assertions.assertThatThrownBy(()->{
//           UserType userType = userFactory.createUserType();
//           userType.setUserTypeID(3);
//
//           userTypeRepository.save(userType);
//        }).isInstanceOf(Exception.class);
//    }

}