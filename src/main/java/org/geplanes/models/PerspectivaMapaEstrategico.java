package org.geplanes.models;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.geplanes.models.MapaEstrategico;
import org.geplanes.models.Perspectiva;

@Entity
@Table(name="perspectivamapaestrategico")
public class PerspectivaMapaEstrategico {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="perspectiva_id")
	private Perspectiva perspectiva;
	
	@ManyToOne
	@JoinColumn(name="mapaestrategico_id")
	private MapaEstrategico mapaEstrategico;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Perspectiva getPerspectiva() {
		return perspectiva;
	}

	public void setPerspectiva(Perspectiva perspectiva) {
		this.perspectiva = perspectiva;
	}

	public MapaEstrategico getMapaEstrategico() {
		return mapaEstrategico;
	}

	public void setMapaEstrategico(MapaEstrategico mapaEstrategico) {
		this.mapaEstrategico = mapaEstrategico;
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
		PerspectivaMapaEstrategico other = (PerspectivaMapaEstrategico) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
