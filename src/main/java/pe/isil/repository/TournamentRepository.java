package pe.isil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.isil.model.Tournament;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {
}
