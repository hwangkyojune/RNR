package com.example.Rnr.repository.prescription;


import com.example.Rnr.repository.doctor.Doctor;
import com.example.Rnr.repository.drug.Drug;
import com.example.Rnr.repository.patient.Patient;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(PrescriptionId.class)
public class Prescription {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="patient_id",referencedColumnName = "id")
    private Patient patient;

    public void setPatient(Patient patient){
        this.patient = patient;

        if(!patient.getPrescriptions().contains(this)){
            patient.addPrescription(this);
        }
    }

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="drug_name",referencedColumnName = "drug_name")
    private Drug drug;

    public void setDrug(Drug drug){
        this.drug = drug;

        if(!drug.getPrescriptions().contains(this)){
            drug.addPrescription(this);
        }
    }

    @Id
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="prescription_time_stamp")
    private Date prescriptionTimeStamp;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="doctor_id",referencedColumnName = "id")
    private Doctor doctor;

    public void setDoctor(Doctor doctor){
        this.doctor = doctor;

        if(!doctor.getPrescriptions().contains(this)){
            doctor.addPrescription(this);
        }
    }

    private int amount;
}
