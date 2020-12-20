package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.entities.RankKorisnika;

public interface RankRepository extends JpaRepository<RankKorisnika, Long>{
	
}
