package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import app.entities.Avion;

public interface AvionRepository extends JpaRepository<Avion, Long> {
	
	Avion findById(long id);
	
}
