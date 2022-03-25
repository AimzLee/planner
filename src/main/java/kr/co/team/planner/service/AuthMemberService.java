package kr.co.team.planner.service;

import kr.co.team.planner.dto.AuthMember;
import kr.co.team.planner.entity.Member;
import kr.co.team.planner.entity.MemberRole;
import kr.co.team.planner.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class AuthMemberService extends DefaultOAuth2UserService {

    //repository for store data
    private final MemberRepository memberRepository;
    //instance for encoding password
    private final PasswordEncoder passwordEncoder;

    //method for store in DB
    private Member saveSocialMember(String email) {
        //check whether the email exists in DB or not?
        Optional<Member> result = memberRepository.findById(email);
        //if it exists then return data
        if(result.isPresent()){
            return result.get();
        }
        String temp = email.substring(0, email.indexOf("@"));
        //unless
        Member member = Member.builder()
                .email(email)
                .password(passwordEncoder.encode(temp))
                .name(temp)
                .fromSocial(true)
                .build();
        member.addMemberRole(MemberRole.USER);
        memberRepository.save(member);
        return member;
    }

    //OAuth 를 이용하여 로그인 한 Member의 정보를 확인하기 위한 method
    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        String clientName = userRequest.getClientRegistration().getClientName();
        log.info("clientName: " + clientName);

        OAuth2User oAuth2User = super.loadUser(userRequest);
        oAuth2User.getAttributes().forEach((k, v) -> {
            log.info(k + ":" + v);
        });

        //if user uses google email when try to login this site
        String email = null;
        if(clientName.trim().toLowerCase().indexOf("google")>=0) {
            email = oAuth2User.getAttribute("email");
        }

        //store in DB
        Member member = saveSocialMember(email);

        //return data that is stored in DB
        AuthMember authMember = new AuthMember(
                member.getEmail(),
                member.getPassword(),
                member.getRoleSet().stream().map(role ->
                        new SimpleGrantedAuthority("ROLE_" + role.name()))
                        .collect(Collectors.toList()),
                oAuth2User.getAttributes()
        );
        return authMember;
    }
}