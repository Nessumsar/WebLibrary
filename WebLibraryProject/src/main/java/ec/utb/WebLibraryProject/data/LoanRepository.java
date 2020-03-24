package ec.utb.WebLibraryProject.data;

import ec.utb.WebLibraryProject.entity.Loan;
import org.springframework.data.repository.CrudRepository;

public interface LoanRepository extends CrudRepository<Loan, Integer> {
}
