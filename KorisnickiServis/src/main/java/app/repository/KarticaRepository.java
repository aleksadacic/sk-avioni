package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import app.entities.KreditnaKartica;
import app.entities.UserClient;

public interface KarticaRepository extends JpaRepository<KreditnaKartica, Long>{

	@Query("select uc.kreditneKartice from UserClient uc where uc = :user")
	public List<KreditnaKartica> findByUser(UserClient user);
	
}
