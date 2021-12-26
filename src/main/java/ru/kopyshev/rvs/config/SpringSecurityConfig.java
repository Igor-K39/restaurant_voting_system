package ru.kopyshev.rvs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.DelegatingFilterProxy;
import ru.kopyshev.rvs.service.UserService;

import static ru.kopyshev.rvs.config.ApplicationProperties.SECURITY_FILTER_NAME;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService service;

    public SpringSecurityConfig(UserService service) {
        this.service = service;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .eraseCredentials(true)
                .userDetailsService(service).passwordEncoder(passwordEncoder());
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

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DelegatingFilterProxy delegatingFilterProxy() {
        return new DelegatingFilterProxy(SECURITY_FILTER_NAME);
    }
}
