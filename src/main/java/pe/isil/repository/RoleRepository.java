package pe.isil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.isil.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
