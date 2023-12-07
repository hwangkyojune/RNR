package com.example.Rnr.repository.doctor;

import com.example.Rnr.repository.hospital.Hospital;
import com.example.Rnr.repository.user.User;
import com.example.Rnr.repository.user.UserFactory;

public class DoctorFactory {
    public static Doctor createDoctor(User user, Hospital hospital){
        Doctor doctor = new Doctor();
        doctor.setUser(user);
        doctor.setHospital(hospital);

        return doctor;
    }

    public static Doctor createDoctor(User user){
        Doctor doctor = createDoctor(user,null);

        return doctor;
    }
}
