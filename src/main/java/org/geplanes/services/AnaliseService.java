package org.geplanes.services;

import java.util.Map;

import org.geplanes.models.UnidadeGerencial;

public interface AnaliseService {
	public Map<String, Integer> analisarDesempenho();
	
	public void create(UnidadeGerencial unidade);
	
}
