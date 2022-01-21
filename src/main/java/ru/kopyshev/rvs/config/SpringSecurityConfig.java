package ru.kopyshev.rvs.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kopyshev.rvs.service.UserService;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService service;

    private final PasswordEncoder passwordEncoder;

    public SpringSecurityConfig(UserService service, PasswordEncoder passwordEncoder) {
        this.service = service;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .eraseCredentials(true)
                .userDetailsService(service).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/swagger-ui/", "/swagger-ui/**", "/swagger-resources/**", "/v2/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/rest/restaurants/**").permitAll()
                .and().authorizeRequests().antMatchers("/rest/profile").hasAnyRole("USER", "ADMIN")
                .and().authorizeRequests().antMatchers("/rest/admin/**").hasRole("ADMIN")
                .and().authorizeRequests().antMatchers("/rest/votes").hasRole("USER")
                .and()
                .httpBasic();

        http.csrf().disable().cors().disable();
        http.headers().frameOptions().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

}
