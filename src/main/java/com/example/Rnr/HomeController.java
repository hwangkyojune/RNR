package com.example.Rnr;

import com.example.Rnr.Member.Member;
import com.example.Rnr.Member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
public class HomeController {
    final private MemberService memberService;
    @RequestMapping("/")
    public String home(Model model, @SessionAttribute(name = "memberId",required = false) Long memberId){
        model.addAttribute("loginType","session_login");
        model.addAttribute("pageName", "RNR");
        Member member = memberService.getLoginMember(memberId);
        if(member != null){
            model.addAttribute("name",member.getName());
        }
        return "home";
    }
}
