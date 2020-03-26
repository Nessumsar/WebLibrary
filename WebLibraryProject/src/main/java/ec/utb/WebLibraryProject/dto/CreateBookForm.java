package ec.utb.WebLibraryProject.dto;

import javax.validation.constraints.NotBlank;

public class CreateBookForm {

    @NotBlank
    private String title;
    @NotBlank
    private String author;
}
