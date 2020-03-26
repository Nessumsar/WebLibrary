package ec.utb.WebLibraryProject.service;

import ec.utb.WebLibraryProject.data.AppUserRepository;
import ec.utb.WebLibraryProject.entity.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AppUserServiceImpl implements AppUserService, UserDetailsService {
    AppUserRepository appUserRepository;

    @Autowired
    public AppUserServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public AppUser registerAppUser(String firstName, String lastName, String email, String password, LocalDate regDate, boolean isAdmin) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}
