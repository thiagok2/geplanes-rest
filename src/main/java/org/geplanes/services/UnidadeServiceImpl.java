package org.geplanes.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.geplanes.models.Indicador;
import org.geplanes.models.Iniciativa;
import org.geplanes.models.PlanoAcao;
import org.geplanes.models.ResumoUnidade;
import org.geplanes.models.UnidadeGerencial;
import org.geplanes.models.enums.StatusPlanoAcao;
import org.geplanes.repository.IndicadorRepository;
import org.geplanes.repository.IniciativaRepository;
import org.geplanes.repository.PlanoAcaoRepository;
import org.geplanes.repository.UnidadeGerencialRepository;
import org.geplanes.validators.Pendencia;
import org.geplanes.validators.ValidadorIndicador;
import org.geplanes.validators.ValidadorPlanoAcao;
import org.geplanes.validators.ValidationResult;
import org.geplanes.validators.enums.FasePendencia;
import org.geplanes.validators.enums.NivelPendencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UnidadeServiceImpl implements UnidadeService {
	
	@Autowired
	UnidadeGerencialRepository unidadeRepository;
	
	@Autowired
	IndicadorRepository indicadorRepository;
	
	@Autowired
	IniciativaRepository iniciativaRepository;
	
	@Autowired
	PlanoAcaoRepository planoAcaoRepository;
	
	@Autowired
	ValidadorIndicador validadorIndicador;
	
	@Autowired
	ValidadorPlanoAcao validadorPlanoAcao;
	
	public Integer countTotalIndicadores(){
		
		return 0;
	}
	
	public ResumoUnidade createResumoUnidadeGestora(UnidadeGerencial unidadeGerencial, boolean completo){
		
		ResumoUnidade resumo = new ResumoUnidade(unidadeGerencial);
		
		List<Indicador> indicadores = indicadorRepository.findIndicadorByUnidadeUnidadeSuperiorId(unidadeGerencial.getId());
		long countEmAndamento = 0;
		long countEmPlanejamento = 0;
		long countAtrasados = 0;
		long countConcluidos = 0;
		long countConcluidoSucesso = 0;
		long countConcluidoInsucesso = 0;
		
		long countAcompanhamentos = 0;
		long countAcompanhamentosAplicaveis = 0;
		
		for(Indicador i: indicadores){
			if(i.isAndamento()) 
				countEmAndamento++;
			if(i.isPlanejamento()) 
				countEmPlanejamento++;
			if(i.isAtrasado())
				countAtrasados++;
			if(i.isConcluido())
				countConcluidos++;
			if(i.isConcluidoSucesso())
				countConcluidoSucesso++;
			if(i.isConcluidoInsucesso())
				countConcluidoInsucesso++;
			
			countAcompanhamentos += i.getCountAcompanhamentos();
			countAcompanhamentosAplicaveis += i.getCountAcompanhamentosAplicaveis(); 
			
		}
		resumo.setCountEmAndamento(countEmAndamento);
		resumo.setCountEmPlanejamento(countEmPlanejamento);
		resumo.setCountAtrasados(countAtrasados);
		resumo.setCountConcluidos(countConcluidos);
		resumo.setCountConcluidoSucesso(countConcluidoSucesso);
		resumo.setCountConcluidoInsucesso(countConcluidoInsucesso);
		
		resumo.setCountAcompanhamentos(countAcompanhamentos);	
		resumo.setCountAcompanhamentosAplicaveis(countAcompanhamentosAplicaveis);
		
		if(completo){
		
			List<Iniciativa> iniciativas = iniciativaRepository.findByUnidadeUnidadeSuperiorId(unidadeGerencial.getId());
			resumo.setCountIniciativas((long)iniciativas.size());
			
			long countIniciativasConcluidas = 0;
			long countIniciativasAtrasadas = 0;
			long countIniciativasAndamento = 0;
			
			for(Iniciativa i: iniciativas){
				if(i.isConcluida())
					countIniciativasConcluidas++;
				if(i.isAtrasada())
					countIniciativasAtrasadas++;
				if(i.isAtrasada() && !i.isConcluida())
					countIniciativasAndamento++;
					
			}
			resumo.setCountIniciativasConcluidas(countIniciativasConcluidas);
			resumo.setCountIniciativasAtrasadas(countIniciativasAtrasadas);
			resumo.setCountIniciativasAndamento(countIniciativasAndamento);
			
			List<PlanoAcao> planosAcao = planoAcaoRepository.findByUnidadeUnidadeSuperiorId(unidadeGerencial.getId());
			resumo.setCountPlanosAcao((long)planosAcao.size());
			
			long countPlanosAcaoConcluidos = 0;
			long countPlanosAcaoAndamento = 0;
			long countPlanosAcaoPlanejados = 0;
			
			for(PlanoAcao p: planosAcao){
				if(p.isConcluido())
					countPlanosAcaoConcluidos++;
				if(p.getStatusPlanoAcao() == StatusPlanoAcao.ANDAMENTO)
					countPlanosAcaoAndamento++;
				if(p.getStatusPlanoAcao() == StatusPlanoAcao.PLANEJADO)
					countPlanosAcaoPlanejados++;
				
			}
			resumo.setCountPlanosAcaoConcluidos(countPlanosAcaoConcluidos);
			resumo.setCountPlanosAcaoAndamento(countPlanosAcaoAndamento);
			resumo.setCountPlanosAcaoPlanejados(countPlanosAcaoPlanejados);
		}
		
		return resumo;
	}
	
	public ResumoUnidade createResumoUnidade(UnidadeGerencial unidadeGerencial, 
			boolean comIndicadores, boolean comPendencias, boolean comIniciativas, boolean comPlanosAcao){
		
		ResumoUnidade resumo = new ResumoUnidade(unidadeGerencial);
		
		List<Indicador> indicadores = indicadorRepository.findByUnidadeId(unidadeGerencial.getId());
		resumo.setIndicadoresCount((long)indicadores.size());
		
		@SuppressWarnings("rawtypes")
		List<Pendencia> pendencias = new ArrayList<Pendencia>();
		Long countPendenciaPlanejamento = 0l;
		Long countPendenciaPrazo = 0l;
		Long countPendenciaResultados = 0l;
		Long countInfo = 0l;
		Long countAviso = 0l;
		Long countAlerta = 0l;
		
		if(comIndicadores){
			long countEmAndamento = 0;
			long countEmPlanejamento = 0;
			long countAtrasados = 0;
			long countConcluidos = 0;
			long countConcluidoSucesso = 0;
			long countConcluidoInsucesso = 0;
			
			long countAcompanhamentos = 0;
			long countAcompanhamentosAplicaveis = 0;
		
			
			for(Indicador i: indicadores){
				if(i.isAndamento()) 
					countEmAndamento++;
				if(i.isPlanejamento()) 
					countEmPlanejamento++;
				if(i.isAtrasado())
					countAtrasados++;
				if(i.isConcluido())
					countConcluidos++;
				if(i.isConcluidoSucesso())
					countConcluidoSucesso++;
				if(i.isConcluidoInsucesso())
					countConcluidoInsucesso++;
				
				countAcompanhamentos += i.getCountAcompanhamentos();
				countAcompanhamentosAplicaveis += i.getCountAcompanhamentosAplicaveis(); 
				
				if(comPendencias){
					ValidationResult validationResultIndicador = validadorIndicador.validate(i);
					pendencias.addAll(validationResultIndicador.getPendencias());
					countPendenciaPlanejamento+=validationResultIndicador.getCountPlanejamento();
					countPendenciaPrazo+=validationResultIndicador.getCountPrazo();
					countPendenciaResultados+=validationResultIndicador.getCountResultados();
					
					countInfo+=validationResultIndicador.getCountInfo();
					countAviso+=validationResultIndicador.getCountAviso();
					countAlerta+=validationResultIndicador.getCountAlerta();
				}
				
			}
			
			resumo.setCountEmAndamento(countEmAndamento);
			resumo.setCountEmPlanejamento(countEmPlanejamento);
			resumo.setCountAtrasados(countAtrasados);
			resumo.setCountConcluidos(countConcluidos);
			resumo.setCountConcluidoSucesso(countConcluidoSucesso);
			resumo.setCountConcluidoInsucesso(countConcluidoInsucesso);
			
			resumo.setCountAcompanhamentos(countAcompanhamentos);	
			resumo.setCountAcompanhamentosAplicaveis(countAcompanhamentosAplicaveis);
			
			
				
		}
		
		if(comIniciativas){
			List<Iniciativa> iniciativas = iniciativaRepository.findByUnidadeId(unidadeGerencial.getId());
			resumo.setCountIniciativas((long)iniciativas.size());
			
			long countIniciativasConcluidas = 0;
			long countIniciativasAtrasadas = 0;
			long countIniciativasAndamento = 0;
			
			for(Iniciativa i: iniciativas){
				if(i.isConcluida())
					countIniciativasConcluidas++;
				if(i.isAtrasada())
					countIniciativasAtrasadas++;
				if(i.isAtrasada() && !i.isConcluida())
					countIniciativasAndamento++;
				
					
			}
			resumo.setCountIniciativasConcluidas(countIniciativasConcluidas);
			resumo.setCountIniciativasAtrasadas(countIniciativasAtrasadas);
			resumo.setCountIniciativasAndamento(countIniciativasAndamento);
			
		}
		if(comPlanosAcao){
		
			List<PlanoAcao> planosAcao = planoAcaoRepository.findByUnidadeId(unidadeGerencial.getId());
			resumo.setCountPlanosAcao((long)planosAcao.size());
			
			long countPlanosAcaoConcluidos = 0;
			long countPlanosAcaoAndamento = 0;
			long countPlanosAcaoPlanejados = 0;
			
			for(PlanoAcao p: planosAcao){
				if(p.isConcluido())
					countPlanosAcaoConcluidos++;
				if(p.getStatusPlanoAcao() == StatusPlanoAcao.ANDAMENTO)
					countPlanosAcaoAndamento++;
				if(p.getStatusPlanoAcao() == StatusPlanoAcao.PLANEJADO)
					countPlanosAcaoPlanejados++;
				
				if(comPendencias){
					ValidationResult validationResultAcao = validadorPlanoAcao.validate(p);
					pendencias.addAll(validationResultAcao.getPendencias());
					
					countPendenciaPlanejamento+=validationResultAcao.getCountPlanejamento();
					countPendenciaPrazo+=validationResultAcao.getCountPrazo();
					countPendenciaResultados+=validationResultAcao.getCountResultados();
					
					
					countInfo+=validationResultAcao.getCountInfo();
					countAviso+=validationResultAcao.getCountAviso();
					countAlerta+=validationResultAcao.getCountAlerta();
				}
				
			}
			resumo.setCountPlanosAcaoConcluidos(countPlanosAcaoConcluidos);
			resumo.setCountPlanosAcaoAndamento(countPlanosAcaoAndamento);
			resumo.setCountPlanosAcaoPlanejados(countPlanosAcaoPlanejados);
			
		}
		
		if(comPendencias){
			resumo.setCountPendencias((long)pendencias.size());
			resumo.setCountPendenciasPlanejamento(countPendenciaPlanejamento);
			resumo.setCountPendenciasPrazo(countPendenciaPrazo);
			resumo.setCountPendenciasResultado(countPendenciaResultados);
			resumo.setCountPendenciasAlerta(countAlerta);
			resumo.setCountPendenciasInfo(countInfo);
			resumo.setCountPendenciasAviso(countAviso);
			
		}
	
		
		return resumo;
	}
	
	@SuppressWarnings("rawtypes")
	public List<Pendencia> findPendencias(UnidadeGerencial unidadeGerencial, String fase, String nivel){
		List<Indicador> indicadores = indicadorRepository.findByUnidadeId(unidadeGerencial.getId());
		List<PlanoAcao> planosAcao = planoAcaoRepository.findByUnidadeId(unidadeGerencial.getId());
		
		List<Pendencia> pendencias = new ArrayList<Pendencia>();
		
		for(Indicador i: indicadores){
			ValidationResult validationResult = validadorIndicador.validate(i);
			pendencias.addAll(validationResult.getPendencias());
		}
		
		for(PlanoAcao p: planosAcao){
			ValidationResult validationResult =  validadorPlanoAcao.validate(p);
			pendencias.addAll(validationResult.getPendencias());
		}
		
		if(!fase.isEmpty())
			pendencias = pendencias.stream()
				.filter(p -> p.getFase() == FasePendencia.valueOf(fase.toUpperCase())).collect(Collectors.toList());;
		
		
		if(!nivel.isEmpty())
			pendencias = pendencias.stream()
			.filter(p -> p.getNivel() == NivelPendencia.valueOf(nivel.toUpperCase())).collect(Collectors.toList());;
		
		pendencias = pendencias.stream().sorted((p1, p2) -> p1.getData().compareTo(p2.getData())).collect(Collectors.toList());	
			
		return pendencias;
	}

	@Override
	public UnidadeGerencial findBySigla(String sigla, Integer ano) {
		List<UnidadeGerencial> unidades = unidadeRepository.findBySigla(sigla);
		return unidades.stream().filter(u -> u.getPlanoGestao().getAnoExercicio().equals(ano)).collect(Collectors.toList()).get(0);
	}

	@Override
	public List<UnidadeGerencial> findByUnidadeSuperior(UnidadeGerencial unidadeSuperior, boolean incluirCampus,
			boolean incluirProreitorias) {
		List<UnidadeGerencial> unidades = unidadeRepository.findByUnidadeSuperiorId(unidadeSuperior.getId());
		
		if(incluirCampus)
			unidades = unidades.stream().filter(u -> u.isCampus()).collect(Collectors.toList());
		else
			unidades = unidades.stream().filter(u -> !u.isCampus()).collect(Collectors.toList());
		
		if(incluirProreitorias)
			unidades = unidades.stream().filter(u -> u.isProReitoria()).collect(Collectors.toList());
		else
			unidades = unidades.stream().filter(u -> !u.isProReitoria()).collect(Collectors.toList());
		
		return unidades;
	}

}
