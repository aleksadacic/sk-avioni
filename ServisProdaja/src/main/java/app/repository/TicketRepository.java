package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import app.entities.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long>{

	@Query("select ticket from Ticket ticket where ticket.userId = :userId")
	List<Ticket> findTicketsById(long userId);
	
}
