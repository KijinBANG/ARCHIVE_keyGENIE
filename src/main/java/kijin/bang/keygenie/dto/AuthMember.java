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
    private String nickname;
    private boolean fromSocial;

    private String password;

    //속성 값을 읽어오기 위한 Map
    private Map<String, Object> attr;

    public AuthMember(
            String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.email = username;
        this.password = password;
    }

    public AuthMember(
            String username, String password, Collection<? extends GrantedAuthority> authorities, Map<String, Object> attr) {
        super(username, password, authorities);
        this.attr = attr;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attr;
    }

    @Override
    public String getName() {
        return null;
    }
}
