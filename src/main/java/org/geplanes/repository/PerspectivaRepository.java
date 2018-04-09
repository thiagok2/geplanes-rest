package org.geplanes.repository;

import org.geplanes.models.Perspectiva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("perspectivaDAO")
public interface PerspectivaRepository extends JpaRepository<Perspectiva, Integer> {

}
