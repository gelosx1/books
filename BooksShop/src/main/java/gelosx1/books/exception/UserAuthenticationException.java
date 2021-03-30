package gelosx1.books.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserAuthenticationException extends RuntimeException {

	public UserAuthenticationException(String message) {
		// TODO Auto-generated constructor stub
		super(message);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}