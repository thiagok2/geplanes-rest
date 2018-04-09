package org.geplanes.repository.custom;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.geplanes.models.UnidadeGerencial;
import org.geplanes.models.Usuario;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class UnidadeGerencialRepositoryImpl implements UnidadeGerencialRepositoryCustom {

	@PersistenceContext
	EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public List<Usuario> findUsuariosByUnidade(UnidadeGerencial unidade) {
		
		return entityManager.createQuery("SELECT u FROM Usuario u INNER JOIN u.unidades unidade WHERE unidade.id = :unidadeId ORDER BY u.nome")
			.setParameter("unidadeId",  unidade.getId()).getResultList();
		
	}
	
	@SuppressWarnings("unchecked")
	public List<String> findResponsaveisUnidade(UnidadeGerencial unidade){
		Query query = entityManager.createQuery("SELECT DISTINCT i.responsavel FROM Indicador i INNER JOIN i.unidade WHERE i.unidade.id = :pUnidadeId").setParameter("pUnidadeId", unidade.getId());
		
		return query.getResultList();		

	}
	
	@Override
	public  List<JSONObject> dataChartAcompanhamentosBimestre(Integer ano, UnidadeGerencial unidade) throws JSONException {
		String sql = "SELECT "
				+ "(date_part('year', ac.datainicial) || '-') || date_part('quarter', ac.datainicial) AS bimestre,"
				+ "count(*) AS total, "
				+ "sum(case when ac.valorreal is not null then 1 else 0 end) as com_valor "
				+ "FROM acompanhamentoindicador ac "
				+ "JOIN indicador i ON i.id = ac.indicador_id "
				+ "JOIN unidadegerencial u ON u.id = i.unidadegerencial_id "
				+ "WHERE ac.naoaplicavel = false AND date_part('year', ac.datainicial) = :pAno "
				+ "AND u.id = :pUnidadeId "
				+ "GROUP BY ((date_part('year', ac.datainicial) || '-') || date_part('quarter', ac.datainicial)) "
				+ "ORDER BY ((date_part('year', ac.datainicial) || '-') || date_part('quarter', ac.datainicial))";
		
		
		@SuppressWarnings("unchecked")
		List<Object[]> listObjs = entityManager.createNativeQuery(sql)
				.setParameter("pAno", ano)
				.setParameter("pUnidadeId", unidade.getId())
				.getResultList();
		
		
		List<JSONObject> jsonObjects = new ArrayList<JSONObject>();
		
		for (Object[] obj : listObjs) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("bimestre", obj[0]);
			jsonObject.put("total", obj[1]);
			jsonObject.put("com_valor", obj[2]);
			jsonObjects.add(jsonObject);
		}
		
		
		return jsonObjects;
	}

	@Override
	public List<JSONObject> dataChartAcoesBimestre(Integer ano, UnidadeGerencial unidade) throws JSONException {
		String sql = "SELECT "
				+ "(date_part('year', dtplano) || '-') || date_part('quarter', dtplano) AS bimestre, "
				+ "count(*) AS total, "
				+ "sum(case when p.status = 2 then 1 else 0 end) as concluido "
				+ "FROM planoacao p "
				+ "JOIN unidadegerencial u ON u.id = p.unidadegerencial_id  "
				+ "WHERE date_part('year', dtplano) = :pAno "
				+ "AND u.id = :pUnidadeId "
				+ "GROUP BY u.sigla, ((date_part('year', dtplano) || '-') || date_part('quarter', dtplano)) "
				+ "ORDER BY ((date_part('year', dtplano) || '-') || date_part('quarter', dtplano))";
		
	
		@SuppressWarnings("unchecked")
		List<Object[]> listObjs = entityManager.createNativeQuery(sql)
				.setParameter("pAno", ano)
				.setParameter("pUnidadeId", unidade.getId())
				.getResultList();
		
		
		List<JSONObject> jsonObjects = new ArrayList<JSONObject>();
		
		for (Object[] obj : listObjs) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("bimestre", obj[0]);
			jsonObject.put("total", obj[1]);
			jsonObject.put("concluido", obj[2]);
			jsonObjects.add(jsonObject);
		}
		
		return jsonObjects;
	}
	

}
