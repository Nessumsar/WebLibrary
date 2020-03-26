package ec.utb.WebLibraryProject.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

public class MySecurityConfiguration extends WebSecurityConfigurerAdapter {

    //AppUser uppfylla krav. UserDetails (interface) Skapa Principal.

    // Skapa en bean som implementerar UserDetailsService - viktig del när man loggar in

    // Skapa en bean av en password krypterare

    //Definera funktionalitet i MySecurityConfiguration



    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authorizeRequests()
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers("/users/**").permitAll()
                .antMatchers("/**").permitAll()
                .and();
    }
}
