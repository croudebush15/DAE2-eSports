package pe.isil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.isil.model.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
}
