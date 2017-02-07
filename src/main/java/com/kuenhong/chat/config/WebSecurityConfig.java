package com.kuenhong.chat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        	.csrf().disable()
            .formLogin()
                .loginPage("/")
                .defaultSuccessUrl("/chatting")
                .permitAll()
                .and()
            .logout()
            	.logoutSuccessUrl("/")
                .permitAll()
                .and()
            .authorizeRequests()
            	.antMatchers("/existOrNot").permitAll() // check user nickname
            	.antMatchers("/css/**", "/webjars/**").permitAll()
            	.antMatchers("/monitor").hasRole("ADMIN")
            	.anyRequest().authenticated();
    }
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.authenticationProvider(new AuthenticationProvider() {
			
			public boolean supports(Class<?> authentication) {
				return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
			}
			
			public Authentication authenticate(Authentication authentication) throws AuthenticationException {
				UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
				return new UsernamePasswordAuthenticationToken(token.getName(), token.getCredentials(), 
						token.getName().startsWith("admin") ? AuthorityUtils.createAuthorityList("ROLE_ADMIN") : null);
			}
		});
	}
	
}
