package ec.utb.WebLibraryProject.controller;

import ec.utb.WebLibraryProject.data.AppUserRepository;
import ec.utb.WebLibraryProject.data.BookRepository;
import ec.utb.WebLibraryProject.dto.CreateAppUserForm;
import ec.utb.WebLibraryProject.dto.CreateBookForm;
import ec.utb.WebLibraryProject.entity.AppUser;
import ec.utb.WebLibraryProject.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import javax.validation.Valid;
import java.time.LocalDate;

@Controller
public class AdminController {

    private AppUserRepository appUserRepository;
    private BookRepository bookRepository;

    @Autowired
    public AdminController(AppUserRepository appUserRepository, BookRepository bookRepository) {
        this.appUserRepository = appUserRepository;
        this.bookRepository = bookRepository;
    }

    @GetMapping("/create/user")
    public String getCreateUserForm(Model model){
        model.addAttribute("form",new CreateAppUserForm());
        return "create-user";
    }

    @PostMapping("/create/user/process")
    public String postCreateUserForm(@Valid @ModelAttribute("form") CreateAppUserForm form, BindingResult bindingResult){

        if(appUserRepository.findByEmailIgnoreCase(form.getEmail()).isPresent()){
            FieldError error = new FieldError("form", "email", "Email is already in use");
            bindingResult.addError(error);
        }

        if(!form.getPassword().equals(form.getPasswordConfirm())){
            FieldError error = new FieldError("form", "passwordConfirm", "Your confirmation didn't match password");
            bindingResult.addError(error);
        }

        if(bindingResult.hasErrors()){
            return "create-user";
        }

        AppUser newAppUser = new AppUser(form.getFirstName(),form.getLastName(),form.getEmail(),form.getPassword(), LocalDate.now());
        appUserRepository.save(newAppUser);

        return "redirect:/index";
    }

    @GetMapping("/create/book")
    public String getCreateBookForm(Model model){
        model.addAttribute("form", new CreateBookForm());
        return "create-book";
    }

    @PostMapping("/create/book/process")
    public String processCreateBookForm(@Valid @ModelAttribute("form") CreateBookForm form, BindingResult bindingResult){


        if(form.getMaxLoanDays().length() < 1){
            FieldError fieldError = new FieldError("form","maxLoanDays","Enter max amount of loan days");
            bindingResult.addError(fieldError);
        }

        if (bindingResult.hasErrors()){
            return "create-book";
        }

        Book book = new Book(Integer.parseInt(form.getMaxLoanDays()), form.getTitle(), form.getAuthor());
        bookRepository.save(book);
        return "redirect:/books";
    }

}
