package gelosx1.books.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import gelosx1.books.accounting.model.UserAccount;


public interface UserRepository extends MongoRepository<UserAccount, String> {

}
