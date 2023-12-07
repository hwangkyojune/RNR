package com.example.Rnr.controller;

import com.example.Rnr.RnrVariable;
import com.example.Rnr.dto.EnrollDto;
import com.example.Rnr.repository.doctor.ReservedDoctorRepository;
import com.example.Rnr.repository.hospital.Hospital;
import com.example.Rnr.repository.hospital.HospitalAmount;
import com.example.Rnr.repository.hospital.HospitalIdAndDrugName;
import com.example.Rnr.repository.prescription.Prescription;
import com.example.Rnr.repository.user.User;
import com.example.Rnr.service.*;
import com.example.Rnr.service.hospitalamount.DrugAmountDto;
import com.example.Rnr.service.hospitalamount.HospitalAmountService;
import com.example.Rnr.service.prescription.PrescriptionCheckDto;
import com.example.Rnr.service.prescription.PrescriptionDto;
import com.example.Rnr.service.prescription.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class DoctorController {
    private final HospitalService hospitalService;
    private final DoctorService doctorService;
    private final ReservedDoctorRepository reservedDoctorRepository;
    private final ReservedDoctorService reservedDoctorService;
    private final HospitalAmountService hospitalAmountService;
    private final UserService userService;
    private final PrescriptionService prescriptionService;
    @RequestMapping("/doctor/enroll")
    public String enrollPage(Model model, @SessionAttribute(name="id")String id){
        model.addAttribute("appName", RnrVariable.appName);
        EnrollDto enrollDto = new EnrollDto();
        model.addAttribute("enrollDto",enrollDto);
        return "/doctor/enroll";
    }

    @PostMapping("/doctor/enroll")
    public String enroll(@ModelAttribute EnrollDto enrollDto, BindingResult bindingResult
            , Model model, @SessionAttribute(name="id")String id){
        model.addAttribute("appName",RnrVariable.appName);
        String hospitalId = enrollDto.getHospitalId();
        if(!hospitalService.checkIdExists(hospitalId)){
            bindingResult.addError(new FieldError("EnrollDto","hospitalId","일치하는 병원이 없습니다."));
        }
        if(bindingResult.hasErrors()){
            return "/doctor/enroll";
        }

        reservedDoctorService.enrollDoctor(id,hospitalId);

        return "redirect:/";
    }

    @GetMapping("/doctor/prescription")
    public String prescriptionPage(@SessionAttribute(value = "id")String doctorId
    ,Model model){
        model.addAttribute("appName",RnrVariable.appName);
        Hospital hospital = doctorService.getHospital(doctorId);
        List<DrugAmountDto> drugAmountDtos = hospitalAmountService.getDrugAmounts(hospital.getId());
        model.addAttribute("drugAmountDtos",drugAmountDtos);

        PrescriptionDto prescriptionDto = new PrescriptionDto();
        model.addAttribute("prescriptionDto",prescriptionDto);

        return "doctor/prescription";
    }

    @PostMapping("/doctor/prescription")
    public String prescription(@SessionAttribute(value = "id")String doctorId,Model model
    ,@ModelAttribute PrescriptionDto prescriptionDto,BindingResult bindingResult){
        model.addAttribute("appName",RnrVariable.appName);
        Hospital hospital = doctorService.getHospital(doctorId);
        List<DrugAmountDto> drugAmountDtos = hospitalAmountService.getDrugAmounts(hospital.getId());
        model.addAttribute("drugAmountDtos",drugAmountDtos);
        model.addAttribute("appName",RnrVariable.appName);

        String hospitalId = doctorService.getHospitalId(doctorId);
        HospitalIdAndDrugName hospitalIdAndDrugName = HospitalIdAndDrugName.builder()
                .hospital(hospitalId).drug(prescriptionDto.getDrugName()).build();

        User user = userService.getUser(prescriptionDto.getPatientId());
        if (user == null){
            bindingResult.addError((new FieldError("PrescriptionDto","patientId","해당 환자가 없습니다.")));
            return "/doctor/prescription";
        }
        if(!user.getPw().equals(prescriptionDto.getPatientPw())){
            bindingResult.addError((new FieldError("PrescriptionDto","patientPw","해당 환자와 비밀번호가 일치하지 않습니다.")));
            return "/doctor/prescription";
        }
        if(!hospitalAmountService.doHospitalHaveDrug(hospitalIdAndDrugName)){
            bindingResult.addError(new FieldError("PrescriptionDto","drugName","해당 약물을 보유하고 있지 않습니다."));
            return "/doctor/prescription";
        }

        HospitalAmount hospitalAmount = hospitalAmountService
                .findHospitalAmount(hospitalIdAndDrugName);

        if(hospitalAmount.getAmount()<prescriptionDto.getAmount()){
            bindingResult.addError(new FieldError("PrescriptionDto","amount","처방량보다 보유량이 많습니다."));
            return "/doctor/prescription";
        }

        hospitalAmount.subtractAmount(prescriptionDto.getAmount());

        prescriptionService.prescriptByValue(prescriptionDto.getPatientId(),prescriptionDto.getDrugName()
        , prescriptionDto.getAmount(),doctorId);

        return "redirect:/doctor/prescription";
    }

    @RequestMapping("/doctor/amount")
    public String amount(@SessionAttribute(value = "id")String doctorId,Model model){
        Hospital hospital = doctorService.getHospital(doctorId);
        List<DrugAmountDto> drugAmountDtos = hospitalAmountService.getDrugAmounts(hospital.getId());
        model.addAttribute("drugAmountDtos",drugAmountDtos);

        return "/doctor/amount";
    }

    @RequestMapping("/doctor/check")
    private String check(@SessionAttribute(value = "id")String doctorId,Model model){
        model.addAttribute("appName",RnrVariable.appName);
        IdPwDto idPwDto = new IdPwDto();
        model.addAttribute("idPwDto",idPwDto);

        return "/doctor/check";
    }

    @RequestMapping("/doctor/checkPre")
    private String checkPrescription(@SessionAttribute(value="id")String doctorId,Model model
    ,@ModelAttribute IdPwDto idPwDto,BindingResult bindingResult){
        model.addAttribute("appName",RnrVariable.appName);
        User user = userService.getUser(idPwDto.getId());
        if(user==null){
            bindingResult.addError(new FieldError("IdPwDto","id","해당하는 환자가 없습니다."));
            return "/doctor/check";
        }
        if(!user.getPw().equals(idPwDto.getPw())){
            bindingResult.addError(new FieldError("IdPwDto","pw","환자의 비밀번호가 없습니다."));
            return "/doctor/check";
        }
        List<Prescription> prescriptions = prescriptionService
                .findPrescriptionsByPatientId(user.getId());

        model.addAttribute("patientName",user.getUserName());

        List<PrescriptionCheckDto> prescriptionCheckDtos = prescriptionService.prescriptionsToCheckDto(prescriptions);

        model.addAttribute("prescriptionCheckDtos",prescriptionCheckDtos);

        return "/doctor/checkPre";
    }
}
