package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import app.entities.Let;

public interface LetRepository extends JpaRepository<Let, Long> {

	@Query("select let from Let let where :query ")
	List<Let> findByAny(String query);
	
}
