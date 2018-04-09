package org.geplanes.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.geplanes.util.URLUtil;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="unidadegerencial",schema="public")
public class UnidadeGerencial {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	private String nome;
	
	private String sigla;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="subordinacao_id")
	private UnidadeGerencial unidadeSuperior;
	
	@ManyToOne
	@JoinColumn(name="planogestao_id")
	private PlanoGestao planoGestao;
	
	@ManyToOne
	@JoinColumn(name="nivelhierarquico_id")
	private NivelHierarquico nivelHierarquico;
	
	private boolean permitirmapaestrategico;
	
	private Integer nivelnum;
	
	@Transient
	public String getDescricaoURL(){
		return URLUtil.toURL(sigla);
	}
	
	
	@Transient
	public boolean isProReitoria(){
		return nivelHierarquico.getId() == NivelHierarquico.PROREITORIA;
	}
	
	@Transient
	public boolean isGestora(){
		return unidadeSuperior == null;
	}
	
	@Transient
	public boolean isCampus(){
		return unidadeSuperior != null && unidadeSuperior.isGestora() && nivelHierarquico.getId() == NivelHierarquico.DIRECAO_GERAL &&
				(nome.toUpperCase().contains("CAMPUS") || nome.toUpperCase().contains("CÂMPUS"));
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public UnidadeGerencial getUnidadeSuperior() {
		return unidadeSuperior;
	}

	public void setUnidadeSuperior(UnidadeGerencial unidadeSuperior) {
		this.unidadeSuperior = unidadeSuperior;
	}

	public PlanoGestao getPlanoGestao() {
		return planoGestao;
	}

	public void setPlanoGestao(PlanoGestao planoGestao) {
		this.planoGestao = planoGestao;
	}

	public NivelHierarquico getNivelHierarquico() {
		return nivelHierarquico;
	}

	public void setNivelHierarquico(NivelHierarquico nivelHierarquico) {
		this.nivelHierarquico = nivelHierarquico;
	}
	
	public boolean isPermitirmapaestrategico() {
		return permitirmapaestrategico;
	}

	public boolean getPermitirmapaestrategico() {
		return permitirmapaestrategico;
	}

	public void setPermitirmapaestrategico(boolean permitirmapaestrategico) {
		this.permitirmapaestrategico = permitirmapaestrategico;
	}

	public Integer getNivelnum() {
		return nivelnum;
	}

	public void setNivelnum(Integer nivelnum) {
		this.nivelnum = nivelnum;
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
		UnidadeGerencial other = (UnidadeGerencial) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UnidadeGerencial [id=" + id + ", nome=" + nome + ", sigla=" + sigla + ", unidadeSuperior="
				+ unidadeSuperior + ", planoGestao=" + planoGestao + ", nivelHierarquico=" + nivelHierarquico
				+ ", permitirmapaestrategico=" + permitirmapaestrategico + ", nivelnum=" + nivelnum + "]";
	}

}
