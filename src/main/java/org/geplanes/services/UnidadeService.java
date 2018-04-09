package org.geplanes.services;

import java.util.List;

import org.geplanes.models.ResumoUnidade;
import org.geplanes.models.UnidadeGerencial;
import org.geplanes.validators.Pendencia;

public interface UnidadeService {
	public ResumoUnidade createResumoUnidadeGestora(UnidadeGerencial unidadeGerencial, boolean completo);
	
	public ResumoUnidade createResumoUnidade(UnidadeGerencial unidadeGerencial, boolean comIndicadores, boolean comPendencias, boolean comIniciativas, boolean comPlanosAcao);
	
	public UnidadeGerencial findBySigla(String sigla, Integer ano);
	
	@SuppressWarnings("rawtypes")
	public List<Pendencia> findPendencias(UnidadeGerencial unidadeGerencial, String fase, String nivel);
	
	public List<UnidadeGerencial> findByUnidadeSuperior(UnidadeGerencial unidadeSuperior, boolean campus, boolean reitoria);
}
