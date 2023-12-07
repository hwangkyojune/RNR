package com.example.Rnr.controller;

import com.example.Rnr.RnrVariable;
import com.example.Rnr.repository.doctor.Doctor;
import com.example.Rnr.repository.doctor.DoctorRepository;
import com.example.Rnr.repository.doctor.ReservedDoctor;
import com.example.Rnr.repository.hospital.Hospital;
import com.example.Rnr.repository.user.User;
import com.example.Rnr.service.DoctorService;
import com.example.Rnr.service.HospitalService;
import com.example.Rnr.service.IdNameDto;
import com.example.Rnr.service.ReservedDoctorService;
import com.example.Rnr.service.hospitalamount.DrugAmountDto;
import com.example.Rnr.service.hospitalamount.HospitalAmountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class HospitalController {
    private final ReservedDoctorService reservedDoctorService;
    private final HospitalService hospitalService;
    private final DoctorService doctorService;
    private final HospitalAmountService hospitalAmountService;
    @GetMapping("/hospital/enroll")
    public String enrollPage(Model model, @SessionAttribute(name = "hospitalId") String hospitalId){
        model.addAttribute("title","의사 등록");
        model.addAttribute("appName", RnrVariable.appNameForHospital);
        List<ReservedDoctor> reservedDoctors = reservedDoctorService.getReservedDoctors(hospitalId);

        List<Doctor> doctors = reservedDoctors.stream()
                .map(ReservedDoctor::getDoctor)
                .collect(Collectors.toList());

        List<IdNameDto> idNameDtos = doctorService.mapToIdNameDto(doctors);
        System.out.println(idNameDtos.size());

        model.addAttribute("idNameDtosNotEnrolled",idNameDtos);


        Hospital hospital = hospitalService.getHospitalWhenExists(hospitalId);

        List<IdNameDto> idNameDtosAlreadEnrolled = doctorService
                .mapToIdNameDto(hospital.getDoctors());
        System.out.println(idNameDtosAlreadEnrolled.size());

        model.addAttribute("idNameDtosAlreadEnrolled",idNameDtosAlreadEnrolled);

        return "/hospital/enroll";
    }

    @PostMapping("/hospital/enroll")
    public String enroll(@ModelAttribute IdNameDto idNameDto, @RequestParam String action
    ,@SessionAttribute(value = "hospitalId")String hospitalId) {
        ReservedDoctor reservedDoctor = reservedDoctorService
                .findReservedDoctor(idNameDto.getId());
        if(action.equals("enroll")){
            Hospital hospital = hospitalService.getHospitalWhenExists(hospitalId);
            Doctor doctor = reservedDoctor.getDoctor();
            doctor.setHospital(hospital);
        }
        reservedDoctorService.deleteReservedDoctor(reservedDoctor);

        return "redirect:/hospital/enroll";
    }

    @GetMapping("/hospital/amount")
    public String amountPage(@SessionAttribute(value = "hospitalId")String hospitalId
    , Model model){
        model.addAttribute("appName",RnrVariable.appNameForHospital);

        List<DrugAmountDto> drugAmountDtos = hospitalAmountService.getDrugAmounts(hospitalId);
        model.addAttribute("drugAmountDtos",drugAmountDtos);

        return "hospital/amount";
    }

}
