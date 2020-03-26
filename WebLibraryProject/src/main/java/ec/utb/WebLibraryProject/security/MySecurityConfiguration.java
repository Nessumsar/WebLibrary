package ec.utb.WebLibraryProject.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

//Author: Lukas Rasmussen
public class MySecurityConfiguration extends WebSecurityConfigurerAdapter {
    AppUserPrincipal appUserPrincipal;

    PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authorizeRequests()
                    .antMatchers("").hasAuthority("ADMIN")
                    .antMatchers("").permitAll()
                    .antMatchers("").permitAll()
                .and()
                    .formLogin()
                        .usernameParameter("")
                        .loginPage("")
                        .loginProcessingUrl("")
                        .permitAll()
                .and()
                    .logout()
                    .logoutUrl("")
                    .logoutSuccessUrl("");
    }
}
