package com.dinotrove.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dinotrove.entities.DinosaurVideoSummary;

@Repository
public interface DinosaurVideoSummaryRepository extends CrudRepository<DinosaurVideoSummary, Long> {
    

}
