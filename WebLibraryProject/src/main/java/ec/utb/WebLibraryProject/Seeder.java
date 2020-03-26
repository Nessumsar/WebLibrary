package ec.utb.WebLibraryProject;

import ec.utb.WebLibraryProject.data.AppUserRepository;
import ec.utb.WebLibraryProject.data.BookRepository;
import ec.utb.WebLibraryProject.data.LoanRepository;
import ec.utb.WebLibraryProject.entity.AppUser;
import ec.utb.WebLibraryProject.entity.Book;
import ec.utb.WebLibraryProject.entity.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.Arrays;

//Author: Benjamin Boson
@Component
public class Seeder implements CommandLineRunner {

    private BookRepository bookRepository;
    private AppUserRepository appUserRepository;
    private LoanRepository loanRepository;

    @Autowired
    public Seeder(BookRepository bookRepository, AppUserRepository appUserRepository, LoanRepository loanRepository) {
        this.bookRepository = bookRepository;
        this.appUserRepository = appUserRepository;
        this.loanRepository = loanRepository;
    }

    @Override
    public void run(String... args) throws Exception {

    }
/*
    @Override
    public void run(String... args) throws Exception {
        AppUser user = new AppUser("Benjamin","Boson","BenjaminEBoson@Gmail.com","1a1b1c1d", LocalDate.now());
        Book book = new Book(14, "Harry Potter", "J.K Rowling");
        bookRepository.saveAll(
                Arrays.asList(new Book(14, "Harry Potter2", "J.K Rowling"),
                        new Book(14, "Harry Potter3", "J.K Rowling"),
                        new Book(14, "Harry Potter4", "J.K Rowling"),
                        new Book(14, "Harry Potter5", "J.K Rowling"),
                        (new Book(14, "Harry Potter6", "J.K Rowling")
                        )
                ));
        Loan loan = new Loan(LocalDate.now(), LocalDate.of(2020,04,15),user,book);
        user = appUserRepository.save(user);
        loan = loanRepository.save(loan);
    }

 */

}
