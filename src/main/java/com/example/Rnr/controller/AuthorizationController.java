package com.example.Rnr.controller;

import com.example.Rnr.RnrVariable;
import com.example.Rnr.repository.hospital.Hospital;
import com.example.Rnr.repository.user.User;
import com.example.Rnr.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthorizationController{
    private final UserService userService;
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final HospitalService hospitalService;
    @GetMapping("/join")
    public String joinPage(Model model){
        model.addAttribute("pageName", "회원가입");
        model.addAttribute("appName",RnrVariable.appName);
        JoinDto jd = new JoinDto();
        model.addAttribute("joinDto",jd);
        return "join";
    }

    @GetMapping("/hospital/join")
    public String hospitalJoinPage(Model model){
        model.addAttribute("pageName","병원 회원가입");
        model.addAttribute("appName",RnrVariable.appNameForHospital);
        HospitalJoinDto hospitalJoinDto = new HospitalJoinDto();
        model.addAttribute("hospitalJoinDto",hospitalJoinDto);
        return "hospital/join";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute JoinDto joinDto, BindingResult bindingResult,Model model){
        model.addAttribute("pageName", "회원가입");
        model.addAttribute("appName",RnrVariable.appName);

        if(userService.checkIdDuplicated(joinDto.getId())){
            bindingResult.addError(new FieldError("JoinDto","id","중복된 아이디가 존재합니다."));
        }

        if(!joinDto.getPwCheck().equals(joinDto.getPw())){
            bindingResult.addError(new FieldError("JoinDto","pwCheck","비밀번호가 다릅니다."));
        }
        if(joinDto.getLastRNN().length()!=7){
            bindingResult.addError(new FieldError("JoinDto","lassRNN","주민번호 형식이 아닙니다."));
        }

        if(bindingResult.hasErrors()){
            return "join";
        }

        if(joinDto.getUserTypeId()==1){
            patientService.joinAsPatient(joinDto);
        }else if(joinDto.getUserTypeId()==2){
            doctorService.joinAsDoctor(joinDto);
        }

        return "redirect:/";
    }

    @PostMapping("/hospital/join")
    public String hospitalJoin(@ModelAttribute HospitalJoinDto hospitalJoinDto,
                               BindingResult bindingResult,Model model){
        model.addAttribute("pageName", "회원가입");
        model.addAttribute("appName",RnrVariable.appNameForHospital);
        if(hospitalService.checkIdExists(hospitalJoinDto.getId())){
            bindingResult.addError(new FieldError("HospitalJoinDto","id","중복된 아이디가 존재합니다."));
        }
        if(!hospitalJoinDto.getPw().equals(hospitalJoinDto.getPw())){
            bindingResult.addError(new FieldError("HospitalJoinDto","pw","같은 비밀번호를 입력해주십시오"));
        }

        if(bindingResult.hasErrors()){
            return "/hospital/join";
        }

        hospitalService.join(hospitalJoinDto);
        return "redirect:/hospital/";
    }

    @GetMapping("/login")
    public String loginPage(Model model){
        model.addAttribute("appName",RnrVariable.appName);
        IdPwDto idPwDto = new IdPwDto();
        model.addAttribute("idPwDto", idPwDto);
        return "login";

    }

    @GetMapping("/hospital/login")
    public String hospitalLoginPage(Model model){
        model.addAttribute("appName",RnrVariable.appNameForHospital);
        IdPwDto idPwDto = new IdPwDto();
        model.addAttribute("idPwDto", idPwDto);
        return "hospital/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute IdPwDto idPwDto, BindingResult bindingResult,
                        HttpServletRequest httpServletRequest, Model model){
        model.addAttribute("appName",RnrVariable.appName);

        User user = userService.login(idPwDto);
        if(user==null){
            bindingResult.reject("loginFail","로그인 아이디 혹은 비밀번호가 틀렸습니다.");
        }
        if(bindingResult.hasErrors()){
            return "/login";
        }

        httpServletRequest.getSession().invalidate();
        HttpSession session = httpServletRequest.getSession(true);

        session.setAttribute("id",user.getId());
        session.setMaxInactiveInterval(1800);

        return "redirect:/";
    }

    @PostMapping("/hospital/login")
    public String hospitalLogin(@ModelAttribute IdPwDto idPwDto, BindingResult bindingResult,
                                HttpServletRequest httpServletRequest, Model model){
        model.addAttribute("appName",RnrVariable.appNameForHospital);

        Hospital hospital = hospitalService.login(idPwDto);
        if(hospital==null){
            bindingResult.reject("loginFail","로그인 아이디 혹은 비밀번호가 틀렸습니다.");
        }
        if(bindingResult.hasErrors()){
            return "hospital/login";
        }

        httpServletRequest.getSession().invalidate();
        HttpSession session = httpServletRequest.getSession(true);

        session.setAttribute("hospitalId",hospital.getId());
        session.setMaxInactiveInterval(1800);

        return "redirect:/hospital/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest httpServletRequest,Model model){
        model.addAttribute("appName",RnrVariable.appName);
        HttpSession session = httpServletRequest.getSession(false);

        if(session!=null){
            session.invalidate();
        }

        return "redirect:/";
    }

    @GetMapping("hospital/logout")
    public String hospitalLogout(HttpServletRequest httpServletRequest,Model model){
        model.addAttribute("appName",RnrVariable.appNameForHospital);
        HttpSession session = httpServletRequest.getSession(false);

        if(session!=null){
            session.invalidate();
        }

        return "redirect:/hospital/";
    }

}
