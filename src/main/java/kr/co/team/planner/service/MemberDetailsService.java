package kr.co.team.planner.service;

import kr.co.team.planner.dto.AuthMember;
import kr.co.team.planner.entity.Member;
import kr.co.team.planner.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadByUserName: " + username);

        //get Data from DB by using 'username'
        Member member = memberRepository.findByEmail(username, false).get();


        //create instance to proceed with the authorization process
        AuthMember authMember = new AuthMember(
                member.getEmail(),
                member.getPassword(),
                member.getRoleSet().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                        .collect(Collectors.toSet())
        );
        authMember.setName(member.getName());
        authMember.setFromSocial(member.isFromSocial());
        log.info(member);
        log.info(authMember);
        return authMember;
    }
}
