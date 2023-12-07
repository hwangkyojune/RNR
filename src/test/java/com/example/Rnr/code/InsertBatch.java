package com.example.Rnr.code;

import com.example.Rnr.repository.doctor.Doctor;
import com.example.Rnr.repository.doctor.DoctorRepository;
import com.example.Rnr.repository.hospital.Hospital;
import com.example.Rnr.repository.hospital.HospitalRepository;
import com.example.Rnr.repository.patient.Patient;
import com.example.Rnr.repository.patient.PatientRepository;
import com.example.Rnr.repository.user.User;
import com.example.Rnr.repository.user.UserFactory;
import com.example.Rnr.repository.user.UserType;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.print.Doc;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class InsertBatch {
    private UserFactory userFactory= new UserFactory();
    private PatientRepository patientRepository;
    private DoctorRepository doctorRepository;
    @Autowired
    public InsertBatch(PatientRepository patientRepository,DoctorRepository doctorRepository){
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }
    @Test
    @Transactional
    @Rollback(value = false)
    public void patientInsert(){
        UserType userType = userFactory.createUserType(1);
        for(int i=0;i<100;i++){
            List<User> users = userFactory.createBulkUsers(1000,userType);
            List<Patient> patients = users.stream().map(user->Patient.builder().user(user).build())
                    .collect(Collectors.toList());
            patientRepository.saveAll(patients);
        }
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void doctorInsert(){
        UserType userType = userFactory.createUserType(2);
        for(int i=0;i<100;i++){
            List<User> users = userFactory.createBulkUsers(1000,userType);
            List<Doctor> doctors = users.stream().map(user->Doctor.builder().user(user).build())
                    .collect(Collectors.toList());
            doctorRepository.saveAll(doctors);
        }
    }
}
