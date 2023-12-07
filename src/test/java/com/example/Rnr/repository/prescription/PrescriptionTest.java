package com.example.Rnr.repository.prescription;

import com.example.Rnr.repository.doctor.Doctor;
import com.example.Rnr.repository.drug.Drug;
import com.example.Rnr.repository.drug.DrugsFactory;
import com.example.Rnr.repository.patient.Patient;
import com.example.Rnr.repository.user.User;
import com.example.Rnr.repository.user.UserFactory;
import com.example.Rnr.repository.user.UserType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class PrescriptionTest {

    //patient,drug,date : pk, doctor :fk, amount
    UserFactory userFactory = new UserFactory();

    @Test
    void patient에_대한_prescription을_작성하면_환자의_prescription에_추가되는지(){

        User user = userFactory.createUser();

        Patient patient = new Patient();
        patient.setUser(user);

        Prescription prescription = new Prescription();

        prescription.setPatient(patient);

        Assertions.assertThat(patient.getPrescriptions()).contains(prescription);
    }

    @Test
    void drug에_대한_prescription을_작성하면_drug의_prescription에_추가되는지(){
        Drug drug = DrugsFactory.createDrug("프로포폴");

        Prescription prescription = new Prescription();
        prescription.setDrug(drug);

        Assertions.assertThat(drug.getPrescriptions()).contains(prescription);
    }

    @Test
    void doctor_에_대한_prescription을_작성하면_doctor의_prescription에_추가되는지(){
        User user = userFactory.createUser();

        Doctor doctor = new Doctor();
        doctor.setUser(user);

        Prescription prescription = new Prescription();
        prescription.setDoctor(doctor);

        Assertions.assertThat(doctor.getPrescriptions()).contains(prescription);
    }
}