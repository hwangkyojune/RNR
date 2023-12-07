package com.example.Rnr.repository.patient;

import com.example.Rnr.repository.user.User;
import com.example.Rnr.repository.user.UserFactory;
import com.example.Rnr.repository.user.UserType;

public class PatientFactory {
    public static Patient createPatient(User user){

        Patient patient = new Patient();
        patient.setUser(user);

        return patient;
    }

}
