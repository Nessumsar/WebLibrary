package ec.utb.WebLibraryProject.controller;

import ec.utb.WebLibraryProject.data.AppUserRepository;
import ec.utb.WebLibraryProject.data.BookRepository;
import ec.utb.WebLibraryProject.data.LoanRepository;
import ec.utb.WebLibraryProject.dto.CreateLoanForm;
import ec.utb.WebLibraryProject.entity.AppUser;
import ec.utb.WebLibraryProject.entity.Book;
import ec.utb.WebLibraryProject.entity.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

//Author: Benjamin Boson
@Controller
public class AppUserController {

    private BookRepository bookRepository;
    private AppUserRepository appUserRepository;
    private LoanRepository loanRepository;

    @Autowired
    public AppUserController(BookRepository bookRepository, AppUserRepository appUserRepository, LoanRepository loanRepository) {
        this.bookRepository = bookRepository;
        this.appUserRepository = appUserRepository;
        this.loanRepository = loanRepository;
    }

    @GetMapping("/books")
    public String getBookView(Model model){
        List<Book> bookList = bookRepository.findAll();
        model.addAttribute("bookList",bookList);
        return "books-view";
    }

    @GetMapping("/create/loan/{libraryBookId}")
    public String getCreateLoanForm(Model model, @PathVariable("libraryBookId") int bookId){
        model.addAttribute("form",new CreateLoanForm());
        model.addAttribute("libraryBookId",bookId);
        Book book = bookRepository.findById(bookId).orElseThrow(IllegalArgumentException::new);
        model.addAttribute("book",book);
        return "create-loan";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/create/loan/process")
    public String processCreateLoanForm(@Valid @ModelAttribute("form") CreateLoanForm form, BindingResult bindingResult, Model model){

        if (!form.getEndDate().isEmpty() && LocalDate.parse(form.getEndDate()).isBefore(LocalDate.parse(form.getStartDate()))){
            FieldError error = new FieldError("form","endDate","Return date can't be before initial loan date");
            bindingResult.addError(error);
        }

        if (!form.getEndDate().isEmpty() && LocalDate.parse(form.getStartDate()).isBefore(LocalDate.now())){
            FieldError error = new FieldError("form","startDate","Enter a date after "+LocalDate.now().minusDays(1));
            bindingResult.addError(error);
        }

        if (!form.getEndDate().isEmpty() && DAYS.between(LocalDate.parse(form.getStartDate()),LocalDate.parse(form.getEndDate()))
                > bookRepository.findById(Integer.valueOf(form.getLibraryBookId())).orElseThrow(IllegalArgumentException::new).getMaxLoanDays()
                && LocalDate.parse(form.getStartDate()).isBefore(LocalDate.parse(form.getEndDate()))){
            FieldError error = new FieldError("form","endDate","Loan period exceeds max loan days for this book");
            bindingResult.addError(error);
        }

        if (bindingResult.hasErrors()){
            model.addAttribute("form",form);
            model.addAttribute("libraryBookId",Integer.valueOf(form.getLibraryBookId()));
            Book book = bookRepository.findById(Integer.valueOf(form.getLibraryBookId())).orElseThrow(IllegalArgumentException::new);
            model.addAttribute("book",book);
            return "create-loan";
        }

        AppUser user = appUserRepository.findByEmailIgnoreCase(form.getAppUserEmail()).orElseThrow(IllegalArgumentException::new);
        Book book = bookRepository.findById(Integer.valueOf(form.getLibraryBookId())).orElseThrow(IllegalAccessError::new);
        book.setAvailable(false);
        Loan loan = new Loan(LocalDate.parse(form.getStartDate()),LocalDate.parse(form.getEndDate()), user,book);
        user.addLoan(loan);
        loanRepository.save(loan);
        return "redirect:/index";
    }
}
