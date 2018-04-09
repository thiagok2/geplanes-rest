package org.geplanes.services;

import java.util.List;

import org.geplanes.models.Indicador;
import org.geplanes.models.ResumoIndicador;
import org.geplanes.validators.ValidationResult;

public interface IndicadorService {
	public List<Indicador> findFromIndicador(Indicador indicadorReferencia);
	
	public ResumoIndicador getResumoIndicadorReferencia(Indicador indicadorReferencia);
	
	public ValidationResult validarIndicador(Indicador indicador);
}
