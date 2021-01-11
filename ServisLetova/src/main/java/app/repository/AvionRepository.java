package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.entities.Avion;

public interface AvionRepository extends JpaRepository<Avion, Long> {
	
	Avion findById(long id);
	
}
