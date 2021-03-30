package gelosx1.books.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import gelosx1.books.models.Publisher;

public interface PublisherRepository extends MongoRepository<Publisher, String> {

}
