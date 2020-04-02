package ec.utb.WebLibraryProject.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//Author: Lukas Rasmussen & Benjamin Boson
@EnableWebSecurity
public class MySecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authorizeRequests()
                    .antMatchers("/create/book").hasAuthority("ADMIN")
                    .antMatchers("/users").hasAuthority("ADMIN")
                    .antMatchers("/books").hasAuthority("USER")
                    .antMatchers("/**").permitAll()
                .and()
                    .formLogin()
                    .loginPage("/login") //Get
                    .loginProcessingUrl("/login")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .permitAll()
                .and()
                    .logout()
                    .logoutUrl("/logout")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .logoutSuccessUrl("/login?logout")
                .and()
                    .exceptionHandling()
                    .accessDeniedPage("/accessDenied");
    }
}
