package ec.utb.WebLibraryProject;

import ec.utb.WebLibraryProject.data.BookRepository;
import ec.utb.WebLibraryProject.data.LoanRepository;
import ec.utb.WebLibraryProject.entity.AppUser;
import ec.utb.WebLibraryProject.entity.Book;
import ec.utb.WebLibraryProject.entity.Loan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import java.time.LocalDate;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//Author: Cheng Tao
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestEntityManager
@Transactional
public class AppUserControllerTest {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private TestEntityManager em;

    @Autowired
    private BookRepository bookRepository;
    private LoanRepository loanRepository;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void nothingInputShouldReturn_302() throws Exception{
        mockMvc.perform(get("/loans/{email}", " "))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/accessDenied"));
    }

    @Test
    @WithMockUser(username = "BenjaminEBoson@Gmail.com", authorities = { "ADMIN", "USER" })
    public void EmailInputShouldReturn_200() throws Exception{
        mockMvc.perform(get("/loans/{email}", "BenjaminEBoson@Gmail.com"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("loans-view"))
                .andExpect(model().attributeExists("loanList"));
    }

    @Test
    public void InvalidEmailInputShouldReturn_302() throws Exception{
        mockMvc.perform(get("/loans/{email}", "cheng.tao86@gmail.com"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/accessDenied"));
    }

    @Test
    @WithMockUser(username = "BenjaminEBoson@Gmail.com", authorities = { "ADMIN", "USER" })
    public void inputEmptyShouldReturnBookList_200() throws Exception{
        mockMvc.perform(get("/books"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("books-view"))
                .andExpect(model().attributeExists("bookList"));
    }

    @Test
    public void loginReturnSuccess_200() throws Exception{
        mockMvc.perform(get("/login"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    public void AccessDeniedReturnSuccess_200() throws Exception{
        mockMvc.perform(get("/accessDenied"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("access-denied"));
    }

    @Test
    @WithMockUser(username = "BenjaminEBoson@Gmail.com", authorities = { "ADMIN", "USER" })
    public void WrongEndDateReturnError() throws Exception{
        mockMvc.perform(post("/create/loan/process")
                .param("startDate", "2020-04-08")//String - LocalDate
                .param("endDate", "2020-04-07")
                .param("appUserEmail", "BenjaminEBoson@Gmail.com")
                .param("bookId", "1")
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeHasErrors("form"))
                .andExpect(model().attributeHasFieldErrors("form", "endDate"));
    }

    @Test
    @WithMockUser(username = "BenjaminEBoson@Gmail.com", authorities = { "ADMIN", "USER" })
    public void WrongStartDateReturnError() throws Exception{
        mockMvc.perform(post("/create/loan/process")
                .param("startDate", "2020-04-01")//String - LocalDate
                .param("endDate", "2020-04-09")
                .param("appUserEmail", "BenjaminEBoson@Gmail.com")
                .param("bookId", "1")
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeHasErrors("form"))
                .andExpect(model().attributeHasFieldErrors("form", "startDate"));
    }

    @Test
    @WithMockUser(username = "BenjaminEBoson@Gmail.com", authorities = { "ADMIN", "USER" })
    public void OverDueReturnError() throws Exception{
        mockMvc.perform(post("/create/loan/process")
                .param("startDate", "2020-04-09")//String - LocalDate
                .param("endDate", "2020-08-09")
                .param("appUserEmail", "BenjaminEBoson@Gmail.com")
                .param("bookId", "1") // 90 days
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeHasErrors("form"))
                .andExpect(model().attributeHasFieldErrors("form", "endDate"));
    }

    @Test
    @WithMockUser(username = "BenjaminEBoson@Gmail.com", authorities = { "ADMIN", "USER" })
    public void PostReturnNoError_302() throws Exception{
        mockMvc.perform(post("/create/loan/process")
                .param("startDate", "2020-04-11")//String - LocalDate
                .param("endDate", "2020-04-16")
                .param("appUserEmail", "BenjaminEBoson@Gmail.com")
                .param("bookId", "1")
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(model().hasNoErrors());
    }

    @Test
    @WithAnonymousUser
    public void ReturnBookReturnSuccess_302() throws Exception{
        Book testBook = bookRepository.findById(1).get();
        Loan testLoan = new Loan(LocalDate.parse("2020-04-08"),
                LocalDate.parse("2020-04-30"),
                new AppUser("Cheng","Tao","cheng.tao86@gmail.com","1a1b1c1d", LocalDate.now()),
                testBook);
        em.persist(testLoan);
        mockMvc.perform(get("/loans/return/{id}", "2")) // May be dynamic...the loanId every time has an increment of 1.
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/index"));
    }

}