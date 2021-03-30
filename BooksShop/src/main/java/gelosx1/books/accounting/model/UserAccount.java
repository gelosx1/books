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
@Builder
@EqualsAndHashCode(of={"name"})
@Document(collection = "users")
public class UserAccount {
	
	@Setter @Id String name;	
	@Setter String password;	
	
	@Setter
	@Singular
	Set<UserRole> roles;
	
	public boolean addRole(UserRole role) {
		return roles.add(role);
	}

	public boolean removeRole(UserRole role) {
		return roles.remove(role);
	}

}
