package ec.utb.WebLibraryProject.data;

import ec.utb.WebLibraryProject.entity.AppUserRole;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AppUserRoleRepository extends CrudRepository<AppUserRole, Integer> {

}
