package gelosx1.books.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gelosx1.books.dao.AuthorRepository;
import gelosx1.books.dao.BookRepository;
import gelosx1.books.dao.PublisherRepository;
import gelosx1.books.dto.AuthorDto;
import gelosx1.books.dto.BookDto;
import gelosx1.books.exception.BookNotFoundException;
import gelosx1.books.models.Author;
import gelosx1.books.models.Book;
import gelosx1.books.models.Publisher;

@Service
public class BookServiceImpl implements BookService{
	
	@Autowired
	BookRepository bookRepository;

	@Autowired
	AuthorRepository authorRepository;

	@Autowired
	PublisherRepository publisherRepository;

	@Override
	public boolean addBook(BookDto bookDto) {
		if (bookRepository.existsById(bookDto.getIsbn())) {
			return false;
		}
		String publisherName= bookDto.getPublisher();
		Publisher publisher = publisherRepository
				.findById(publisherName)
				.orElse(publisherRepository.save(new Publisher(publisherName)));
		Set<AuthorDto> authorDtos = bookDto.getAuthors();
		Set<Author> authors = authorDtos.stream()
				.map(a -> authorRepository.findById(a.getName())
				.orElse(authorRepository.save(new Author(a.getName()))))
				.collect(Collectors.toSet());			
		
		Book book = new Book(bookDto.getIsbn(), bookDto.getTitle(), authors, publisher);
		bookRepository.save(book);
		return true;
	}

	@Override
	public BookDto findBookByIsbn(String isbn) {
		Book book = bookRepository.findById(isbn).orElseThrow(()-> 
		new BookNotFoundException(isbn));
		return convertToBookDto(book);
	}
	
	private BookDto convertToBookDto(Book book) {
		return BookDto.builder()
				.isbn(book.getIsbn())
				.title(book.getTitle())
				.authors(book.getAuthors()
						.stream()
						.map(a -> new AuthorDto(a.getName()))
						.collect(Collectors.toSet())
						)
				.publisher(book.getPublisher().getPublisherName())
				.build();
	}

	@Override
	public BookDto updateBook(String isbn, String title) {
		Book book = bookRepository.findById(isbn).orElse(null);
		if (book == null) {
			return null;
		}
		book.setTitle(title);
		bookRepository.save(book);
		return convertToBookDto(book);
	}

	@Override
	public BookDto removeBook(String isbn) {
		Book book = bookRepository.findById(isbn).orElseThrow(()-> 
		new BookNotFoundException(isbn));
		bookRepository.delete(book);
		return convertToBookDto(book);
	}

	@Override
	public Iterable<BookDto> findBooksByAuthor(String authorName) {
		return bookRepository.findByAuthorsName(authorName)
				.stream()
				.map(this::convertToBookDto)
				.collect(Collectors.toList());
	}

	@Override
	public Iterable<BookDto> findBooksByPublisher(String publisherName) {
		return bookRepository.findByPublisherPublisherName(publisherName)
				.stream()
				.map(this::convertToBookDto)
				.collect(Collectors.toList());
	}

	@Override
	public Iterable<BookDto> findBooksByIsbn(Set<String> isbnSet) {
		return isbnSet.stream()
		.map(isbn->bookRepository.findById(isbn).orElse(null))
		.filter(book->book != null)
		.map(this::convertToBookDto)
		.collect(Collectors.toList());
	}

	@Override
	public Iterable<BookDto> findAllBooks() {
		// TODO Auto-generated method stub
		return bookRepository.findAll()
		.stream()
		.map(this::convertToBookDto)
		.collect(Collectors.toList());
	}
		
}
