package gelosx1.books.accounting.jwt;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import gelosx1.books.accounting.configuration.SecurityConstants;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


@Service
public class JwtTokenProvider{
	
	@Autowired
	private UserDetailsService userDetailsService;

	 public String createToken(String username) {
			return Jwts.builder()
			    .signWith(Keys.hmacShaKeyFor(getSecretKey()), SignatureAlgorithm.HS512)
			    .setHeaderParam("type", SecurityConstants.TOKEN_TYPE)
			    .setSubject(username)
			    .setExpiration(new Date(System.currentTimeMillis() 
			    		+ SecurityConstants.EXPIRED))
			    .claim("roles", getUserRoles(username))
			    .compact();
			
    }
	 
	 public Authentication getAuthentication(String token) {
	        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
	        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	    }
	 
	 public boolean validateToken(String token, HttpServletResponse response) {
	        try {
	            	Jwts.parser()
	            		.setSigningKey(getSecretKey())
	            		.parseClaimsJws(token);
	        } catch (JwtException | IllegalArgumentException e) {
	        	sendUnautorizedStatus(response);
	        	return false;
	        }
	        return true;
	    }
	 
	 
	 public Set<String> getUserRoles(String username) {
			UserDetails user = this.userDetailsService.loadUserByUsername(username);
			return user.getAuthorities()
		            .stream()
		            .map(GrantedAuthority::getAuthority)
		            .collect(Collectors.toSet());
			
		}
	 
	 private String getUsername(String token) {
	        return Jwts.parser()
	        		.setSigningKey(getSecretKey())
	        		.parseClaimsJws(token)
	        		.getBody()
	        		.getSubject();
	    }
	 
	 
	 private byte[] getSecretKey() {
			return SecurityConstants
					.S_KEY
					.getBytes();
		}
	 
	 private void sendUnautorizedStatus(HttpServletResponse response) {
		 try {
     		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		
	}

	
}
