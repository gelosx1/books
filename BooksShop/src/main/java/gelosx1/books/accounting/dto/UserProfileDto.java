package gelosx1.books.accounting.dto;

import java.util.Set;


import gelosx1.books.accounting.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UserProfileDto {
	  String name;
	  Set<UserRole>roles;
}
