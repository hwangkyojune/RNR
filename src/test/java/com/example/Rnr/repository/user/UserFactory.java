package com.example.Rnr.repository.user;

import com.example.Rnr.Utility;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Setter
public class UserFactory {
    private String id="shane5969";
    private String pw="shane5885";
    private String name="황교준";
    private String mail="shane5969@naver.com";
    private String lastRNN="1188488";
    private Date birthOfDate=new Date();
    private int userTypeId=UserType.UserTypeProperty.DOCTOR.getUserTypeId();
    private String role=UserType.UserTypeProperty.DOCTOR.getRole();

    public User createUser(UserType userType){
        User user = new User();
        user.setId(id);
        user.setPw(pw);
        user.setUserName(name);
        user.setMail(mail);
        user.setLastRNN(lastRNN);
        user.setBirthOfDate(birthOfDate);
        user.setUserType(userType);

        return user;
    }
    public User createUser(UserType userType,String id){
        Utility utility = new Utility();

        User user = new User();
        user.setId(id);
        user.setPw(utility.createRandomString(10));
        user.setUserName(utility.createRandomString(4));
        user.setMail(utility.createRandomString(5));
        user.setLastRNN(utility.createRandomString(7));
        user.setBirthOfDate(birthOfDate);
        user.setUserType(userType);

        return user;
    }

    public User createUserRandomly(UserType userType){
        String id = UUID.randomUUID().toString();
        return createUser(userType,id);
    }

    public List<User> createBulkUsers(int num,UserType userType){
        List<User> users = Stream.iterate(0,a->a+1).limit(num)
                .map(a->createUserRandomly(userType)).collect(Collectors.toList());

        return users;
    }

    public User createUser(){
        User user = createUser(createUserType(1));

        return user;
    }

    public UserType createUserType(int userTypeId){
        UserType userType = new UserType();
        userType.setUserTypeID(userTypeId);
        userType.setRole(role);

        return userType;
    }
}
