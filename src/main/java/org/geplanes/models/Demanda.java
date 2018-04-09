package org.geplanes.models;

import java.util.Date;

public interface Demanda {
	public Integer getId();
	public String getNome();
	public String getResumo();
	public String getResponsavel();
	public Date getDataLimite();
	
	public UnidadeGerencial getUnidade();
}
