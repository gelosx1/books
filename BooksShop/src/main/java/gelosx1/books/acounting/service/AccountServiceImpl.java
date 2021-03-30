package gelosx1.books.acounting.service;

import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gelosx1.books.accounting.configuration.AccountingConfiguration;
import gelosx1.books.accounting.configuration.SecurityConstants;
import gelosx1.books.accounting.configuration.UserAccountCredentials;
import gelosx1.books.accounting.dto.UserProfileDto;
import gelosx1.books.accounting.dto.UserRegisterDto;
import gelosx1.books.accounting.jwt.JwtTokenProvider;
import gelosx1.books.accounting.model.UserAccount;
import gelosx1.books.accounting.model.UserRole;
import gelosx1.books.dao.UserRepository;
import gelosx1.books.exception.UserAuthenticationException;
import gelosx1.books.exception.UserExistsException;
import gelosx1.books.exception.UserNotFoundException;

@Service
public class AccountServiceImpl implements AccountService{
	
	@Autowired
	AccountingConfiguration userAccountConfiguration;
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	UserRepository userRepository;

	@Override
	public UserProfileDto register(UserRegisterDto userRegisterDto) {
		if (userRepository.existsById(userRegisterDto.getName())) {
			throw new UserExistsException();
		}
		String hashPassword = BCrypt.hashpw(userRegisterDto.getPassword(), BCrypt.gensalt());
		UserAccount userAccount = UserAccount.builder()
				.password(hashPassword)
				.name(userRegisterDto.getName())
				.role(UserRole.ROLE_USER)
				.build();
			userRepository.save(userAccount);
			return userAccountToUserProfileDto(userAccount);
	}

	@Override
	public UserProfileDto login(String token, HttpServletResponse response) {
		UserAccount userAccount = checkUserAccount(token);
		placeTokenToHeader(userAccount.getName(), response);
		return userAccountToUserProfileDto(userAccount);
	}
	
	
	private UserProfileDto userAccountToUserProfileDto(UserAccount userAccount) {
		return UserProfileDto.builder()
				.name(userAccount.getName())
				.roles(userAccount.getRoles())
				.build();
	}
	
	private UserAccount checkUserAccount(String token) {
		UserAccountCredentials credential = userAccountConfiguration.tokenDecode(token);
		  UserAccount user = userRepository.findById(credential.getLogin())
				 .orElseThrow(() -> new UserNotFoundException(credential.getLogin()));
		if (!BCrypt.checkpw(credential.getPassword(),user.getPassword())) {
			throw new UserAuthenticationException("Wrong password");
			
		}
		return user;
	}
	
	private void placeTokenToHeader(String username, HttpServletResponse response) {
		String Jwttoken =  jwtTokenProvider.createToken(username);
		if (response.getHeader(SecurityConstants.X_TOKEN_HEADER) != null) {
			response.setHeader(SecurityConstants.X_TOKEN_HEADER, Jwttoken);
		}else {
			response.addHeader(SecurityConstants.X_TOKEN_HEADER, Jwttoken);
		}
		
	}

}
