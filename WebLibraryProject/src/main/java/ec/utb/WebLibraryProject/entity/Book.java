package ec.utb.WebLibraryProject.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;
    private int maxLoanDays;
    private String title;
    private boolean isAvailable;
    private boolean isReserved;

}
