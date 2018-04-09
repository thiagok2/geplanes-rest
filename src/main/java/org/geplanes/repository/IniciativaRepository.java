package org.geplanes.repository;

import java.util.List;

import org.geplanes.models.Iniciativa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IniciativaRepository extends JpaRepository<Iniciativa, Integer> {
	public List<Iniciativa> findByUnidadeId(Integer unidadeId);
	
	public List<Iniciativa> findByUnidadeIdAndObjetivoMapaEstrategicoObjetivoEstrategicoId(
			Integer unidadeId,
			Integer objetivoEstrategicoId);
	
	public List<Iniciativa> findByUnidadeUnidadeSuperiorId(Integer unidadeSuperiorId);

}
