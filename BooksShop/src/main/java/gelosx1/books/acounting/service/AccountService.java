package gelosx1.books.acounting.service;

import javax.servlet.http.HttpServletResponse;

import gelosx1.books.accounting.dto.LoginUserProfileDto;
import gelosx1.books.accounting.dto.UserProfileDto;
import gelosx1.books.accounting.dto.UserRegisterDto;



public interface AccountService {

	UserProfileDto register(UserRegisterDto userRegisterDto);
	
	LoginUserProfileDto login(String token, HttpServletResponse response);
}
