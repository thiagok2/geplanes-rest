package org.geplanes.repository;

import java.util.List;

import org.geplanes.models.Indicador;
import org.geplanes.models.UnidadeGerencial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("indicadorRepository")
public interface IndicadorRepository extends JpaRepository<Indicador, Integer> {
	public List<Indicador> findByUnidade(UnidadeGerencial unidade);
	
	public List<Indicador> findByUnidadeId(Integer unidadeId);
	
	public List<Indicador> findByUnidadeAndResponsavelIgnoreCaseContaining(UnidadeGerencial unidade, String responsavel);
	
	public List<Indicador> findByUnidadeIdAndPerspectivaId(Integer unidadeId, Integer pespectivaId);
	
	public Indicador findByNomeAndUnidadeId(String nome, Integer unidadeReferenciaId);
	
	public List<Indicador> findIndicadorByUnidadeUnidadeSuperiorId(Integer idUnidadeSuperior);
	
	public List<Indicador> findByNomeAndUnidadePlanoGestaoAnoExercicio(String nome, Integer ano);
	
	
}
