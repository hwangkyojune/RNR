package com.example.Rnr.repository.prescription;

import com.example.Rnr.Utility;
import com.example.Rnr.repository.doctor.Doctor;
import com.example.Rnr.repository.drug.Drug;
import com.example.Rnr.repository.patient.Patient;

import java.util.Random;

public class PrescriptionFactory {
    public static Prescription createPrescription(Drug drug, Doctor doctor, Patient patient){
        Utility utility = new Utility();
        Random random = new Random(System.currentTimeMillis());

        Prescription prescription = new Prescription();
        prescription.setDrug(drug);
        prescription.setDoctor(doctor);
        prescription.setPatient(patient);
        prescription.setPrescriptionTimeStamp(utility.createRandomDate(50,55));
        prescription.setAmount(random.nextInt(1,50)*10);

        return prescription;
    }
}
