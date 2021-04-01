package gelosx1.books.service;

import java.util.Set;

import gelosx1.books.dto.BookDto;

public interface BookService {
	
	boolean addBook(BookDto bookDto);
	
	BookDto findBookByIsbn(String isbn);
	
	BookDto updateBook(String isbn, String title);
	
	BookDto removeBook(String isbn);
	
	Iterable<BookDto> findBooksByAuthor(String authorName);
	
	Iterable<BookDto> findBooksByPublisher(String publisherName);
	
	Iterable<BookDto> findBooksByIsbn(Set<String> isbnSet);
	
	Iterable<BookDto> findAllBooks();

}
