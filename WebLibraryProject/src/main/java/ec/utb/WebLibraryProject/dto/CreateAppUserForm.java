package ec.utb.WebLibraryProject.dto;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

public class CreateAppUserForm {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email()
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String passwordConfirm;
}
