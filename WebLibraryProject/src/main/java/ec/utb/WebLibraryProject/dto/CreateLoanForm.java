package ec.utb.WebLibraryProject.dto;

import javax.validation.constraints.NotBlank;

//Author: Benjamin Boson & Lukas Rasmussen
public class CreateLoanForm {

    @NotBlank(message = "Please enter loan date")
    private String startDate;
    @NotBlank(message = "Please enter return date")
    private String endDate;
    private String appUserEmail;
    private String bookId;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getAppUserEmail() {
        return appUserEmail;
    }

    public void setAppUserEmail(String appUserEmail) {
        this.appUserEmail = appUserEmail;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
}
