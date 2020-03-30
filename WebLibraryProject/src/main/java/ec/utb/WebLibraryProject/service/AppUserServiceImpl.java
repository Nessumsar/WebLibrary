package ec.utb.WebLibraryProject.service;

import ec.utb.WebLibraryProject.data.AppUserRepository;
import ec.utb.WebLibraryProject.data.AppUserRoleRepository;
import ec.utb.WebLibraryProject.dto.CreateAppUserForm;
import ec.utb.WebLibraryProject.entity.AppUser;
import ec.utb.WebLibraryProject.entity.AppUserRole;
import ec.utb.WebLibraryProject.security.AppUserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;

//Author: Lukas Rasmussen & Cheng Tao
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
    public AppUser registerAppUser(String firstName, String lastName, String email, String password, LocalDate regDate, boolean isAdmin) {
        AppUser newUser = new AppUser(firstName,lastName,email,passwordEncoder.encode(password),regDate);
        List<AppUserRole> appRoleList = new ArrayList<>();

        if (isAdmin){
            AppUserRole admin = appUserRoleRepository.findByRole("ADMIN").orElseThrow(IllegalArgumentException::new);
            appRoleList.add(admin);
        }

        AppUserRole userRole = appUserRoleRepository.findByRole("USER").orElseThrow(IllegalArgumentException::new);
        appRoleList.add(userRole);

        newUser.setRoleList(appRoleList);
        return appUserRepository.save(newUser);
    }



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<AppUser> optional = appUserRepository.findByEmailIgnoreCase(email);
        AppUser user = optional.orElseThrow(() -> new IllegalArgumentException("Email "+email+" could not be found"));

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for (AppUserRole role: user.getRoleList()){
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getRole());
            authorities.add(authority);
        }

        return new AppUserPrincipal(user,authorities);
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
