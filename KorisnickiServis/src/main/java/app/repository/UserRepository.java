package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import app.entities.RankKorisnika;
import app.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	User findByEmail(String email);
	
	boolean existsByEmail(String email);
	
	@Query("select u.rankKorisnika from User u where u = :user")
	RankKorisnika findRankKorisnika(User user);
}
