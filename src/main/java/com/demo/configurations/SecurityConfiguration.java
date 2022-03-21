package com.demo.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	
@Autowired
    private UsersDetailsService usersDetailsService;
   
	
    @Bean
    public BCryptPasswordEncoder bcrypt() {
    	return new BCryptPasswordEncoder();
    }
    
    private PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();  
        return encoder;
    }
    //authorisation
    //jwt filters added here
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http.csrf().disable();
       http.headers().frameOptions().disable();
    	http.authorizeRequests()

        .antMatchers("/h2-console").permitAll()
                .antMatchers("/api/signup").permitAll()
                .antMatchers("/api/test").authenticated()
                .and()
                .addFilter(new UserNameAndPasswordAuthenticationFilter(authenticationManager()))
              .addFilter(new AuthorizationFilter(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usersDetailsService).passwordEncoder(passwordEncoder());
    }
    
    
}
