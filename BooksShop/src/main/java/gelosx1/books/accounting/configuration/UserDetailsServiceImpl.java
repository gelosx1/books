package gelosx1.books.accounting.configuration;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import gelosx1.books.accounting.model.UserAccount;
import gelosx1.books.accounting.model.UserRole;
import gelosx1.books.dao.UserRepository;
import gelosx1.books.exception.UserNotFoundException;



@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	UserRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserAccount userAccount = repository.findById(username).orElseThrow(()->
		new UserNotFoundException(username));
		String password = userAccount.getPassword();
		Set<String> setRoles = userAccount.getRoles()
				.stream()
				.map(UserRole::name)
				.collect(Collectors.toSet());
		return new User(username, password,
				AuthorityUtils.createAuthorityList(setRoles.toArray(new String[setRoles.size()])));
	}

}