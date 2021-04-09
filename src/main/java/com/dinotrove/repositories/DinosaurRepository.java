package com.dinotrove.repositories;

import com.dinotrove.entities.Dinosaur;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DinosaurRepository extends CrudRepository<Dinosaur, Long> {
    
    List<Dinosaur> findByName(String name);
    
}
