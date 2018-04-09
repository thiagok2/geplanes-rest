package org.geplanes.repository.custom;

import java.util.List;

import org.geplanes.models.UnidadeGerencial;
import org.geplanes.models.Usuario;
import org.json.JSONException;
import org.json.JSONObject;

public interface UnidadeGerencialRepositoryCustom {
	public List<Usuario> findUsuariosByUnidade(UnidadeGerencial unidade);
	
	public List<String> findResponsaveisUnidade(UnidadeGerencial unidade);
	
	public List<JSONObject> dataChartAcompanhamentosBimestre(Integer ano, UnidadeGerencial unidade) throws JSONException;
	
	public List<JSONObject> dataChartAcoesBimestre(Integer ano, UnidadeGerencial unidade) throws JSONException;
	
}
