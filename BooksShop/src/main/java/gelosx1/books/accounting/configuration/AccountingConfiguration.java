package gelosx1.books.accounting.configuration;

import java.util.Base64;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import gelosx1.books.exception.UserAuthenticationException;


@Configuration
public class AccountingConfiguration {
	public UserAccountCredentials tokenDecode(String token) {
		try {
		token = token.substring(token.indexOf(" ") + 1);
		String[] credential = new String(Base64.getDecoder().decode(token)).split(":");
		return new UserAccountCredentials(credential[0], credential[1]);
		} catch (Exception e) {
			throw new UserAuthenticationException("Wrong password!");
		}
		
	}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
