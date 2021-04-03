package gelosx1.books.dto;


import java.util.List;

import gelosx1.books.models.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PageableBookDto {
		List<BookDto> books;
		@Setter
		PageInfo pageInfo;
		
}
