package gelosx1.books.accounting.jwt;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import gelosx1.books.accounting.configuration.SecurityConstants;
import gelosx1.books.acounting.service.AccountServiceImpl;



@Component
public class JwtTokenFilter extends OncePerRequestFilter {

	@Autowired
	AccountServiceImpl userAccountService;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String endPoint = getStringFromPathVariable(request, "(?<=(book|account)\\/)(.*?)(?=\\/|$)");
	
		
			if (!"registration".equals(endPoint) 
					&& !"login".equals(endPoint) 
					&& !"publisher".equals(endPoint)
					&& !"author".equals(endPoint)
					&& !"all".equals(endPoint)
					&& !"id".equals(endPoint)) {			
				String headerToken = request.getHeader(SecurityConstants.X_TOKEN_HEADER);
		
				if (headerToken != null && jwtTokenProvider.validateToken(headerToken, response)) {
		            Authentication auth = jwtTokenProvider.getAuthentication(headerToken);
		            
		            if (auth != null) {
		                SecurityContextHolder.getContext().setAuthentication(auth);
		                String Jwttoken =  jwtTokenProvider.createToken(auth.getName());
		                response.setHeader(SecurityConstants.X_TOKEN_HEADER, Jwttoken);
		            }
		            
		        }				
			
			}
			filterChain.doFilter(request, response);
		}

	private String getStringFromPathVariable(HttpServletRequest request, String regex) {
		try {
			String path = request.getServletPath();
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(path);
			matcher.find();
			return matcher.group();
		} catch (Exception e) {
			return null;
		}

	}

}