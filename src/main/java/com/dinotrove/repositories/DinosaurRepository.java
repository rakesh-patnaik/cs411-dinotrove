package com.dinotrove.repositories;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dinotrove.entities.Dinosaur;

@Repository
public interface DinosaurRepository extends CrudRepository<Dinosaur, Long> {
    
    List<Dinosaur> findByName(String name);
    
    @Query(value = "select t.* from dinosaurs t where LOWER(t.name) LIKE :searchString OR LOWER(t.description) LIKE :searchString", 
    				nativeQuery = true)
    List<Dinosaur> findBySearchString(@Param("searchString")String searchString);
    
    @Transactional  // START A Transaction to be atomic
    @Modifying(clearAutomatically = true, flushAutomatically = true) // Ensure JPA context is clean and it refreshes after this delete
    @Query(value = "update"
    		+ "        dinosaurs"
    		+ "    set"
    		+ "        all_facts_document_id=:allFactsDocumentId,"
    		+ "        description=:description,"
    		+ "        dinosaur_type=:dinosaurType,"
    		+ "        name=:name,"
    		+ "        size_height=:sizeHeight,"
    		+ "        size_length=:sizeLength,"
    		+ "        size_weight=:sizeWeight"
    		+ "    where"
    		+ "        dinosaur_id=:dinosaurId",
            nativeQuery = true)
    void updateDinosaur(@Param("dinosaurId") Long dinosaurId, 
    					@Param("name") String name, 
    					@Param("dinosaurType") String dinosaurType,
    					@Param("sizeHeight") Double sizeHeight, 
    					@Param("sizeWeight") Double sizeWeight, 
    					@Param("sizeLength") Double sizeLength, 
    					@Param("description") String description, 
    					@Param("allFactsDocumentId") String allFactsDocumentId);
    
}
