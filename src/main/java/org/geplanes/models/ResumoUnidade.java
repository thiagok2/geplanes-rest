package org.geplanes.models;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ResumoUnidade {
	
	private UnidadeGerencial unidade;
	
	private Long indicadoresCount;
	
	private Long countEmAndamento;
	
	private Long countEmPlanejamento;
	
	private Long countAtrasados;
	
	private Long countConcluidos;
	
	private Long countConcluidoSucesso;
	
	private Long countConcluidoInsucesso;
	
	/*Acompanhamento Indicador*/
	private Long countAcompanhamentos;
	
	private Long countAcompanhamentosAplicaveis;
	
	
	/*Iniciativas */
	private Long countIniciativas;
	
	private Long countIniciativasAtrasadas;
	
	private Long countIniciativasConcluidas;
	
	private Long countIniciativasAndamento;
	
	/*Plano Ação*/
	private Long countPlanosAcao;
	
	private Long countPlanosAcaoConcluidos;
	
	private Long countPlanosAcaoPlanejados;
	
	private Long countPlanosAcaoAndamento;
	
	/*Pendencias*/
	private Long countPendencias = 0l;
	
	private Long countPendenciasPlanejamento = 0l;
	
	private Long countPendenciasPrazo = 0l;
	
	private Long countPendenciasResultado = 0l;
	
	private Long countPendenciasInfo = 0l;
	
	private Long countPendenciasAviso = 0l;
	
	private Long countPendenciasAlerta = 0l;
	
	public ResumoUnidade(UnidadeGerencial unidade) {
		this.unidade = unidade;
	}
	
	public BigDecimal getTaxaPlanejamento(){
		Double taxa = (double)countAcompanhamentosAplicaveis/(double)countAcompanhamentos * 100d;
		BigDecimal taxaD = BigDecimal.valueOf(taxa).setScale(2, RoundingMode.HALF_UP);
		return taxaD;
	}
	
	public BigDecimal getTaxaConclusao(){
		Double taxa = (double)countConcluidos/(double)indicadoresCount*100d;
		BigDecimal taxaD = BigDecimal.valueOf(taxa).setScale(2, RoundingMode.HALF_UP);
		return taxaD;
	}
	
	public Integer getAno(){
		return unidade.getPlanoGestao().getAnoExercicio();
	}
	
	public Integer getUnidadeSuperiorId(){
		return unidade.getUnidadeSuperior().getId();
	}

	public UnidadeGerencial getUnidade() {
		return unidade;
	}

	public void setUnidade(UnidadeGerencial unidade) {
		this.unidade = unidade;
	}

	public Long getIndicadoresCount() {
		return indicadoresCount;
	}

	public void setIndicadoresCount(Long indicadoresCount) {
		this.indicadoresCount = indicadoresCount;
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

	public Long getCountIniciativas() {
		return countIniciativas;
	}

	public void setCountIniciativas(Long countIniciativas) {
		this.countIniciativas = countIniciativas;
	}

	public Long getCountIniciativasAtrasadas() {
		return countIniciativasAtrasadas;
	}

	public void setCountIniciativasAtrasadas(Long countIniciativasAtrasadas) {
		this.countIniciativasAtrasadas = countIniciativasAtrasadas;
	}

	public Long getCountIniciativasConcluidas() {
		return countIniciativasConcluidas;
	}

	public void setCountIniciativasConcluidas(Long countIniciativasConcluidas) {
		this.countIniciativasConcluidas = countIniciativasConcluidas;
	}

	public Long getCountIniciativasAndamento() {
		return countIniciativasAndamento;
	}

	public void setCountIniciativasAndamento(Long countIniciativasAndamento) {
		this.countIniciativasAndamento = countIniciativasAndamento;
	}

	public Long getCountPlanosAcao() {
		return countPlanosAcao;
	}

	public void setCountPlanosAcao(Long countPlanosAcao) {
		this.countPlanosAcao = countPlanosAcao;
	}

	public Long getCountPlanosAcaoConcluidos() {
		return countPlanosAcaoConcluidos;
	}

	public void setCountPlanosAcaoConcluidos(Long countPlanosAcaoConcluido) {
		this.countPlanosAcaoConcluidos = countPlanosAcaoConcluido;
	}

	public Long getCountPlanosAcaoPlanejados() {
		return countPlanosAcaoPlanejados;
	}

	public void setCountPlanosAcaoPlanejados(Long countPlanosAcaoPlanejado) {
		this.countPlanosAcaoPlanejados = countPlanosAcaoPlanejado;
	}

	public Long getCountPlanosAcaoAndamento() {
		return countPlanosAcaoAndamento;
	}

	public void setCountPlanosAcaoAndamento(Long countPlanosAcaoAndamento) {
		this.countPlanosAcaoAndamento = countPlanosAcaoAndamento;
	}

	public Long getCountAcompanhamentos() {
		return countAcompanhamentos;
	}

	public void setCountAcompanhamentos(Long countAcompanhamentos) {
		this.countAcompanhamentos = countAcompanhamentos;
	}

	public Long getCountAcompanhamentosAplicaveis() {
		return countAcompanhamentosAplicaveis;
	}

	public void setCountAcompanhamentosAplicaveis(Long countAcompanhamentosAplicaveis) {
		this.countAcompanhamentosAplicaveis = countAcompanhamentosAplicaveis;
	}

	public Long getCountPendencias() {
		return countPendencias;
	}

	public void setCountPendencias(Long countPendencias) {
		this.countPendencias = countPendencias;
	}

	public Long getCountPendenciasPlanejamento() {
		return countPendenciasPlanejamento;
	}

	public void setCountPendenciasPlanejamento(Long countPendenciasPlanejamento) {
		this.countPendenciasPlanejamento = countPendenciasPlanejamento;
	}

	public Long getCountPendenciasPrazo() {
		return countPendenciasPrazo;
	}

	public void setCountPendenciasPrazo(Long countPendenciasPrazo) {
		this.countPendenciasPrazo = countPendenciasPrazo;
	}

	public Long getCountPendenciasResultado() {
		return countPendenciasResultado;
	}

	public void setCountPendenciasResultado(Long countPendenciasResultado) {
		this.countPendenciasResultado = countPendenciasResultado;
	}

	public Long getCountPendenciasInfo() {
		return countPendenciasInfo;
	}

	public void setCountPendenciasInfo(Long countPendenciasInfo) {
		this.countPendenciasInfo = countPendenciasInfo;
	}

	public Long getCountPendenciasAviso() {
		return countPendenciasAviso;
	}

	public void setCountPendenciasAviso(Long countPendenciasAviso) {
		this.countPendenciasAviso = countPendenciasAviso;
	}

	public Long getCountPendenciasAlerta() {
		return countPendenciasAlerta;
	}

	public void setCountPendenciasAlerta(Long countPendenciasAlerta) {
		this.countPendenciasAlerta = countPendenciasAlerta;
	}

}