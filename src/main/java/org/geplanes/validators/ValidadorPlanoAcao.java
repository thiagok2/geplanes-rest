package org.geplanes.validators;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

import org.geplanes.models.PlanoAcao;
import org.geplanes.validators.enums.FasePendencia;
import org.geplanes.validators.enums.NivelPendencia;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPlanoAcao implements ValidadorGeplanes<PlanoAcao>{

	@Value("${planoacao.texto}")
	private String oquePadrao;
	
	@Value("${planoacao.como}")
	private String comoPadrao;
	
	@Value("${planoacao.porque}")
	private String porquePadrao;
	
	@Value("${planoacao.quem}")
	private String quemPadrao;
	
	@Override
	public ValidationResult validate(PlanoAcao planoAcao) {
		ValidationResult validationResult = new ValidationResult();
		
		if(planoAcao.getOQue().equals(oquePadrao) || planoAcao.getOQue() == null || planoAcao.getOQue().trim().length() == 0)
			validationResult.addPendencia(new Pendencia<PlanoAcao>(
					"",
					"Texto do campo OQUE não definido.", NivelPendencia.ALERTA, FasePendencia.PLANEJAMENTO
					,planoAcao.getUnidade().getPlanoGestao().getDataLimitelancamentoResultados1t()
					,planoAcao));
		
		if(planoAcao.getComo().equals(comoPadrao) || planoAcao.getComo() == null || planoAcao.getComo().trim().length() == 0)
				validationResult.addPendencia(new Pendencia<PlanoAcao>(
					"",
					"Texto do campo COMO não definido.", NivelPendencia.ALERTA, FasePendencia.PLANEJAMENTO
					,planoAcao.getUnidade().getPlanoGestao().getDataLimitelancamentoResultados1t()
					,planoAcao));
		
		if(planoAcao.getPorque().equals(porquePadrao) || planoAcao.getPorque() == null || planoAcao.getPorque().trim().length() == 0)
			validationResult.addPendencia(new Pendencia<PlanoAcao>(
					"",
					"Texto do campo PORQUE não definido.", NivelPendencia.ALERTA, FasePendencia.PLANEJAMENTO
					,planoAcao.getUnidade().getPlanoGestao().getDataLimitelancamentoResultados1t()
					, planoAcao));
		
		if(planoAcao.getQuem().equals(quemPadrao) || planoAcao.getQuem() == null || planoAcao.getQuem().trim().length() == 0)
			validationResult.addPendencia(new Pendencia<PlanoAcao>(
					""
					, "Texto do campo QUEM não definido.", NivelPendencia.ALERTA, FasePendencia.PLANEJAMENTO
					,planoAcao.getUnidade().getPlanoGestao().getDataLimitelancamentoResultados1t()
					, planoAcao));
		
		if(planoAcao.isAtrasada()){
			Calendar cal = Calendar.getInstance();
			cal.setTime(planoAcao.getDataLimite());
			LocalDate localData8 = LocalDate.of(cal.get(Calendar.YEAR),
			        cal.get(Calendar.MONTH) + 1,
			        cal.get(Calendar.DAY_OF_MONTH));
			
			
			DateTimeFormatter formatadorBarra = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			
			long diferencaEmDias = ChronoUnit.DAYS.between(localData8 , LocalDate.now());
			
			
			validationResult.addPendencia(new Pendencia<PlanoAcao>(
					"Plano de ação atrasado",
					"Status atual: "+ planoAcao.getStatusPlanoAcao() + ". Data planejada: " +  localData8.format(formatadorBarra)+
					" Conclusão atrasada há " + diferencaEmDias + " dias."
					, NivelPendencia.ALERTA, FasePendencia.PRAZO
					, planoAcao.getDataLimite()
					, planoAcao));
		}
			
			
		return validationResult;
	}

}
