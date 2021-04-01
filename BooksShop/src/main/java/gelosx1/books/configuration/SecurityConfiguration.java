package gelosx1.books.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import gelosx1.books.accounting.jwt.JwtConfigurer;



@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
	JwtConfigurer jwtConfigurer;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic();
		http.csrf().disable();
		http.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests()
			.antMatchers(HttpMethod.GET,"/book/id/{isbn}").permitAll()
			.antMatchers(HttpMethod.GET,"/book/author/{author}").permitAll()
			.antMatchers(HttpMethod.GET,"/book/publisher/{publisher}").permitAll()
			.antMatchers(HttpMethod.POST,"/account/registration").permitAll()
			.antMatchers(HttpMethod.POST,"/account/login").permitAll()
			.antMatchers(HttpMethod.GET,"/book/all").permitAll()
			.antMatchers(HttpMethod.POST,"/book").hasRole("ADMIN")
			.antMatchers(HttpMethod.PUT,"/book/{isbn}/title/{title}").hasRole("ADMIN")
			.antMatchers(HttpMethod.DELETE,"/book/{isbn}").hasRole("ADMIN")
            .and()
            .apply(jwtConfigurer);

			
	}
	

}
