package org.geplanes.repository;

import java.util.List;

import org.geplanes.models.ObjetivoEstrategico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("objetivoEstrategicoDAO")
public interface ObjetivoEstrategicoRepository extends JpaRepository<ObjetivoEstrategico, Integer>{
	@Query(value="SELECT objetivoestrategico.id, objetivoestrategico.descricao "
			+ "FROM "
			+ "objetivoestrategico, "
			+ "public.objetivomapaestrategico, "
			+ "public.perspectivamapaestrategico, "
			+ "public.perspectiva, "
			+ "public.mapaestrategico, "
			+ "public.planogestao, "
			+ "public.unidadegerencial "
			+ "WHERE "
			+ "objetivomapaestrategico.objetivoestrategico_id = objetivoestrategico.id AND "
			+ "objetivomapaestrategico.perspectivamapaestrategico_id = perspectivamapaestrategico.id AND "
			+ "perspectivamapaestrategico.mapaestrategico_id = mapaestrategico.id AND "
			+ "perspectivamapaestrategico.perspectiva_id = perspectiva.id AND "
			+ "mapaestrategico.unidadegerencial_id = unidadegerencial.id AND "
			+ "unidadegerencial.planogestao_id = planogestao.id AND "
			+ "planogestao.anoexercicio = :pAno", nativeQuery=true)
	public List<ObjetivoEstrategico> findByAno(@Param("pAno")Integer ano);
	
	@Query(value="SELECT objetivoestrategico.id, objetivoestrategico.descricao "
			+ "FROM "
			+ "objetivoestrategico, "
			+ "public.objetivomapaestrategico, "
			+ "public.perspectivamapaestrategico, "
			+ "public.perspectiva, "
			+ "public.mapaestrategico, "
			+ "public.planogestao, "
			+ "public.unidadegerencial "
			+ "WHERE "
			+ "objetivomapaestrategico.objetivoestrategico_id = objetivoestrategico.id AND "
			+ "objetivomapaestrategico.perspectivamapaestrategico_id = perspectivamapaestrategico.id AND "
			+ "perspectivamapaestrategico.mapaestrategico_id = mapaestrategico.id AND "
			+ "perspectivamapaestrategico.perspectiva_id = perspectiva.id AND "
			+ "mapaestrategico.unidadegerencial_id = unidadegerencial.id AND "
			+ "unidadegerencial.planogestao_id = planogestao.id AND "
			+ "perspectiva.id = :pPerspectivaId AND planogestao.anoexercicio = :pAno", nativeQuery=true)
	public List<ObjetivoEstrategico> findByPerspectivaAndAno(@Param("pPerspectivaId") Integer perspectivaId  ,@Param("pAno")Integer ano);
}
