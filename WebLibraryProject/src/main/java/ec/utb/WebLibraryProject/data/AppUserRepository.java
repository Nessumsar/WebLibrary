package ec.utb.WebLibraryProject.data;

import ec.utb.WebLibraryProject.entity.AppUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

//Author: Benjamin Boson
public interface AppUserRepository extends CrudRepository<AppUser, Integer> {
    Optional<AppUser> findByEmailIgnoreCase(String email);
}
