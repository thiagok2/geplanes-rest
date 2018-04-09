package org.geplanes.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="nivelhierarquico")
public class NivelHierarquico {

	public static Integer DEPARTAMENTO = 1;
	public static Integer DIRETORIA = 2;
	public static Integer COORDENACAO = 3;
	public static Integer REITORIA = 4;
	public static Integer PROREITORIA = 5;
	public static Integer DIRECAO_GERAL = 7;
	/**
	1;"Departamento"
	2;"Diretoria"
	3;"Coordenação"
	4;"Reitoria"
	5;"Pró-Reitoria"
	7;"Direção Geral"

	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	private String descricao;

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
		NivelHierarquico other = (NivelHierarquico) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NivelHierarquico [id=" + id + ", descricao=" + descricao + "]";
	}
}
