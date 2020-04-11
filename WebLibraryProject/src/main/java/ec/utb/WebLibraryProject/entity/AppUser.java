package ec.utb.WebLibraryProject.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//Author: Benjamin Boson & Lukas Rasmussen
@Entity
public class AppUser {
    /*
    The AppUser is the most important entity of them all.
    Without a User, you can't have any security or deny certain people access.

    Id and GeneratedValue for a automatic generation of primary ID in the database.
    Column unique means that you cant make 2 different Users with the same email.

    The class has a ManyToMany relationship with Role. An Admin is still an User, therefore it has to be a ManyToMany.
    JoinTable is a tricky annotation because it decides how the ManyToMany should act.
    We use a "middle" table called app_user_app_role.
    With joinColumns and inverseJoinColumns we decide where the primary keys should come from.
    user_Id here and role_id as inverse since that's not the class we're working from.
    If this relationship would be set in AppUserRole, we would set inverseJoinColumns on user_id instead.

    We also got a OneToMany with Loan since one user can have many Loans.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int appUserId;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String password;
    private LocalDate regDate;

    @ManyToMany(
            cascade = {CascadeType.MERGE},
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "app_user_app_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<AppUserRole> roleList;
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},
            mappedBy = "appUser"
    )
    private List<Loan> loanList = new ArrayList<>();

    public AppUser(String firstName, String lastName, String email, String password, LocalDate regDate ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.regDate = regDate;
    }

    public AppUser() {
    }

    public boolean addLoan(Loan loan){
        if (!loanList.contains(loan)) return loanList.add(loan);
        return false;
    }

    public List<Loan> getLoanList() {
        return loanList;
    }

    public void setLoanList(List<Loan> loanList) {
        this.loanList = loanList;
    }

    public List<AppUserRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<AppUserRole> roleList) {
        this.roleList = roleList;
    }

    public int getAppUserId() {
        return appUserId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getRegDate() {
        return regDate;
    }

    public void setRegDate(LocalDate regDate) {
        this.regDate = regDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUser appUser = (AppUser) o;
        return appUserId == appUser.appUserId &&
                firstName.equals(appUser.firstName) &&
                lastName.equals(appUser.lastName) &&
                email.equals(appUser.email) &&
                password.equals(appUser.password) &&
                regDate.equals(appUser.regDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appUserId, firstName, lastName, email, password, regDate);
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "appUserId=" + appUserId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", regDate=" + regDate +
                '}';
    }
}
