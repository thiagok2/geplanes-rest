package org.geplanes.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.geplanes.models.enums.StatusPlanoAcao;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="planoacao")
public class PlanoAcao implements Demanda{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(name="texto")
	private String oque;
	
	@Column(name="textocomo")
	private String como;
	
	@Column(name="textoporque")
	private String porque;
	
	@Column(name="textoquem")
	private String quem;
	
	private Integer status;//1 - PLANEJADO //2 - CONCLUIDO //0 - ANDAMENTO
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="unidadegerencial_id")
	private UnidadeGerencial unidade;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="iniciativa_id")
	private Iniciativa iniciativa;
	
	@Temporal(TemporalType.DATE)
	@Column(name="dtplano")
	private Date dataPlano;
	
	@Temporal(TemporalType.DATE)
	@Column(name="dtatualizacaostatus")
	private Date dataAtualizacaoStatus;
	
	@Transient
	@JsonIgnore
	public String getNome(){
		return oque;
	}
	
	@Transient
	@JsonIgnore
	public StatusPlanoAcao getStatusPlanoAcao(){
		return StatusPlanoAcao.get(status);
	}
	
	@Transient
	@JsonIgnore
	public boolean isAtrasada(){
		return (dataPlano.before(new Date()) && getStatusPlanoAcao().isAberto());
	}
	
	public boolean isConcluido(){
		return getStatusPlanoAcao().equals(StatusPlanoAcao.CONCLUIDO);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOQue() {
		return oque;
	}

	public void setOQue(String descricao) {
		this.oque = descricao;
	}

	public String getComo() {
		return como;
	}

	public void setComo(String como) {
		this.como = como;
	}

	public String getPorque() {
		return porque;
	}

	public void setPorque(String porque) {
		this.porque = porque;
	}

	public String getQuem() {
		return quem;
	}

	public void setQuem(String quem) {
		this.quem = quem;
	}

	public Date getDataPlano() {
		return dataPlano;
	}

	public void setDataPlano(Date dataPlano) {
		this.dataPlano = dataPlano;
	}

	public Date getDataAtualizacaoStatus() {
		return dataAtualizacaoStatus;
	}

	public void setDataAtualizacaoStatus(Date dataAtualizacaoStatus) {
		this.dataAtualizacaoStatus = dataAtualizacaoStatus;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Iniciativa getIniciativa() {
		return iniciativa;
	}

	public void setIniciativa(Iniciativa iniciativa) {
		this.iniciativa = iniciativa;
	}

	public UnidadeGerencial getUnidade() {
		return unidade;
	}

	public void setUnidade(UnidadeGerencial unidade) {
		this.unidade = unidade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlanoAcao other = (PlanoAcao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String getResumo() {
		return oque;
	}

	@Override
	public String getResponsavel() {
		return quem;
	}

	@Override
	public Date getDataLimite() {
		return dataPlano;
	}
}
