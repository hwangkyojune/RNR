package com.example.Rnr.Member;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    /***
     * 회원가입 메소드,
     * post를 통해 전달 받은 데이터를 회원 정보 객체인 Member로 mapping하고,
     * database에 저장
     * @param jd
     */
    public void join(JoinDto jd) {
        Member member = Member.builder().
                loginId(jd.getLoginId()).
                password(jd.getPassword()).
                name(jd.getName()).build();
        memberRepository.save(member);
    }

    /***
     * id 중복 체크
     * @param loginId
     * @return
     */
    public boolean checkLoginIdDuplicated(String loginId) {
        return memberRepository.existsByLoginId(loginId);
    }

    public boolean checkNameDuplicated(String name) {
        return memberRepository.existsByName(name);
    }

    public Member login(LoginDto ld){
        Optional<Member> oMember = memberRepository.findByLoginId(ld.getLoginId());

        //아이디와 일치하는 멤버가 없으면 null return
        if(oMember.isEmpty()){
            return null;
        }

        Member member = oMember.get();
        //비밀번호와 일치하는지 검사
        if(!member.getPassword().equals(ld.getPassword())){
            return null;
        }

        return member;
    }
    public Member getLoginMember(Long userId){
        if(userId==null){
            return null;
        }
        Optional<Member> optionalMember = memberRepository.findById(userId);
        if(optionalMember.isEmpty()){
            return null;
        }
        return optionalMember.get();
    }
}
