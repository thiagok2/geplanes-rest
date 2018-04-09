package org.geplanes.validators;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;

import org.geplanes.models.AcompanhamentoIndicador;
import org.geplanes.models.Indicador;
import org.geplanes.models.UnidadeGerencial;
import org.geplanes.models.enums.Polaridade;
import org.geplanes.repository.IndicadorRepository;
import org.geplanes.repository.UnidadeGerencialRepository;
import org.geplanes.validators.enums.FasePendencia;
import org.geplanes.validators.enums.NivelPendencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorIndicador implements ValidadorGeplanes<org.geplanes.models.Indicador> {

	@Autowired
	IndicadorRepository indicadorRepository;
	
	@Autowired
	UnidadeGerencialRepository unidadeGerencialRepository;
	
	@Override
	public ValidationResult validate(Indicador indicador) {
		
		ValidationResult validationResult = new ValidationResult();
		Integer anoPlano = indicador.getUnidade().getPlanoGestao().getAnoExercicio();
		
		//UnidadeGerencial unidadeReferencia = unidadeGerencialRepository.findUnidadeReferenciaAno(anoPlano);
		UnidadeGerencial unidadeReferencia = unidadeGerencialRepository.findUnidadeReferenciaAno();
		
		org.geplanes.models.Indicador indicadorReferencia = indicadorRepository.findByNomeAndUnidadeId(indicador.getNome(), unidadeReferencia.getId());
		
		if(indicadorReferencia != null){
			if(indicadorReferencia.getResponsavel().equals(indicador.getResponsavel()))
				validationResult.addPendencia(new Pendencia<Indicador>(
						"Pendência no cadastro de responsável",
						"Valor atual:" + indicador.getResponsavel(), 
						NivelPendencia.AVISO, FasePendencia.PLANEJAMENTO,
						indicador.getUnidade().getPlanoGestao().getDataLimitelancamentoResultados1t()
						,indicador));
		
		
			ArrayList<AcompanhamentoIndicador> acompanhamentos =  new ArrayList<AcompanhamentoIndicador>(indicador.getAcompanhamentos());
			ArrayList<AcompanhamentoIndicador> acompanhamentosReferencia = new ArrayList<AcompanhamentoIndicador>(indicadorReferencia.getAcompanhamentos());
			
			acompanhamentos.sort((a1,a2) -> a2.getIndice().compareTo(a1.getIndice()));
			acompanhamentosReferencia.sort((a1,a2) -> a2.getIndice().compareTo(a1.getIndice()));
			
			for(int i = 0;i<acompanhamentosReferencia.size();i++){
				AcompanhamentoIndicador acomReal = acompanhamentos.get(i);
				AcompanhamentoIndicador acomRef = acompanhamentosReferencia.get(i);
				
				if(acomReal.isAplicavel() && acomReal.getIndicador().getPolaridade().equals(Polaridade.MAIOR_MELHOR) &&
						acomReal.getIndice() == acomRef.getIndice()
						&& acomReal.getValorlimitesuperior() == acomRef.getValorlimitesuperior())
					validationResult.addPendencia(new Pendencia<Indicador>(
							"Pendência no cadastro da Meta ",
							"Valor do campus referência não atualizado. Valor atual: "+ acomReal.getValorlimitesuperior(), 
																	NivelPendencia.AVISO, FasePendencia.PLANEJAMENTO
																	,indicador.getUnidade().getPlanoGestao().getDataLimitelancamentoResultados1t()
																	,indicador));
				
				if(acomReal.isAplicavel() && acomReal.getIndicador().getPolaridade().equals(Polaridade.MENOR_MELHOR) &&
						acomReal.getIndice() == acomRef.getIndice()
						&& acomReal.getValorlimiteinferior() == acomRef.getValorlimiteinferior())
					validationResult.addPendencia(new Pendencia<Indicador>(
							"Pendência no cadastro da Meta ",
							"Valor do campus referência não atualizado. Valor atual: "+ acomReal.getValorlimiteinferior(),
																	NivelPendencia.AVISO, FasePendencia.PLANEJAMENTO, 
																	indicador.getUnidade().getPlanoGestao().getDataLimitelancamentoResultados1t()
																	, acomReal.getIndicador()));
				
				if(acomReal.isAplicavel() && acomReal.getIndicador().getPolaridade().equals(Polaridade.ENTRE_FAIXAS) &&
						acomReal.getIndice() == acomRef.getIndice()
						&& acomReal.getValorlimiteinferior() == acomRef.getValorlimiteinferior()
						&& acomReal.getValorlimitesuperior() == acomRef.getValorlimitesuperior())
					validationResult.addPendencia(new Pendencia<Indicador>(
							"Pendência no cadastras da metas inferior e superior ",
							"Valor das METAS SUPERIOR E INFERIOR não atualizadas. Valores atuais: "+
									acomReal.getValorlimitesuperior() + " e " + acomReal.getValorlimiteinferior() , 
																	NivelPendencia.AVISO, FasePendencia.PLANEJAMENTO
																	,indicador.getUnidade().getPlanoGestao().getDataLimitelancamentoResultados1t()
																	, acomReal.getIndicador()));
				
			}
		}
		
		if(indicador.getCountAcompanhamentosAplicaveis() == 0){
			validationResult.addPendencia(new Pendencia<Indicador>(
						"Indicador sem Metas",
						"Cadastre metas para o Indicador",
						NivelPendencia.ALERTA, 
						FasePendencia.PLANEJAMENTO, 
						indicador.getUnidade().getPlanoGestao().getDataLimitelancamentoResultados1t()
						, indicador));
		}
		
		
		if(indicador.isAtrasado()){
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(indicador.getDataLancamentoAtrasado());
			LocalDate localData8 = LocalDate.of(cal.get(Calendar.YEAR),
			        cal.get(Calendar.MONTH) + 1,
			        cal.get(Calendar.DAY_OF_MONTH));
			
			DateTimeFormatter formatadorBarra = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			
			long diferencaEmDias = ChronoUnit.DAYS.between(localData8 , LocalDate.now());
			
			validationResult.addPendencia(new Pendencia<Indicador>(
					"Atraso no lançamento dos resultados.",
					"Há resultados atrasados de " + localData8.format(formatadorBarra)+
					" Resultados atrasados há " + diferencaEmDias +" dias.", 
					NivelPendencia.ALERTA, FasePendencia.PRAZO
					,indicador.getDataLancamentoAtrasado()
					, indicador));
		}
			
		
		if(indicador.isConcluidoInsucesso())
			validationResult.addPendencia(new Pendencia<Indicador>(
					"Resultados abaixo do esperado.",
					"Valor atual: " + indicador.getValorRealTotal() +
					". Meta: " + indicador.getMetasToText(), 
					NivelPendencia.INFO, FasePendencia.RESULTADOS
					,indicador.getDataLimite()
					,indicador));
	
		
		return validationResult;
	}

}
