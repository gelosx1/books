package gelosx1.books.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import gelosx1.books.models.User;

public interface UserRepository extends MongoRepository<User, String> {

}
