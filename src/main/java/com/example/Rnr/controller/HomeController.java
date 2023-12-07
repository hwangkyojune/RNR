package com.example.Rnr.controller;

import com.example.Rnr.RnrVariable;
import com.example.Rnr.service.UserService;
import com.example.Rnr.repository.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
public class HomeController {
    final private UserService userService;
    @RequestMapping("/")
    public String home(Model model, @SessionAttribute(name = "id",required = false) String userId){
        model.addAttribute("appName", RnrVariable.appName);

        User user = userService.getUser(userId);
        if(user != null){
            model.addAttribute("name",user.getUserName());
            model.addAttribute("userTypeId",user.getUserTypeId());
            model.addAttribute("hospital_id",userService.getHospitalIdIfDoctor(user));
        }


        return "home";
    }
}
