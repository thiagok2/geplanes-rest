package org.geplanes.repository;

import java.util.List;

import org.geplanes.models.PlanoAcao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanoAcaoRepository extends JpaRepository<PlanoAcao, Integer> {
	public List<PlanoAcao> findByUnidadeId(Integer unidadeId);
	
	public List<PlanoAcao> findByUnidadeUnidadeSuperiorId(Integer unidadeId);
	
}
