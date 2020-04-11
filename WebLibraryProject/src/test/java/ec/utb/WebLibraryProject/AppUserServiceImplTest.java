package ec.utb.WebLibraryProject;

import ec.utb.WebLibraryProject.entity.AppUser;
import ec.utb.WebLibraryProject.service.AppUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

//Author: Rijad Hamula
@SpringBootTest
public class AppUserServiceImplTest {

    @Autowired
    private AppUserService appUserService;

    //Testing register user to make sure that everything's working when you register a user in login page
    @Test
    public void registerUser(){
        String firstName = "Test";
        String lastName = "Testsson";
        String email = "test@gmail.com";
        String password = "1234";

        AppUser appUser = appUserService.registerAppUser(firstName,lastName,email,password, LocalDate.now(),true);
        assertNotNull(appUser);
        assertEquals(2,appUser.getAppUserId());
        assertEquals("Test",appUser.getFirstName());
        assertEquals("Testsson",appUser.getLastName());
        assertEquals("test@gmail.com",appUser.getEmail());

    }
}
