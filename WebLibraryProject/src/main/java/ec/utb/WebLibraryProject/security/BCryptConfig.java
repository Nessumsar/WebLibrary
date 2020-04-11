package ec.utb.WebLibraryProject.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//Author: Lukas Rasmussen
@Configuration
public class BCryptConfig {
    //Small but good class. We make this method a Bean so we could inject it where we'd like. This is used to encrypt passwords.

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
