package gelosx1.books.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import gelosx1.books.models.Book;

public interface BookRepository extends MongoRepository<Book, String> {

	List<Book> findByAuthorsName(String authorName);
	
	List<Book> findByPublisherPublisherName(String publisherName);
	
}
