package org.geplanes.validators;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.geplanes.models.Demanda;
import org.geplanes.validators.enums.FasePendencia;
import org.geplanes.validators.enums.NivelPendencia;

@SuppressWarnings("rawtypes")
public class ValidationResult {
	
	private List<Pendencia> pendencias = new ArrayList<Pendencia>();
	
	private Integer countPlanejamento = 0;
	private Integer countPrazo = 0;
	private Integer countResultados = 0;
	
	private Integer countInfo = 0;
	private Integer countAviso = 0;
	private Integer countAlerta = 0;
	
	
	
	public void addPendencia(Pendencia p){
		pendencias.add(p);
		
		if(p.getFase().equals(FasePendencia.PLANEJAMENTO))
			countPlanejamento++;
		if(p.getFase().equals(FasePendencia.PRAZO))
			countPrazo++;
		if(p.getFase().equals(FasePendencia.RESULTADOS))
			countResultados++;
		
		if(p.isInfo())
			countInfo++;
		if(p.isAviso())
			countAviso++;
		if(p.isAlerta())
			countAlerta++;
	}
	
	@SuppressWarnings("unchecked")
	public void addPendencia(String titulo, String mensagem, NivelPendencia nivelPendencia, 
			FasePendencia fase, Date data, Demanda demanda){
		Pendencia p =  new Pendencia(titulo, mensagem, nivelPendencia, fase, data, demanda);
		addPendencia(p);
	}
	
	public void removePendencia(Pendencia p){
		pendencias.remove(p);
	}
	
	public List<Pendencia> getPendencias(){
		return pendencias;
	}
	
	public boolean hasPendencias(){
		return !pendencias.isEmpty();
	}

	public Integer getCountPlanejamento() {
		return countPlanejamento;
	}

	public Integer getCountPrazo() {
		return countPrazo;
	}

	public Integer getCountResultados() {
		return countResultados;
	}

	public Integer getCountInfo() {
		return countInfo;
	}

	public Integer getCountAviso() {
		return countAviso;
	}

	public Integer getCountAlerta() {
		return countAlerta;
	}
	
	public Integer countPendencias(){
		return pendencias.size();
	}
	
	
}
