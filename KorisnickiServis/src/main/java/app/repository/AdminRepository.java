package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import app.entities.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long>{
	
	@Query("select uc from Admin uc where lower(uc.email) like lower(:email)")
	Admin findByEmail(String email);
	
	@Query("select case when count(uc)>0 then true else false end from Admin uc where lower(uc.email) like lower(:email)")
	boolean existsByEmail(String email);
}
