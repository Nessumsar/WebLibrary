package ec.utb.WebLibraryProject.data;

import ec.utb.WebLibraryProject.entity.AppUserRole;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

//Author: Benjamin Boson
public interface AppUserRoleRepository extends CrudRepository<AppUserRole, Integer> {
    Optional<AppUserRole> findByRole(String role);
}
