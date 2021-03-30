package gelosx1.books.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import gelosx1.books.models.Author;

public interface AuthorRepository extends MongoRepository<Author, String> {

}
