package gelosx1.books.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PageInfo {
		Integer itemsOnPage;
		Integer currentPage;
		Integer pagesQuantity;		
}

