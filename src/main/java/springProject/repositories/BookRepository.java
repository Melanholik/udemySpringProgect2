package springProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springProject.models.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
}
