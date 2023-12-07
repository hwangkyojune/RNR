package com.example.Rnr.controller;

import com.example.Rnr.RnrVariable;
import com.example.Rnr.repository.prescription.Prescription;
import com.example.Rnr.service.prescription.PrescriptionCheckDto;
import com.example.Rnr.service.prescription.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class PatientController{
    private final PrescriptionService prescriptionService;
    @RequestMapping("/patient/prescription")
    public String prescriptionRecord(@SessionAttribute(value = "id")String patientId,Model model){
        model.addAttribute("appName", RnrVariable.appName);
        List<Prescription> prescriptions = prescriptionService.findPrescriptionsByPatientId(patientId);
        List<PrescriptionCheckDto> prescriptionCheckDtos = prescriptionService.prescriptionsToCheckDto(prescriptions);

        model.addAttribute("prescriptionCheckDtos",prescriptionCheckDtos);

        return "/patient/prescription";
    }
}
