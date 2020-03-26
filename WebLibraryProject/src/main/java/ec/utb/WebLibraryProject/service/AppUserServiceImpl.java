package ec.utb.WebLibraryProject.service;

import ec.utb.WebLibraryProject.data.AppUserRepository;
import ec.utb.WebLibraryProject.data.AppUserRoleRepository;
import ec.utb.WebLibraryProject.dto.CreateAppUserForm;
import ec.utb.WebLibraryProject.entity.AppUser;
import ec.utb.WebLibraryProject.entity.AppUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

//Author: Lukas Rasmussen
@Service
public class AppUserServiceImpl implements AppUserService, UserDetailsService {
    private AppUserRepository appUserRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private AppUserRoleRepository appUserRoleRepository;

    @Autowired
    public AppUserServiceImpl(AppUserRepository appUserRepository, BCryptPasswordEncoder passwordEncoder, AppUserRoleRepository appUserRoleRepository) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.appUserRoleRepository = appUserRoleRepository;
    }

    @Override
    public AppUser registerAppUser(CreateAppUserForm appUserForm) {
       // AppUserRole appUserRole = appUserRoleRepository

        AppUser user = new AppUser(appUserForm.getFirstName(),
                appUserForm.getLastName(),
                appUserForm.getEmail(),
                passwordEncoder.encode(appUserForm.getPassword()),
                LocalDate.now());

        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return null;
    }

    @Override
    public AppUser save(AppUser appUser) {
        return appUserRepository.save(appUser);
    }

    @Override
    public AppUser findById(int userId) {
        Optional<AppUser> userOptional = appUserRepository.findById(userId);
        AppUser appUser = userOptional.get();
        return appUser;
    }

    @Override
    public Optional<AppUser> findByEmail(String email) {
        return appUserRepository.findByEmailIgnoreCase(email);
    }
}
