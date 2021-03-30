package gelosx1.books.configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic();
		http.csrf().disable();
		http.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests()
			.antMatchers(HttpMethod.GET,"/book/{isbn}").permitAll()
			.antMatchers(HttpMethod.GET,"/book/author/{author}").permitAll()
			.antMatchers(HttpMethod.GET,"/book/publisher/{publisher}").permitAll()
			.antMatchers("/book").authenticated()
			.antMatchers("/book").hasRole("ADMIN")
			.antMatchers("/book/{isbn}/title/{title}").hasRole("ADMIN")
			.antMatchers("/book/{isbn}").hasRole("ADMIN");;

			
	}
	

}
