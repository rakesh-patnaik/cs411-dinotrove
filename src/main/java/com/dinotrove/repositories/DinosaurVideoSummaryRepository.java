package com.dinotrove.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dinotrove.entities.DinosaurVideoSummary;

@Repository
public interface DinosaurVideoSummaryRepository extends CrudRepository<DinosaurVideoSummary, Long> {

	@Query(value = "select vs.* from dinosaur_video_summary vs limit 100", nativeQuery = true)
	List<DinosaurVideoSummary> findVideoSummary();
}
