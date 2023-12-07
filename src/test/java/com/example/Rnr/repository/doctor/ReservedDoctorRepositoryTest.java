package com.example.Rnr.repository.doctor;

import com.example.Rnr.repository.hospital.Hospital;
import com.example.Rnr.repository.hospital.HospitalRepository;
import com.example.Rnr.repository.hospital.HospitalsFactory;
import com.example.Rnr.repository.user.User;
import com.example.Rnr.repository.user.UserFactory;
import com.example.Rnr.repository.user.UserType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.print.Doc;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReservedDoctorRepositoryTest {
    @Autowired
    ReservedDoctorRepository reservedDoctorRepository;
    @Autowired
    HospitalRepository hospitalRepository;
    @Autowired
    DoctorRepository doctorRepository;

    @Test
    public void hospital에_요청한_의사_반환(){
        Hospital hospital = HospitalsFactory.createHospital("aaaa111");
        hospitalRepository.save(hospital);

        UserFactory userFactory = new UserFactory();
        UserType userType = userFactory.createUserType(2);
        List<User> users = userFactory.createBulkUsers(10,userType);
        List<Doctor> doctors = users.stream()
                .map(user->Doctor.builder().user(user).build())
                .collect(Collectors.toList());

        doctorRepository.saveAll(doctors);


        List<ReservedDoctor> reservedDoctors = doctors.stream()
                .map(doctor -> ReservedDoctor.builder().doctor(doctor).hospital(hospital).build())
                .collect(Collectors.toList());

        reservedDoctorRepository.saveAll(reservedDoctors);

        List<ReservedDoctor> foundReservedDoctor = reservedDoctorRepository.findByHospital_Id("aaaa111");
        Assertions.assertThat(foundReservedDoctor.size()).isEqualTo(10);
    }
}