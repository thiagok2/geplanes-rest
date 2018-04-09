package org.geplanes.repository;

import java.util.List;

import org.geplanes.models.UnidadeGerencial;
import org.geplanes.repository.custom.UnidadeGerencialRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadeGerencialRepository 
	extends JpaRepository<UnidadeGerencial, Integer>, UnidadeGerencialRepositoryCustom{
	
	public List<UnidadeGerencial> findByNomeLikeIgnoreCase(String nome);
	
	public List<UnidadeGerencial> findBySigla(String sigla);
	
	public List<UnidadeGerencial> findByUnidadeSuperiorId(Integer id);
	
	public Long countByUnidadeSuperiorId(Integer idUnidadeSuperior);
	
	@Query("SELECT u FROM UnidadeGerencial u "
			+ "INNER JOIN u.planoGestao p "
			+ "INNER JOIN u.nivelHierarquico n "
			+ "WHERE p.anoExercicio = :ano AND n.id = 4 AND u.permitirmapaestrategico = 'FALSE' AND u.unidadeSuperior is not null")
	public UnidadeGerencial findUnidadeReferenciaAno(@Param("ano") Integer ano);
	
	
	@Query("SELECT u FROM UnidadeGerencial u "
			+ "INNER JOIN u.planoGestao p "
			+ "INNER JOIN u.nivelHierarquico n "
			+ "WHERE u.id = 1305")
	public UnidadeGerencial findUnidadeReferenciaAno();
	
}
