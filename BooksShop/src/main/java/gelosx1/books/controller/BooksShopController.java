package gelosx1.books.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gelosx1.books.dto.BookDto;
import gelosx1.books.service.BookService;



@RestController
@RequestMapping("/book")
public class BooksShopController {
	
	@Autowired
	BookService bookService;
	
	@PostMapping
	public boolean addBook(@RequestBody BookDto book) {
		return bookService.addBook(book);
		
	}
	
	@GetMapping("/{isbn}")
	public BookDto findBook(@PathVariable String isbn) {
		return bookService.findBookByIsbn(isbn);
		
	}
	
	@PutMapping("/{isbn}/title/{title}")
	public BookDto updateBook(@PathVariable String isbn,@PathVariable String title) {
		return bookService.updateBook(isbn, title);
	}
	
	@DeleteMapping("/{isbn}")
	public BookDto removeBook(@PathVariable String isbn) {
		return bookService.removeBook(isbn);
	}
	
	@GetMapping("/author/{author}")
	public Iterable<BookDto> findBooksByAuthr(@PathVariable String author) {
		return bookService.findBooksByAuthor(author);
		
	}
	@GetMapping("/publisher/{publisher}")
	public Iterable<BookDto> findBookByPublisher(@PathVariable String publisher) {
		return bookService.findBooksByPublisher(publisher);
		
	}

}
