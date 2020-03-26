package ec.utb.WebLibraryProject.service;

import ec.utb.WebLibraryProject.dto.CreateAppUserForm;
import ec.utb.WebLibraryProject.entity.AppUser;

import java.time.LocalDate;

public interface AppUserService {
    AppUser registerAppUser(CreateAppUserForm appUserForm);
    AppUser save(AppUser appUser);
    AppUser findById(int userId);
    AppUser findByEmail(String email);


}
