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

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

//Author: Lukas Rasmussen
@Service
public class AppUserServiceImpl implements AppUserService, UserDetailsService {
    private AppUserRole appUserRole;
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
        AppUserRole userRole = appUserRoleRepository.findByRole("Admin").orElseThrow(
                () -> new IllegalArgumentException("Could't find role of Admin")
        );

        AppUser user = new AppUser(appUserForm.getFirstName(),
                appUserForm.getLastName(),
                appUserForm.getEmail(),
                passwordEncoder.encode(appUserForm.getPassword()),
                LocalDate.now());

        Set<AppUserRole> roleSet = new HashSet<>();
        roleSet.add(userRole);
        user = appUserRepository.save(user);
        user.setRoleSet((AppUserRole) roleSet);
        return user;
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
