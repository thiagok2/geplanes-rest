package org.geplanes.repository;

import org.geplanes.models.MapaEstrategico;
import org.geplanes.repository.custom.MapaEstrategicoRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MapaEstrategicoRepository extends JpaRepository<MapaEstrategico, Integer>, MapaEstrategicoRepositoryCustom {
	public MapaEstrategico findByUnidadeGerencialPlanoGestaoAnoExercicio(Integer ano);

}
