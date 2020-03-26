package ec.utb.WebLibraryProject;

import ec.utb.WebLibraryProject.data.AppUserRoleRepository;
import ec.utb.WebLibraryProject.entity.AppUserRole;
import ec.utb.WebLibraryProject.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
public class Seeder implements CommandLineRunner {

    private AppUserService appUserService;
    private AppUserRoleRepository appUserRoleRepository;

    @Autowired
    public Seeder(AppUserService appUserService, AppUserRoleRepository appRoleRepository) {
        this.appUserService = appUserService;
        this.appUserRoleRepository = appRoleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
       /*
        appUserRoleRepository.save(new AppUserRole("USER"));
        appUserRoleRepository.save(new AppUserRole("ADMIN"));

        appUserService.registerAppUser("Peter", "Sundberg", "peter@admin.com", "admin", LocalDate.now(), true);
        appUserService.registerAppUser("Erik", "Svensson", "erik@user.com", "user", LocalDate.now(), false);
        */
    }

}
