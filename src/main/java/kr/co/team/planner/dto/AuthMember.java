package kr.co.team.planner.dto;

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
    private String name;
    private boolean fromSocial;

    private String password;

    //속성 값을 읽어오기 위한 Data Container
    private Map<String, Object> attr;

    public AuthMember(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        //call the constructor of super class
        super(username, password, authorities);
        this.email = username;
        this.password = password;
    }
    public AuthMember(String username, String password, Collection<? extends GrantedAuthority> authorities, Map<String, Object> attr) {
        //call the constructor of super class
        super(username, password, authorities);
        this.attr = attr;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }
}
