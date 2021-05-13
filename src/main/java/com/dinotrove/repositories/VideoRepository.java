package com.dinotrove.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dinotrove.entities.Video;

@Repository
public interface VideoRepository extends CrudRepository<Video, Long> {

	@Query(value = "select t.* from videos t where LOWER(t.name) LIKE :searchString OR LOWER(t.video_title) LIKE :searchString LIMIT 1000", nativeQuery = true)
	List<Video> findBySearchString(@Param("searchString") String searchString);

}
