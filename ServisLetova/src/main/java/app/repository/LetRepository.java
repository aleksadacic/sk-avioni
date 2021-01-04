package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import app.entities.Avion;
import app.entities.Let;

public interface LetRepository extends JpaRepository<Let, Long> {

	@Query("select let from Let let where lower(:query) like lower(let.pocetnaDestinacija) or lower(:query) like lower(let.krajnjaDestinacija) or "
			+ "lower(:query) like lower(let.duzinaLeta) or cast(:query as integer) = cena or lower(:query) like lower(let.avion.naziv)")
	List<Let> findByAny(String query);
	
	@Query("select let from Let let where let.avion.kapacitet >= let.prodateKarte")
	List<Let> findNotFull();
	
	@Query("select let from Let let where let.avion.id = :id")
	List<Let> findByAvion(long id);
}
