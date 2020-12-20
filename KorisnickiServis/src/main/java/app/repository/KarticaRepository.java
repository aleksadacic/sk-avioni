package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.entities.KreditnaKartica;

public interface KarticaRepository extends JpaRepository<KreditnaKartica, Long>{

}
