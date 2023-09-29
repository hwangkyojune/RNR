package com.example.Rnr.Member;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinDto {
    private String loginId;
    private String password;
    private String passwordCheck;
    private String name;

}
