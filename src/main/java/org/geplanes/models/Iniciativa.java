package org.geplanes.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class Iniciativa {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	private String descricao;
	
	@ManyToOne
	@JoinColumn(name="unidadegerencial_id")
	private UnidadeGerencial unidade;
	
	@ManyToOne
	@JoinColumn(name="objetivomapaestrategico_id")
	private ObjetivoMapaEstrategico objetivoMapaEstrategico;
	
	@OneToMany
	@JoinColumn(name="iniciativa_id")
	private List<PlanoAcao> planosAcao;
	
	@Transient
	public boolean isAtrasada(){
		return planosAcao != null ? planosAcao.stream().anyMatch(acao -> acao.isAtrasada()):false;
	}
	
	@Transient
	public boolean isConcluida(){
		return planosAcao != null ? planosAcao.stream().allMatch(acao -> acao.isConcluido()):false;
	}
	
	
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

	public UnidadeGerencial getUnidade() {
		return unidade;
	}

	public void setUnidade(UnidadeGerencial unidade) {
		this.unidade = unidade;
	}

	public ObjetivoMapaEstrategico getObjetivoMapaEstrategico() {
		return objetivoMapaEstrategico;
	}

	public void setObjetivoMapaEstrategico(ObjetivoMapaEstrategico objetivoMapaEstrategico) {
		this.objetivoMapaEstrategico = objetivoMapaEstrategico;
	}


	public List<PlanoAcao> getPlanosAcao() {
		return planosAcao;
	}

	public void setPlanosAcao(List<PlanoAcao> planosAcao) {
		this.planosAcao = planosAcao;
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
		Iniciativa other = (Iniciativa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
