package org.geplanes.services;

import java.util.List;

import org.geplanes.models.Indicador;
import org.geplanes.models.ResumoIndicador;
import org.geplanes.repository.IndicadorRepository;
import org.geplanes.validators.ValidadorIndicador;
import org.geplanes.validators.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import scala.annotation.meta.setter;

@Service
public class IndicadorServiceImpl implements IndicadorService {

	@Autowired
	IndicadorRepository indicadorRepository;
	
	@Autowired
	ValidadorIndicador validadorIndicador;
	
	@Override
	public List<Indicador> findFromIndicador(Indicador indicadorReferencia) {
		List<Indicador> indicadores = indicadorRepository.findByNomeAndUnidadePlanoGestaoAnoExercicio(
				indicadorReferencia.getNome(), indicadorReferencia.getAno());
		
		indicadores.removeIf(i -> i.getId() == indicadorReferencia.getId());
		return indicadores;
	}

	@Override
	public ResumoIndicador getResumoIndicadorReferencia(Indicador indicadorReferencia) {
		List<Indicador> indicadores = findFromIndicador(indicadorReferencia);
		
		ResumoIndicador resumo = new ResumoIndicador(indicadorReferencia);
		resumo.setCountUnidades(indicadores.stream().count());
		resumo.setCountEmAndamento(indicadores.stream().filter(i -> i.isAndamento()).count());
		resumo.setCountEmPlanejamento(indicadores.stream().filter(i -> i.isPlanejamento()).count());
		resumo.setCountConcluidos(indicadores.stream().filter(i -> i.isConcluido()).count());
		resumo.setCountAtrasados(indicadores.stream().filter(i -> i.isAtrasado()).count());
		
		resumo.setCountConcluidoSucesso(indicadores.stream().filter(i -> i.isConcluidoSucesso()).count());
		resumo.setCountConcluidoInsucesso(indicadores.stream().filter(i -> i.isConcluidoInsucesso()).count());
		
		Double avgReal = indicadores.stream().filter(i -> i.isConcluido()).mapToDouble(i -> i.getValorRealTotal()).average().orElse(0);
		Double totalReal = indicadores.stream().filter(i -> i.isConcluido()).mapToDouble(i -> i.getValorRealTotal()).sum();
		
		
		
		resumo.setAvgValorRealizado(avgReal);
		resumo.setSumValorRealizado(totalReal);
		
		Double avgMetaSuperior = indicadores.stream().filter(i -> i.isConcluido()).mapToDouble(i -> i.getValorMetaSuperiorTotal()).average().orElse(0);
		Double totalMetaSuperior = indicadores.stream().filter(i -> i.isConcluido()).mapToDouble(i -> i.getValorMetaSuperiorTotal()).sum();
		
		resumo.setAvgValorMetaSuperior(avgMetaSuperior);
		resumo.setSumValorMetaSuperior(totalMetaSuperior);
		
		Double avgMetaInferior = indicadores.stream().filter(i -> i.isConcluido()).mapToDouble(i -> i.getValorMetaInferiorTotal()).average().orElse(0);
		Double totalMetaInferior = indicadores.stream().filter(i -> i.isConcluido()).mapToDouble(i -> i.getValorMetaInferiorTotal()).sum();
		
		resumo.setAvgValorMetaInferior(avgMetaInferior);
		resumo.setSumValorMetaInferior(totalMetaInferior);
		
		
		return resumo;
	}

	@Override
	public ValidationResult validarIndicador(Indicador indicador) {
		return validadorIndicador.validate(indicador);
	
	}

}
