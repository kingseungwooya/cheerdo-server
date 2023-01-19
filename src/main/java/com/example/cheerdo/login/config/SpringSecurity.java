package com.example.cheerdo.login.config;

import com.example.cheerdo.login.config.filter.CustomAuthenticationFilter;
import com.example.cheerdo.login.config.filter.CustomAuthorizationFilter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class SpringSecurity extends WebSecurityConfigurerAdapter {


    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/api/token/refresh", "/api/join");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // custom 화
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setUsernameParameter("memberId");
        customAuthenticationFilter.setPasswordParameter("password");
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");


        http
                .csrf().disable().sessionManagement().sessionCreationPolicy(STATELESS)
                .and()
                    .authorizeRequests().antMatchers("/api/login/**", "/api/token/refresh/**", "/api/join/**", "/swagger-ui.html", "/swagger/**",
                "/swagger-resources/**", "/webjars/**", "/v2/api-docs").permitAll()
                .and()
                    .authorizeRequests().antMatchers(GET, "/api/**").hasAnyAuthority("ROLE_USER")
                .and()
                    .authorizeRequests().antMatchers(POST, "/api/**").hasAnyAuthority("ROLE_USER")
                .and()
                    .authorizeRequests().anyRequest().authenticated()
                .and()
                    .addFilter(customAuthenticationFilter)
                    .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
