package gelosx1.books.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import gelosx1.books.dao.AuthorRepository;
import gelosx1.books.dao.BookRepository;
import gelosx1.books.dao.PublisherRepository;
import gelosx1.books.dto.AuthorDto;
import gelosx1.books.dto.BookDto;
import gelosx1.books.dto.PageableBookDto;
import gelosx1.books.exception.BookNotFoundException;
import gelosx1.books.models.Author;
import gelosx1.books.models.Book;
import gelosx1.books.models.PageInfo;
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
	public PageableBookDto findBooksByAuthor(String authorName, Integer currentPage, Integer itemsOnPage) {
		Pageable pageable = PageRequest.of(currentPage - 1, itemsOnPage, Sort.by("isbn").ascending());
		Page<Book> books = bookRepository.findByAuthorsName(authorName, pageable);
		return convertToPageableBookDto(books);
	}

	@Override
	public PageableBookDto findBooksByPublisher(String publisherName, Integer currentPage, Integer itemsOnPage) {
		Pageable pageable = PageRequest.of(currentPage - 1, itemsOnPage, Sort.by("isbn").ascending());
		Page<Book> books = bookRepository.findByPublisherPublisherName(publisherName, pageable);
		return convertToPageableBookDto(books);
	}

	
	@Override
	public PageableBookDto findBooksByIsbn(Set<String> isbnSet, Integer currentPage, Integer itemsOnPage) {
		Pageable pageable = PageRequest.of(currentPage - 1, itemsOnPage, Sort.unsorted());
		Iterable<Book> iterable = bookRepository.findAllById(isbnSet);
		List<Book> books = StreamSupport.stream(iterable.spliterator(), false)
		.collect(Collectors.toList());
		int count = books.size();
		int maxIndex = itemsOnPage * currentPage > count ? count 
				: itemsOnPage * currentPage;
		Page<Book> pageResponse = new PageImpl<Book>(books.subList(itemsOnPage * (currentPage - 1), maxIndex),
				pageable, count);
		return convertToPageableBookDto(pageResponse);
		
	}
	
	@Override
	public PageableBookDto getAllBooks(Integer currentPage, Integer itemsOnPage) {
		Pageable pageable = PageRequest.of(currentPage - 1, itemsOnPage, Sort.by("isbn").ascending());
		Page<Book> books = bookRepository.findAll(pageable);
	return convertToPageableBookDto(books);
		
	}
	
	private PageableBookDto convertToPageableBookDto(Page<Book> books) {
		return	PageableBookDto.builder()
				.pageInfo(new PageInfo(books.getNumberOfElements(),
						books.getNumber() + 1,
						books.getTotalPages()))
				.books(books.getContent()
						.stream()
						.map(this::convertToBookDto)
						.collect(Collectors.toList()))
				.build();
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
		
}
