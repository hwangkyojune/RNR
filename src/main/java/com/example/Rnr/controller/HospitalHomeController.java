package com.example.Rnr.controller;

import com.example.Rnr.RnrVariable;
import com.example.Rnr.service.HospitalService;
import com.example.Rnr.repository.hospital.Hospital;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
public class HospitalHomeController {

    final private HospitalService hospitalService;

    @RequestMapping("/hospital/")
    public String home(Model model,@SessionAttribute(name="hospitalId",required = false)String hospitalId){
        model.addAttribute("appName", RnrVariable.appNameForHospital);

        Hospital hospital = hospitalService.getLoginHospital(hospitalId);
        if (hospitalId != null){
            model.addAttribute("hospitalName",hospital.getName());
        }

        return "hospital/home";
    }
}
