package ec.utb.WebLibraryProject.data;

import ec.utb.WebLibraryProject.entity.AppUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AppUserRepository extends CrudRepository<AppUser, Integer> {
}
