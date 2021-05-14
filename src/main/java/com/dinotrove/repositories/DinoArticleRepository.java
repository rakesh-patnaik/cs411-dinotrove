package com.dinotrove.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.dinotrove.entities.DinoArticle;

@Repository
public interface DinoArticleRepository extends MongoRepository<DinoArticle, Long> {
	
	@Query(value = "{id : ?0}")
	DinoArticle findDinoArticleById(Long id);
	
	@Query(value = "{dinosaurId: ?0}")
	List<DinoArticle> findDinoArticlesByDinosaurId(Long dinosaurId);
}
