package gelosx1.books.service;

import java.util.Set;

import gelosx1.books.dto.BookDto;
import gelosx1.books.dto.PageableBookDto;

public interface BookService {
	
	boolean addBook(BookDto bookDto);
	
	BookDto findBookByIsbn(String isbn);
	
	BookDto updateBook(String isbn, String title);
	
	BookDto removeBook(String isbn);
	
	PageableBookDto findBooksByAuthor(String authorName, Integer currentPage, Integer itemsOnPage);
	
	PageableBookDto findBooksByPublisher(String publisherName, Integer currentPage, Integer itemsOnPage);
	
	PageableBookDto findBooksByIsbn(Set<String> isbnSet, Integer currentPage, Integer itemsOnPage);
	
	PageableBookDto getAllBooks(Integer currentPage, Integer itemsOnPage);

}
