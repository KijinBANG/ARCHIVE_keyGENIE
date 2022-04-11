package kijin.bang.keygenie.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Log4j2
@Getter
@Setter
@ToString
public class AuthMember extends User implements OAuth2User {
    private String email;
    private String password;
    private String nickname;
    private boolean fromSocial;

    //속성 값을 읽어오기 위한 Map
    private Map<String, Object> attr;

    public AuthMember(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        log.info("1111#$%^&^%$");
        this.email = username;
        this.password = password;
    }

    public AuthMember(String username, String password, Collection<? extends GrantedAuthority> authorities, Map<String, Object> attr, String nickname) {
        super(username, password, authorities);
        setEmail(username);
        setNickname(nickname);
        log.info("2222#$%^&^%$");
        this.attr = attr;
    }

    //모든 속성의 값을 리턴하는 메소드
    @Override
    public Map<String, Object> getAttributes() {
        return this.attr;
    }

    @Override
    public String getName() {
        return null;
    }
}
