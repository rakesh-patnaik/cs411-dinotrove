package com.dinotrove.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dinotrove.entities.Dinosaur;

@Repository
public interface DinosaurRepository extends CrudRepository<Dinosaur, Long> {
    
    List<Dinosaur> findByName(String name);
    
    @Query(value = "select t.* from dinosaurs t where LOWER(t.name) LIKE :searchString OR LOWER(t.description) LIKE :searchString", nativeQuery = true)
    List<Dinosaur> findBySearchString(@Param("searchString")String searchString);
    
    
}
