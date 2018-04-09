package org.geplanes.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="planogestao")
public class PlanoGestao {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	private String descricao;
	
	@Column(name="anoexercicio")
	private Integer anoExercicio;
	
	@Temporal(TemporalType.DATE)
	@Column(name="limitecriacaometasindicadores")
	private Date limiteCriacaoMetasIndicadores;
	
	@Column(name="lembretecriacaometasindicadores")
	private Boolean lembreteCriacaoMetasIndicadores;
	
	@Temporal(TemporalType.DATE)
	@Column(name="limitecriacaomapanegocio")
	private Date limiteCriacaoMapaNegocio;
	
	@Temporal(TemporalType.DATE)
	@Column(name="limitecriacaomapaestrategico")
	private Date limiteCriacaoMapaEstrategico;
	
	@Temporal(TemporalType.DATE)
	@Column(name="dtlimlancres1t")
	private Date dataLimitelancamentoResultados1t;
	
	@Temporal(TemporalType.DATE)
	@Column(name="dtlimlancres2t")
	private Date dataLimitelancamentoResultados2t;
	
	@Temporal(TemporalType.DATE)
	@Column(name="dtlimlancres3t")
	private Date dataLimitelancamentoResultados3t;
	
	@Temporal(TemporalType.DATE)
	@Column(name="dtlimlancres4t")
	private Date dataLimitelancamentoResultados4t;
	
	@Temporal(TemporalType.DATE)
	@Column(name="dttravlancres1t")
	private Date dataTravarLancamentoResultados1t;
	
	@Temporal(TemporalType.DATE)
	@Column(name="dttravlancres2t")
	private Date dataTravarLancamentoResultados2t;
	
	@Temporal(TemporalType.DATE)
	@Column(name="dttravlancres3t")
	private Date dataTravarLancamentoResultados3t;
	
	@Temporal(TemporalType.DATE)
	@Column(name="dttravlancres4t")
	private Date dataTravarLancamentoResultados4t;
	
	@Temporal(TemporalType.DATE)
	@Column(name="limitecriacaomapacompetencia")
	private Date dataLimiteCriacaoMapaCompetencia;
	
	@Temporal(TemporalType.DATE)
	@Column(name="limitecriacaomatrizfcs")
	private Date dataLimiteCriacaoMatrizFCS;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getAnoExercicio() {
		return anoExercicio;
	}

	public void setAnoExercicio(Integer anoExercicio) {
		this.anoExercicio = anoExercicio;
	}

	public Date getLimiteCriacaoMetasIndicadores() {
		return limiteCriacaoMetasIndicadores;
	}

	public void setLimiteCriacaoMetasIndicadores(Date limiteCriacaoMetasIndicadores) {
		this.limiteCriacaoMetasIndicadores = limiteCriacaoMetasIndicadores;
	}

	public Boolean getLembreteCriacaoMetasIndicadores() {
		return lembreteCriacaoMetasIndicadores;
	}

	public void setLembreteCriacaoMetasIndicadores(Boolean lembreteCriacaoMetasIndicadores) {
		this.lembreteCriacaoMetasIndicadores = lembreteCriacaoMetasIndicadores;
	}

	public Date getLimiteCriacaoMapaNegocio() {
		return limiteCriacaoMapaNegocio;
	}

	public void setLimiteCriacaoMapaNegocio(Date limiteCriacaoMapaNegocio) {
		this.limiteCriacaoMapaNegocio = limiteCriacaoMapaNegocio;
	}

	public Date getLimiteCriacaoMapaEstrategico() {
		return limiteCriacaoMapaEstrategico;
	}

	public void setLimiteCriacaoMapaEstrategico(Date limiteCriacaoMapaEstrategico) {
		this.limiteCriacaoMapaEstrategico = limiteCriacaoMapaEstrategico;
	}

	public Date getDataLimitelancamentoResultados1t() {
		return dataLimitelancamentoResultados1t;
	}

	public void setDataLimitelancamentoResultados1t(Date dataLimitelancamentoResultados1t) {
		this.dataLimitelancamentoResultados1t = dataLimitelancamentoResultados1t;
	}

	public Date getDataLimitelancamentoResultados2t() {
		return dataLimitelancamentoResultados2t;
	}

