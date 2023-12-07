package com.example.Rnr.Member;

import com.example.Rnr.Member.JoinDto;
import com.example.Rnr.Member.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @RequestMapping("/join")
    public String joinPage(Model model){
        model.addAttribute("loginType", "session-login");
        model.addAttribute("pageName", "세션 로그인");
        JoinDto jd = new JoinDto();
        model.addAttribute("joinDto",jd);
        return "join";
    }

    /***
     * post를 통해 요청 받은 데이터를 통해 회원가입
     * @param jd
     * @param bindingResult
     * @param model
     * @return
     */
    @PostMapping("/join")
    public String join(@ModelAttribute JoinDto jd, BindingResult bindingResult, Model model){
        model.addAttribute("loginType", "session-login");
        model.addAttribute("pageName", "RNR");

        if(memberService.checkLoginIdDuplicated(jd.getLoginId())){
            bindingResult.addError(new FieldError("JoinDto","loginId","중복된 아이디가 존재합니다."));
        }
        if(memberService.checkLoginIdDuplicated(jd.getName())){
            bindingResult.addError(new FieldError("JoinDto","name","중복된 이름이 존재합니다."));
        }
        if(!jd.getPassword().equals(jd.getPasswordCheck())){
            bindingResult.addError((new FieldError("JoinDto","passwordCheck","비밀번호가 다릅니다.")));
        }

        if(bindingResult.hasErrors()){
            return "join";
        }



        memberService.join(jd);
        return "redirect:/";
    }

    @RequestMapping("/login")
    public String loginPage(Model model){
        model.addAttribute("loginType","session-Login");
        model.addAttribute("pageName","RNR");
        LoginDto ld = new LoginDto();
        model.addAttribute("loginDto",ld);
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginDto ld, BindingResult bindingResult,
                        HttpServletRequest httpServletRequest,Model model){
        model.addAttribute("loginType","session-Login");
        model.addAttribute("pageName","RNR");
        Member member = memberService.login(ld);

        if(member == null){
            bindingResult.reject("loginFail","로그인 아이디 혹은 비밀번호가 틀렸습니다.");
        }
        if(bindingResult.hasErrors()){
            return "login";
        }
        // 로그인 성공 => 세션 생성

        // 세션을 생성하기 전에 기존의 세션 파기
        httpServletRequest.getSession().invalidate();
        HttpSession session = httpServletRequest.getSession(true);  // Session이 없으면 생성
        // 세션에 userId를 넣어줌
        session.setAttribute("memberId", member.getId());
        session.setMaxInactiveInterval(1800);

        return "redirect:/";

        
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest httpServletRequest,Model model){
        model.addAttribute("pageName","RNR");
        HttpSession session = httpServletRequest.getSession(false);
        if(session != null){
            session.invalidate();
        }

        return "redirect:/";
    }
    @GetMapping("/info")
    public String info(@SessionAttribute(name="memberId",required = false) Long memberId, Model model){
        model.addAttribute("pageName","RNR");
        Member member = memberService.getLoginMember(memberId);

        if(member==null){
            return "redirect:/";
        }

        model.addAttribute("member",member);


        return "info";
    }


}
