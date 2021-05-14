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

	
	@Query(value="(SELECT v.*\n"
			+ "FROM\n"
			+ "	videos v,\n"
			+ "	(SELECT video_id, \n"
			+ "			count(*) as dino_count\n"
			+ "	FROM video_dinosaurs vd \n"
			+ "	GROUP BY video_id\n"
			+ "    ORDER BY dino_count \n"
			+ "    LIMIT 100) AS video_top100_dino_count\n"
			+ "WHERE v.video_id = video_top100_dino_count.video_id)\n"
			+ "UNION\n"
			+ "(SELECT v.*\n"
			+ "FROM\n"
			+ "	videos v\n"
			+ "ORDER BY v.video_length DESC\n"
			+ "LIMIT 100)", nativeQuery=true)
	List<Video> findVideosThatFeatureAsTop100ForDinoCountOrAsTop100ForVideoLength();
}
