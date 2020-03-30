package ec.utb.WebLibraryProject.service;

import ec.utb.WebLibraryProject.data.AppUserRepository;
import ec.utb.WebLibraryProject.entity.AppUser;
import ec.utb.WebLibraryProject.entity.AppUserRole;
import ec.utb.WebLibraryProject.security.AppUserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

//Author: Lukas Rasmussen
@Service
public class AppUserDetailsService implements UserDetailsService {
    AppUserRepository appUserRepository;

    @Autowired
    public AppUserDetailsService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<AppUser> userOptional = appUserRepository.findByEmailIgnoreCase(email);
        if(userOptional.isPresent()){
            AppUser user = userOptional.get();
            Collection<GrantedAuthority> collection = new HashSet<>();
            for(AppUserRole appRole : user.getRoleList()){
                collection.add(new SimpleGrantedAuthority(appRole.getRole()));
            }
            return new AppUserPrincipal(user, collection);

        }else{
            throw new UsernameNotFoundException("Couldn't find user with email " + email);
        }
    }
}
