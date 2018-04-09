package org.geplanes.models;

public class ResumoIndicador {
	
	private Indicador indicador;
	
	private Long countUnidades;
	
	private Long countEmAndamento;
	
	private Long countEmPlanejamento;
	
	private Long countAtrasados;
	
	private Long countConcluidos;
	
	private Long countConcluidoSucesso;
	
	private Long countConcluidoInsucesso;
	
	private Long countNaoAplicavel;
	
	private Double avgValorRealizado;
	
	private Double sumValorRealizado;
	
	private Double avgValorMetaSuperior;
	
	private Double sumValorMetaSuperior;
	
	private Double avgValorMetaInferior;
	
	private Double sumValorMetaInferior;
	
	public ResumoIndicador(Indicador indicador) {
		this.indicador = indicador;
	}

	public Long getCountUnidades() {
		return countUnidades;
	}

	public void setCountUnidades(Long countUnidades) {
		this.countUnidades = countUnidades;
	}

	public Long getCountEmAndamento() {
		return countEmAndamento;
	}

	public void setCountEmAndamento(Long countEmAndamento) {
		this.countEmAndamento = countEmAndamento;
	}

	public Long getCountEmPlanejamento() {
		return countEmPlanejamento;
	}

	public void setCountEmPlanejamento(Long countEmPlanejamento) {
		this.countEmPlanejamento = countEmPlanejamento;
	}

	public Long getCountAtrasados() {
		return countAtrasados;
	}

	public void setCountAtrasados(Long countAtrasados) {
		this.countAtrasados = countAtrasados;
	}

	public Long getCountConcluidos() {
		return countConcluidos;
	}

	public void setCountConcluidos(Long countConcluidos) {
		this.countConcluidos = countConcluidos;
	}

	public Long getCountConcluidoSucesso() {
		return countConcluidoSucesso;
	}

	public void setCountConcluidoSucesso(Long countConcluidoSucesso) {
		this.countConcluidoSucesso = countConcluidoSucesso;
	}

	public Long getCountConcluidoInsucesso() {
		return countConcluidoInsucesso;
	}

	public void setCountConcluidoInsucesso(Long countConcluidoInsucesso) {
		this.countConcluidoInsucesso = countConcluidoInsucesso;
	}

	public Double getSumValorRealizado() {
		return sumValorRealizado;
	}

	public void setSumValorRealizado(Double sumValorRealizado) {
		this.sumValorRealizado = sumValorRealizado;
	}

	public Indicador getIndicador() {
		return indicador;
	}

	public Long getCountNaoAplicavel() {
		return countNaoAplicavel;
	}

	public void setCountNaoAplicavel(Long countNaoAplicavel) {
		this.countNaoAplicavel = countNaoAplicavel;
	}

	public Double getAvgValorRealizado() {
		return avgValorRealizado;
	}

	public void setAvgValorRealizado(Double avgValorRealizado) {
		this.avgValorRealizado = avgValorRealizado;
	}

	public Double getAvgValorMetaSuperior() {
		return avgValorMetaSuperior;
	}

	public void setAvgValorMetaSuperior(Double avgValorMetaSuperior) {
		this.avgValorMetaSuperior = avgValorMetaSuperior;
	}

	public Double getSumValorMetaSuperior() {
		return sumValorMetaSuperior;
	}

	public void setSumValorMetaSuperior(Double sumValorMetaSuperior) {
		this.sumValorMetaSuperior = sumValorMetaSuperior;
	}

	public Double getSumValorMetaInferior() {
		return sumValorMetaInferior;
	}

	public void setSumValorMetaInferior(Double sumValorMetaInferior) {
		this.sumValorMetaInferior = sumValorMetaInferior;
	}

	public Double getAvgValorMetaInferior() {
		return avgValorMetaInferior;
	}

	public void setAvgValorMetaInferior(Double avgValorMetaInferior) {
		this.avgValorMetaInferior = avgValorMetaInferior;
	}
}
