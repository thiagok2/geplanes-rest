package org.geplanes.repository.custom;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.geplanes.models.Perspectiva;

public class MapaEstrategicoRepositoryImpl implements MapaEstrategicoRepositoryCustom {

	@PersistenceContext
	EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Perspectiva> findPerspectivasByAno(Integer ano) {
		String sqlString = "SELECT perspectiva.id, perspectiva.descricao FROM mapaestrategico, perspectiva, "
				+ "perspectivamapaestrategico, "
				+ "planogestao, "
				+ "unidadegerencial WHERE "
				+ "mapaestrategico.unidadegerencial_id = unidadegerencial.id AND "
				+ "perspectivamapaestrategico.mapaestrategico_id = mapaestrategico.id AND "
				+ "perspectivamapaestrategico.perspectiva_id = perspectiva.id AND "
				+ "planogestao.id = unidadegerencial.planogestao_id AND "
				+ "planogestao.anoexercicio = :pAno";
		javax.persistence.Query query = entityManager.createNativeQuery(sqlString, Perspectiva.class);
		return query.setParameter("pAno", ano).getResultList();


	}
	
	

}
