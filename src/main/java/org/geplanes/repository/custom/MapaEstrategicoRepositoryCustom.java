package org.geplanes.repository.custom;

import java.util.List;

import org.geplanes.models.Perspectiva;

public interface MapaEstrategicoRepositoryCustom {
	public List<Perspectiva> findPerspectivasByAno(Integer ano);
	
}
