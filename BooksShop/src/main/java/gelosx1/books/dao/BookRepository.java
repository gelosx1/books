package gelosx1.books.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import gelosx1.books.models.Book;

public interface BookRepository extends MongoRepository<Book, String> {

	Page<Book> findByAuthorsName(String authorName, Pageable pageable);
	
	Page<Book> findByPublisherPublisherName(String publisherName, Pageable pageable);
	
	List<Book> findAllByIsbn(Set<String> isbnSet, Pageable pageable);
	
}
