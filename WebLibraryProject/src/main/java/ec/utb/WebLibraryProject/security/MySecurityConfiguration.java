package ec.utb.WebLibraryProject.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//Author: Lukas Rasmussen & Benjamin Boson
@EnableWebSecurity
public class MySecurityConfiguration extends WebSecurityConfigurerAdapter {
    /*
    EnableWebSecurity is an annotation that makes the program sense this as the security config.
    We make /create/book and /users to be admin-only with .hasAuthority.
    To view /books you need to be logged in as an User. Other than that, a guest can view everything.

    We set /login as the loginUrl as well as email and password as parameters.
    We set /logout as the logoutUrl then invalidate the users session as well as delete the cookies.

    By setting /accessDenied to exceptionHandling,
    we can make sure that the user gets redirected to /accessDenied if the user doesn't have the right to view something.
     */

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authorizeRequests()
                    .antMatchers("/create/book").hasAuthority("ADMIN")
                    .antMatchers("/users-view").hasAuthority("ADMIN")
                    .antMatchers("/books").hasAuthority("USER")
                    .antMatchers("/**").permitAll()
                .and()
                    .csrf().disable()
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