	public void setDataLimitelancamentoResultados2t(Date dataLimitelancamentoResultados2t) {
		this.dataLimitelancamentoResultados2t = dataLimitelancamentoResultados2t;
	}

	public Date getDataLimitelancamentoResultados3t() {
		return dataLimitelancamentoResultados3t;
	}

	public void setDataLimitelancamentoResultados3t(Date dataLimitelancamentoResultados3t) {
		this.dataLimitelancamentoResultados3t = dataLimitelancamentoResultados3t;
	}

	public Date getDataLimitelancamentoResultados4t() {
		return dataLimitelancamentoResultados4t;
	}

	public void setDataLimitelancamentoResultados4t(Date dataLimitelancamentoResultados4t) {
		this.dataLimitelancamentoResultados4t = dataLimitelancamentoResultados4t;
	}

	public Date getDataTravarLancamentoResultados1t() {
		return dataTravarLancamentoResultados1t;
	}

	public void setDataTravarLancamentoResultados1t(Date dataTravarLancamentoResultados1t) {
		this.dataTravarLancamentoResultados1t = dataTravarLancamentoResultados1t;
	}

	public Date getDataTravarLancamentoResultados2t() {
		return dataTravarLancamentoResultados2t;
	}

	public void setDataTravarLancamentoResultados2t(Date dataTravarLancamentoResultados2t) {
		this.dataTravarLancamentoResultados2t = dataTravarLancamentoResultados2t;
	}

	public Date getDataTravarLancamentoResultados3t() {
		return dataTravarLancamentoResultados3t;
	}

	public void setDataTravarLancamentoResultados3t(Date dataTravarLancamentoResultados3t) {
		this.dataTravarLancamentoResultados3t = dataTravarLancamentoResultados3t;
	}

	public Date getDataTravarLancamentoResultados4t() {
		return dataTravarLancamentoResultados4t;
	}

	public void setDataTravarLancamentoResultados4t(Date dataTravarLancamentoResultados4t) {
		this.dataTravarLancamentoResultados4t = dataTravarLancamentoResultados4t;
	}

	public Date getDataLimiteCriacaoMapaCompetencia() {
		return dataLimiteCriacaoMapaCompetencia;
	}

	public void setDataLimiteCriacaoMapaCompetencia(Date dataLimiteCriacaoMapaCompetencia) {
		this.dataLimiteCriacaoMapaCompetencia = dataLimiteCriacaoMapaCompetencia;
	}

	public Date getDataLimiteCriacaoMatrizFCS() {
		return dataLimiteCriacaoMatrizFCS;
	}

	public void setDataLimiteCriacaoMatrizFCS(Date dataLimiteCriacaoMatrizFCS) {
		this.dataLimiteCriacaoMatrizFCS = dataLimiteCriacaoMatrizFCS;
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
		PlanoGestao other = (PlanoGestao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PlanoGestao [id=" + id + ", descricao=" + descricao + ", anoExercicio=" + anoExercicio
				+ ", limiteCriacaoMetasIndicadores=" + limiteCriacaoMetasIndicadores
				+ ", lembreteCriacaoMetasIndicadores=" + lembreteCriacaoMetasIndicadores + ", limiteCriacaoMapaNegocio="
				+ limiteCriacaoMapaNegocio + ", limiteCriacaoMapaEstrategico=" + limiteCriacaoMapaEstrategico
				+ ", dataLimitelancamentoResultados1t=" + dataLimitelancamentoResultados1t
				+ ", dataLimitelancamentoResultados2t=" + dataLimitelancamentoResultados2t
				+ ", dataLimitelancamentoResultados3t=" + dataLimitelancamentoResultados3t
				+ ", dataLimitelancamentoResultados4t=" + dataLimitelancamentoResultados4t
				+ ", dataTravarLancamentoResultados1t=" + dataTravarLancamentoResultados1t
				+ ", dataTravarLancamentoResultados2t=" + dataTravarLancamentoResultados2t
				+ ", dataTravarLancamentoResultados3t=" + dataTravarLancamentoResultados3t
				+ ", dataTravarLancamentoResultados4t=" + dataTravarLancamentoResultados4t
				+ ", dataLimiteCriacaoMapaCompetencia=" + dataLimiteCriacaoMapaCompetencia
				+ ", dataLimiteCriacaoMatrizFCS=" + dataLimiteCriacaoMatrizFCS + "]";
	}
	
	
}
