package org.geplanes.validators;

import java.util.Date;

import org.geplanes.models.Demanda;
import org.geplanes.models.Indicador;
import org.geplanes.models.PlanoAcao;
import org.geplanes.validators.enums.FasePendencia;
import org.geplanes.validators.enums.NivelPendencia;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Pendencia<T extends Demanda> {
	private String titulo;
	private String mensagem;
	private NivelPendencia nivel;
	private FasePendencia fase;
	private Date dataPendencia;
	private T demanda;
	
	public Pendencia(String titulo, String mensagem, NivelPendencia nivelPendencia, FasePendencia fase, Date data, T demanda){
		this.mensagem = mensagem;
		this.nivel = nivelPendencia;
		this.fase = fase;
		this.titulo = titulo;
		this.demanda = demanda;
		this.dataPendencia = data;
	}
	
	public String getTituloDemanda(){
		return demanda.getNome();
	}
	
	public boolean isIndicador(){
		return demanda.getClass().equals(Indicador.class);
	}
	
	public boolean isPlanoAcao(){
		return demanda.getClass().equals(PlanoAcao.class);
	}
	
	public String getResponsavel() {
		return demanda.getResponsavel();
	}
	
	public String getIdentificador() {
		return demanda.getClass().getSimpleName()+"#"+demanda.getId();
	}
	
	public Integer getDemandaId(){
		return demanda.getId();
	}

	public String getTitulo() {
		return titulo;
	}
	
	public String getMensagem() {
		return mensagem;
	}
	
	public NivelPendencia getNivel() {
		return nivel;
	}
	
	public FasePendencia getFase(){
		return fase;
	}
	
	
	public boolean isAlerta(){
		return nivel == NivelPendencia.ALERTA || nivel == NivelPendencia.ALERTA_MAXIMO;
	}
	
	public boolean isAviso(){
		return nivel == NivelPendencia.AVISO;
	}
	
	public boolean isInfo(){
		return nivel == NivelPendencia.INFO;
	}
	
	@JsonIgnore
	public T getDemanda() {
		return demanda;
	}
	
	public Date getData(){
		return dataPendencia;
	}
	
	public void setData(Date data){
		this.dataPendencia = data;
	}

	
	public String getDemandaResumo(){
		return demanda.getResumo();
	}
	
}
