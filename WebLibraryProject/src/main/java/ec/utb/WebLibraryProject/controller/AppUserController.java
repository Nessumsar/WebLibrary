package ec.utb.WebLibraryProject.controller;

import ec.utb.WebLibraryProject.data.BookRepository;
import ec.utb.WebLibraryProject.dto.CreateLoanForm;
import ec.utb.WebLibraryProject.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class AppUserController {


    private BookRepository bookRepository;

    @Autowired
    public AppUserController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/books")
    public String getBookView(Model model){
        List<Book> bookList = bookRepository.findAll();
        model.addAttribute("bookList",bookList);
        return "books-view";
    }

    @GetMapping("/create/loan")
    public String getCreateLoanForm(Model model){
        model.addAttribute("form",new CreateLoanForm());
        return "create-loan";
    }

    @PostMapping("/create/loan/process")
    public String processCreateLoanForm(@Valid @ModelAttribute("form") CreateLoanForm form, BindingResult bindingResult,
                                        @PathVariable(name = "appUserId") int appUserId,
                                        @PathVariable(name = "libraryBookId") int libraryBookId) {
        return null;
    }
}
