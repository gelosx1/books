package gelosx1.books.acounting.service;

import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import gelosx1.books.accounting.dto.UserProfileDto;
import gelosx1.books.accounting.dto.UserRegisterDto;
import gelosx1.books.dto.PageableBookDto;



public interface AccountService {

	UserProfileDto register(UserRegisterDto userRegisterDto);
	
	UserProfileDto login(String token, HttpServletResponse response);
	
	Set<String> purchaseBook(String name, String isbn);
	
	PageableBookDto getPurchasedBooks(String name, Integer currentPage, Integer itemsOnPage);
}
