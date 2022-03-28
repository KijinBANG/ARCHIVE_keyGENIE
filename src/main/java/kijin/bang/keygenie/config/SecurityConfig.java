package kijin.bang.keygenie.config;

import kijin.bang.keygenie.service.MemberOAuthUserService;
import kijin.bang.keygenie.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@Log4j2
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    private final MemberOAuthUserService memberOAuthUserService;
//    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /* test 를 위한 가상의 User 를 메모리에 생성하는 코드
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user1")
                .password("$2a$10$bR2rWdpBDabTyC4F8PZ41uHtbNIgyhztadk9erpt1SRcSOpjJ53aq")
                .roles("MEMBER");
    }
     */

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/sample/all").permitAll()
//                .antMatchers("/sample/guest").hasRole("GUEST")
//                .antMatchers("/sample/member").hasRole("MEMBER")
//                .antMatchers("/sample/admin").hasRole("ADMIN");
//
//        http.formLogin()
//                .loginPage("/members/login")
//                .defaultSuccessUrl("/")
//                .usernameParameter("email")
//                .failureUrl("/members/login/error")
//                .and()
//                .logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
//                .logoutSuccessUrl("/");
//
//        //REST API 를 사용하기 때문에, Cross Site Request Forgery 를 방지하기 위한 장치를 사용하지 않아도 보안에 문제가 없음!
//        http.csrf()
//                .ignoringAntMatchers("/register");
        http
            .authorizeRequests()
                .antMatchers("/sample/all").permitAll()
                .antMatchers("/sample/guest/**").hasRole("GUEST")
                .antMatchers("/spring/board/**").hasRole("GUEST")
                .antMatchers("/sample/member").hasRole("MEMBER")
                .antMatchers("/sample/admin").hasRole("ADMIN")
            .and()
                .formLogin()
                .loginPage("/members/login")
                .defaultSuccessUrl("/")
                .usernameParameter("email")
                .failureUrl("/members/login/error")
            .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/")
            .and()
                .exceptionHandling()
                .accessDeniedPage("/access-denied")
            .and()
                .oauth2Login();
//                .userInfoEndpoint()
//                .userService(memberOAuthUserService);

        http.csrf().disable();
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsServiceImpl)
//                .passwordEncoder(passwordEncoder());
//    }

}
