package gelosx1.books.accounting.model;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(of={"name"})
@Document(collection = "users")
public class UserAccount {
	
	 @Id String name;	
	 String password;	
	
	@Singular
	Set<UserRole> roles;
	
	public boolean addRole(UserRole role) {
		return roles.add(role);
	}

	public boolean removeRole(UserRole role) {
		return roles.remove(role);
	}
	
	@Singular
	Set<String> purchasedBooks;
	
	public boolean purchaseBook(String isbn) {
		return purchasedBooks.add(isbn);
	}

}
