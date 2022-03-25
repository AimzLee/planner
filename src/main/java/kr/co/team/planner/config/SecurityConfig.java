package kr.co.team.planner.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Log4j2
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder passwordEncoder(){
        //암호화해서 평문과 비교는 할 수 있지만, 복호화는 할 수 없는 클래스의 인스턴스를 생성해서 리턴
        return new BCryptPasswordEncoder();
    }

    //설정 method - Jpa 를 사용하면 필요 없음!
    /*
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        //메모리에 유저 생성
        auth.inMemoryAuthentication()
                .withUser("user1")
                .password("$2a$10$bR2rWdpBDabTyC4F8PZ41uHtbNIgyhztadk9erpt1SRcSOpjJ53aq")
                .roles("USER");
    }
     */

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //'/' 은 로그인 여부 와 상관없이 접근 가능
        //sample/member/main 는 USER 권한이 있어야 만 접근 가능
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                //.antMatchers("/security/member/main").hasRole("USER")
                .antMatchers("/board/register").hasRole("USER")
                .antMatchers("/board/read").hasRole("USER")
                .antMatchers("/board/modify").hasRole("USER")
                .antMatchers("/security/admin/management").hasRole("ADMIN");

        //권한이 없는 경우 로그인 페이지로 이동
        http.formLogin();

        //별도의 로그인페이지를 이용하고자 하면 다음의 method를 사용
        /*
        http.formLogin()
            .loginPage("/customlogin")
            .loginProcessingUrl("/login");
         */

        //CSRF 토큰 비교하는 작업을 수행하지 않음
        http.csrf().disable();

        http.oauth2Login();

    }
}
