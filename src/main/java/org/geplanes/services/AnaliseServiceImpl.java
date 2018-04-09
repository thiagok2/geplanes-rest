package org.geplanes.services;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.geplanes.models.Indicador;
import org.geplanes.models.UnidadeGerencial;
import org.geplanes.models.enums.StatusPrazo;
import org.geplanes.models.enums.StatusResultado;
import org.geplanes.repository.IndicadorRepository;
import org.geplanes.repository.UnidadeGerencialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class AnaliseServiceImpl implements AnaliseService {

	@Autowired
	IndicadorRepository indicadorRepository;
	
	@Autowired
	UnidadeGerencialRepository unidadeGerencialRepository;
	
	private List<Indicador> indicadores;
	
	private UnidadeGerencial unidade;
	
	
	public void create(UnidadeGerencial unidadeGerencial){
		this.unidade = unidadeGerencial;
		this.indicadores = indicadorRepository.findByUnidade(unidade);
	}
	
	
	@Override
	public Map<String, Integer> analisarDesempenho() {
		
		Integer countIndicadores = indicadores.size();
		Integer countConcluidos =  (int)indicadores.stream().filter(i -> i.getStatusPrazo().equals(StatusPrazo.CONCLUIDO)).count();
		Integer countConcluidosComSucesso = (int)indicadores.stream().filter(i -> i.getStatusResultados().isSucesso()).count();
		Integer countAbaixoMeta = (int)indicadores.stream().filter(i -> i.getStatusResultados().isInsucesso()).count();
		Integer countAtrasados = (int)indicadores.stream().filter(i -> i.getStatusPrazo().isAtrasado()).count();
		Integer countAbertosAndamento = (int)indicadores.stream().filter(i -> i.getStatusPrazo().isAberto()).count();
		Integer countPendenciasResultados = (int)indicadores.stream().filter(i -> i.getStatusPrazo().equals(StatusPrazo.INDETERMINADO) 
									|| i.getStatusResultados().equals(StatusResultado.INDETERMINADO)).count();
		
		Map<String, Integer> resumoIndicadores = new LinkedHashMap <String, Integer>();
		resumoIndicadores.put("countIndicadores", countIndicadores);
		resumoIndicadores.put("countConcluidos", countConcluidos);
		resumoIndicadores.put("countConcluidosComSucesso", countConcluidosComSucesso);
		resumoIndicadores.put("countAbaixoMeta", countAbaixoMeta);
		resumoIndicadores.put("countAtrasados", countAtrasados);
		resumoIndicadores.put("countAbertosAndamento", countAbertosAndamento);
		resumoIndicadores.put("countPendenciasResultados", countPendenciasResultados);
		
		
		UnidadeGerencial unidadeReferencia = unidadeGerencialRepository.findUnidadeReferenciaAno(unidade.getPlanoGestao().getAnoExercicio());
		
		if(unidadeReferencia != null){
			Integer countPendenciasCadastro = (int)indicadores.stream().filter(i -> validarDefinicaoIndicador(i, 
					indicadorRepository.findByNomeAndUnidadeId(i.getNome(), unidadeReferencia.getId())))
							.count();

			resumoIndicadores.put("countPendenciasCadastro", countPendenciasCadastro);
		}
		
		
		return resumoIndicadores;
	}
	
	private boolean validarDefinicaoIndicador(Indicador indicadorUnidade, Indicador indicadorReferencia){
		
		if(indicadorReferencia != null){
			if(indicadorUnidade.getResponsavel() != null && indicadorUnidade.getResponsavel().equals(indicadorReferencia.getResponsavel()))
				return false;
			
			if(indicadorUnidade.getValorMetaInferiorTotal() != null & indicadorUnidade.getValorMetaInferiorTotal().equals(indicadorReferencia.getValorMetaInferiorTotal()))
				return false;
			
			if(indicadorUnidade.getValorMetaSuperiorTotal() != null & indicadorUnidade.getValorMetaSuperiorTotal().equals(indicadorReferencia.getValorMetaSuperiorTotal()))
				return false;
		}
		return true;
		
	}
}
