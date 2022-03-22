package kijin.bang.keygenie.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberDTO {
    private String email;
    private String password;
    private String nickname;
    private String birthday;
}
