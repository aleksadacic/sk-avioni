package app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import app.entities.RankKorisnika;
import app.entities.UserClient;

public interface UserRepository extends JpaRepository<UserClient, Long>{
	
	@Query("select uc from UserClient uc where lower(uc.email) like lower(:email)")
	UserClient findByEmail(String email);
	
	@Query("select case when count(uc)>0 then true else false end from UserClient uc where lower(uc.email) like lower(:email)")
	boolean existsByEmail(String email);
	
	@Query("select u.rankKorisnika from UserClient u where u = :user")
	RankKorisnika findRankKorisnika(UserClient user);
	
	@Query("select uc from UserClient uc where uc.id = :id")
	Optional<UserClient> findById(Long id);
}
