package ec.utb.WebLibraryProject.service;

import ec.utb.WebLibraryProject.entity.AppUser;

import java.time.LocalDate;

public interface AppUserService {
    AppUser registerAppUser(String firstName, String lastName, String email, String password, LocalDate regDate, boolean isAdmin);
}
