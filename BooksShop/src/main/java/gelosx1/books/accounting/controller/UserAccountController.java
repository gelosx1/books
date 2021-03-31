package gelosx1.books.accounting.controller;

import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import gelosx1.books.accounting.dto.UserProfileDto;
import gelosx1.books.accounting.dto.UserRegisterDto;
import gelosx1.books.acounting.service.AccountService;



@RestController
@RequestMapping("/account")
public class UserAccountController {
	
	@Autowired
	AccountService accountService;
	
	@PostMapping("/registration")
	public UserProfileDto register(@RequestBody UserRegisterDto userRegisterDto) {
		return accountService.register(userRegisterDto);
		
	}
	
	@PostMapping("/login")
	public UserProfileDto login(@RequestHeader("Authorization") String token,
			HttpServletResponse response){
		return accountService.login(token, response);
		
	}
	
	@PutMapping("/{name}/purchase/{isbn}")
	public Set<String> purchaseBook(@PathVariable String name,
			@PathVariable String isbn) {
		return accountService.purchaseBook(name, isbn);
		
	}
	
	@GetMapping("/{name}/purchased")
	public Set<String> getPurchasedBooks(@PathVariable String name) {
		return accountService.getPurchasedBooks(name);
	}
}
	
