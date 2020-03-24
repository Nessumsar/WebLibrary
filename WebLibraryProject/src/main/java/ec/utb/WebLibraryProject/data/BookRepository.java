package ec.utb.WebLibraryProject.data;

import ec.utb.WebLibraryProject.entity.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Integer> {
}
