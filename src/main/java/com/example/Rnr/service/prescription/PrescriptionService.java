package com.example.Rnr.service.prescription;

import com.example.Rnr.repository.doctor.Doctor;
import com.example.Rnr.repository.doctor.DoctorRepository;
import com.example.Rnr.repository.drug.Drug;
import com.example.Rnr.repository.drug.DrugRepository;
import com.example.Rnr.repository.patient.Patient;
import com.example.Rnr.repository.patient.PatientRepository;
import com.example.Rnr.repository.prescription.Prescription;
import com.example.Rnr.repository.prescription.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrescriptionService {
    private final PrescriptionRepository prescriptionRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final DrugRepository drugRepository;
    public void prescript(Prescription prescription){
        prescriptionRepository.save(prescription);
    }
    public void prescriptByValue(String patientId,String drugId,int amount,String doctorId){
        Patient patient = patientRepository.findById(patientId).get();
        Doctor doctor = doctorRepository.findById(doctorId).get();
        Drug drug = drugRepository.findById(drugId).get();

        Prescription prescription = Prescription.builder().patient(patient)
                .prescriptionTimeStamp(new Date())
                .drug(drug)
                .doctor(doctor)
                .amount(amount).build();

        prescript(prescription);
    }

    public List<Prescription> findPrescriptionsByPatientId(String patientId){
        List<Prescription> prescriptions = prescriptionRepository.findByPatient_Id(patientId);

        return prescriptions;
    }

    public List<PrescriptionCheckDto> prescriptionsToCheckDto(List<Prescription> prescriptions){
        List<PrescriptionCheckDto> prescriptionCheckDtos = new ArrayList<>();

        for(Prescription  prescription: prescriptions){
            PrescriptionCheckDto prescriptionCheckDto = PrescriptionCheckDto.builder()
                    .drugName(prescription.getDrug().getDrugName())
                    .amount(prescription.getAmount())
                    .date(prescription.getPrescriptionTimeStamp())
                    .doctorId(prescription.getDoctor().getId())
                    .hospitalName(prescription.getDoctor().getHospital().getName())
                    .build();

            prescriptionCheckDtos.add(prescriptionCheckDto);
        }
        return prescriptionCheckDtos;
    }

}
