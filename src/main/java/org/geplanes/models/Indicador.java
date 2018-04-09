package org.geplanes.models;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.geplanes.models.enums.FrequenciaAcompanhamento;
import org.geplanes.models.enums.FrequenciaLancamento;
import org.geplanes.models.enums.Polaridade;
import org.geplanes.models.enums.StatusPrazo;
import org.geplanes.models.enums.StatusResultado;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="indicador_view")
public class Indicador implements Demanda{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	private String nome;
	
	private String descricao;
	
	private Double peso;
	
	private Integer frequencia;
	
	private Integer frequenciaacompanhamento;
	
	private Integer precisao;
	
	private Double tolerancia;
	
	private String mecanismocontrole;
	
	private String fontedados;
	
	private String formulacalculo;
	
	private String responsavel;
	
	private Integer melhor;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="indicador_id")
	private List<AcompanhamentoIndicador> acompanhamentos;
	
	
	@ManyToOne
	@JoinColumn(name="mapaestrategico_id")
	private MapaEstrategico mapaEstrategico;
	
	@ManyToOne
	@JoinColumn(name="perspectiva_id")
	private Perspectiva perspectiva;

	@ManyToOne
	@JoinColumn(name="objetivoestrategico_id")
	private ObjetivoEstrategico objetivoEstrategico;
	
	@ManyToOne
	@JoinColumn(name="unidadegerencial_id")
	private UnidadeGerencial unidade;
	
	@Transient
	private StatusResultado statusResultado;
	
	@Transient
	private StatusPrazo statusPrazo;
	
	@Transient
	@JsonIgnore
	public Integer getAno(){
		return unidade.getPlanoGestao().getAnoExercicio();
	}
	
	
	@JsonIgnore
	@Transient
	public Polaridade getPolaridade(){
		return Polaridade.get(melhor);
	}
	
	@JsonIgnore
	@Transient
	public FrequenciaLancamento getEnumFrequenciaLancamento(){
		return FrequenciaLancamento.get(frequencia);
	}
	
	@JsonIgnore
	@Transient
	public FrequenciaAcompanhamento getEnumFrequenciaAcompanhamento(){
		return FrequenciaAcompanhamento.get(frequenciaacompanhamento);
	}
	
	@Transient
	public Double getValorRealTotal(){
		return acompanhamentos.stream().filter(a -> !a.getNaoaplicavel() && a.getValorreal() != null)
				.mapToDouble(a -> a.getValorreal().doubleValue()).sum();
	}
	
	@Transient
	public Double getValorMetaSuperiorTotal(){
		return acompanhamentos.stream().filter(a -> !a.getNaoaplicavel() && a.getValorlimitesuperior() != null)
				.mapToDouble(a -> a.getValorlimitesuperior().doubleValue()).sum();
	}
	
	@Transient
	public Double getValorMetaInferiorTotal(){
		return acompanhamentos.stream().filter(a -> !a.getNaoaplicavel() && a.getValorlimiteinferior() != null)
				.mapToDouble(a -> a.getValorlimiteinferior().doubleValue()).sum();
	}
	
	@Transient
	public Double getRelacaoMetaReal(){
		if(getPolaridade().equals(Polaridade.MAIOR_MELHOR))
			return getValorRealTotal() / getValorMetaSuperiorTotal();
		if(getPolaridade().equals(Polaridade.MENOR_MELHOR))
			return getValorRealTotal() / getValorMetaInferiorTotal();
		else return 0d;
	}
	
	@Transient
	public StatusPrazo getStatusPrazo(){
		if(statusPrazo != null)
			return statusPrazo;
		else{
			statusPrazo = StatusPrazo.INDETERMINADO;
					
			List<AcompanhamentoIndicador> acompanhamentosOk =  acompanhamentos.stream().filter(a ->  !a.getNaoaplicavel()).collect(Collectors.toList());
			
			if(acompanhamentosOk.stream().allMatch(a -> a.getEStatusPrazo().equals(StatusPrazo.CONCLUIDO)))
				statusPrazo = StatusPrazo.CONCLUIDO;
			else if(acompanhamentosOk.stream().allMatch(a -> a.getEStatusPrazo().equals(StatusPrazo.EM_PLANEJAMENTO)))
				statusPrazo = StatusPrazo.EM_PLANEJAMENTO;
			else if(acompanhamentosOk.stream().anyMatch(a -> a.getEStatusPrazo().equals(StatusPrazo.ATRASO_BLOQUEADO)))
				statusPrazo = StatusPrazo.ATRASO_BLOQUEADO;
			else if(acompanhamentosOk.stream().anyMatch(a -> a.getEStatusPrazo().isAtrasado()))
				statusPrazo = StatusPrazo.ATRASADO;
			else if(acompanhamentosOk.stream().noneMatch(a -> a.getEStatusPrazo().isAtrasado()) 
					&& acompanhamentosOk.stream().anyMatch(a -> a.getEStatusPrazo().equals(StatusPrazo.EM_ANDAMENTO)))
				statusPrazo = StatusPrazo.EM_ANDAMENTO;
			
			
			return statusPrazo;
		}
	}
	
	@Transient
	@JsonIgnore
	public boolean isAtrasado(){
		return getStatusPrazo().isAtrasado();
	}
	
	@Transient
	@JsonIgnore
	public boolean isAberto(){
		return getStatusPrazo().isAberto();
	}
	
	@Transient
	@JsonIgnore
	public boolean isAndamento(){
		return getStatusPrazo().equals(StatusPrazo.EM_ANDAMENTO);
	}
	
	@Transient
	@JsonIgnore
	public boolean isPlanejamento(){
		return getStatusPrazo().equals(StatusPrazo.EM_PLANEJAMENTO);
	}
	
	@Transient
	@JsonIgnore
	public StatusResultado getStatusResultados(){
		
		if(statusResultado != null)
			return statusResultado;
		else{
			statusResultado = StatusResultado.INDETERMINADO;
					
			List<AcompanhamentoIndicador> acompanhamentosOk =  acompanhamentos.stream().filter(a ->  !a.getNaoaplicavel()).collect(Collectors.toList());
			
			if(acompanhamentosOk.stream().allMatch(a -> a.getEStatusPrazo().equals(StatusPrazo.CONCLUIDO))){
				if(acompanhamentosOk.stream().allMatch(a -> a.getStatusResultado().isSucesso()))
					statusResultado = StatusResultado.SUCESSO;
				else if(acompanhamentosOk.stream().noneMatch(a -> a.getStatusResultado().isSucesso())
						&&  acompanhamentosOk.stream().anyMatch(a -> a.getStatusResultado().isInsucesso())){
					statusResultado = StatusResultado.INSUCESSO;
				}else if(acompanhamentosOk.stream().anyMatch(a -> a.getStatusResultado().isSucesso()) 
						&& acompanhamentosOk.stream().anyMatch(a -> a.getStatusResultado().isInsucesso())){
					
					if(getPolaridade().equals(Polaridade.MAIOR_MELHOR) 
							|| getPolaridade().equals(Polaridade.MENOR_MELHOR)){
						
						Long totalMeta = acompanhamentosOk.stream().filter(a -> a.getValorlimitesuperior() != null)
								.mapToLong(a -> a.getValorlimitesuperior().longValue()).sum();
						
						Long totalLanc = acompanhamentosOk.stream().filter(a -> a.getValorreal() != null)
								.mapToLong(a -> a.getValorreal().longValue()).sum();
						
						Double relacaoResultado = 0d;
						if(getPolaridade().equals(Polaridade.MAIOR_MELHOR))
							relacaoResultado = Double.valueOf(totalLanc/totalMeta);
						else if(getPolaridade().equals(Polaridade.MENOR_MELHOR))
							relacaoResultado = Double.valueOf(totalMeta/totalLanc);
						
						if(relacaoResultado >= 1) 
							statusResultado = StatusResultado.SUCESSO;
						else if(relacaoResultado >= StatusResultado.TOLERANCIA_INFERIOR) 
							statusResultado = StatusResultado.SUCESSO_PARCIAL;
						
						else
							statusResultado = StatusResultado.INSUCESSO;
					
					}else {//Polaridade.ENTRE_FAIXAS
						Long totalMetaSuperior = acompanhamentosOk.stream().filter(a -> a.getValorlimitesuperior() != null).mapToLong(a -> a.getValorlimitesuperior().longValue()).sum();
						Long totalMetaInferior = acompanhamentosOk.stream().filter(a -> a.getValorlimiteinferior() != null).mapToLong(a -> a.getValorlimiteinferior().longValue()).sum();
						Long totalLanc = acompanhamentosOk.stream().mapToLong(a -> a.getValorreal().longValue()).sum();
						
						if(totalLanc >= totalMetaInferior && totalLanc <= totalMetaSuperior)
							statusResultado = StatusResultado.SUCESSO;
						else{
							totalMetaSuperior = Double.valueOf(totalMetaSuperior * StatusResultado.TOLERANCIA_INFERIOR).longValue();
							totalMetaInferior = Double.valueOf(totalMetaInferior * StatusResultado.TOLERANCIA_INFERIOR).longValue();
							if(totalLanc >= totalMetaInferior && totalLanc <= totalMetaSuperior)
								statusResultado =  StatusResultado.SUCESSO_PARCIAL;
							else statusResultado =  StatusResultado.INSUCESSO;
						}
					}
				}
			}
			
			return statusResultado;
		}
	
	}
	
	@Transient
	@JsonIgnore
	public boolean isConcluidoSucesso(){
		return getStatusResultados().isSucesso();
	}
	
	@Transient
	@JsonIgnore
	public boolean isConcluidoInsucesso(){
		return getStatusResultados().isInsucesso();
	}
	
	@Transient
	@JsonIgnore
	public boolean isConcluido(){
		return getStatusResultados().isConcluido();
	}
	
	@Transient
	@JsonIgnore
	public Date getDataLancamentoAtrasado(){
		return acompanhamentos.stream().filter(a -> a.isAtrasado()).map(AcompanhamentoIndicador::getDataFinal).min(Date::compareTo).get();
	}
	
	@Override
	@Transient
	@JsonIgnore
	public Date getDataLimite() {
		return acompanhamentos.stream().map(AcompanhamentoIndicador::getDataFinal).max(Date::compareTo).get();
	}
	
	@Transient
	@JsonIgnore
	public Integer getCountAcompanhamentos(){
		return acompanhamentos != null ? acompanhamentos.size() : 0;
	}
	
	@Transient
	@JsonIgnore
	public Integer getCountAcompanhamentosAplicaveis(){
		return acompanhamentos != null ? (int)acompanhamentos.stream().filter(a -> a.isAplicavel()).count() : 0;
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

	@Transient
	public String getResumo() {
		return nome;
	}
	
	public String getDescricao(){
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	public Integer getFrequencia() {
		return frequencia;
	}

	public void setFrequencia(Integer frequencia) {
		this.frequencia = frequencia;
	}

	public Integer getPrecisao() {
		return precisao;
	}

	public void setPrecisao(Integer precisao) {
		this.precisao = precisao;
	}

	public Double getTolerancia() {
		return tolerancia;
	}

	public void setTolerancia(Double tolerancia) {
		this.tolerancia = tolerancia;
	}

	public String getMecanismocontrole() {
		return mecanismocontrole;
	}

	public void setMecanismocontrole(String mecanismocontrole) {
		this.mecanismocontrole = mecanismocontrole;
	}

	public String getFontedados() {
		return fontedados;
	}

	public void setFontedados(String fontedados) {
		this.fontedados = fontedados;
	}

	public String getFormulacalculo() {
		return formulacalculo;
	}

	public void setFormulacalculo(String formulacalculo) {
		this.formulacalculo = formulacalculo;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public MapaEstrategico getMapaEstrategico() {
		return mapaEstrategico;
	}

	public void setMapaEstrategico(MapaEstrategico mapaEstrategico) {
		this.mapaEstrategico = mapaEstrategico;
	}

	public Perspectiva getPerspectiva() {
		return perspectiva;
	}

	public void setPerspectiva(Perspectiva perspectiva) {
		this.perspectiva = perspectiva;
	}

	public ObjetivoEstrategico getObjetivoEstrategico() {
		return objetivoEstrategico;
	}

	public void setObjetivoEstrategico(ObjetivoEstrategico objetivoEstrategico) {
		this.objetivoEstrategico = objetivoEstrategico;
	}

	public UnidadeGerencial getUnidade() {
		return unidade;
	}

	public void setUnidade(UnidadeGerencial unidade) {
		this.unidade = unidade;
	}

	public List<AcompanhamentoIndicador> getAcompanhamentos() {
		return acompanhamentos;
	}

	public void setAcompanhamentos(List<AcompanhamentoIndicador> acompanhamentos) {
		this.acompanhamentos = acompanhamentos;
	}

	public Integer getMelhor() {
		return this.melhor;
	}

	public void setMelhor(Integer melhor) {
		this.melhor = melhor;
	}
	
	public Integer getFrequenciaacompanhamento() {
		return frequenciaacompanhamento;
	}

	public void setFrequenciaacompanhamento(Integer frequenciaacompanhamento) {
		this.frequenciaacompanhamento = frequenciaacompanhamento;
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
		Indicador other = (Indicador) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Indicador [id=" + id + ", nome=" + nome + ", descricao=" + descricao + ", peso=" + peso
				+ ", frequencia=" + frequencia + ", precisao=" + precisao + ", tolerancia=" + tolerancia
				+ ", mecanismocontrole=" + mecanismocontrole + ", fontedados=" + fontedados + ", formulacalculo="
				+ formulacalculo + ", responsavel=" + responsavel 
				+ ", mapaEstrategico=" + mapaEstrategico + ", perspectiva=" + perspectiva + ", objetivoEstrategico="
				+ objetivoEstrategico + ", unidade=" + unidade + "]";
	}

	
	public String getMetasToText(){
		switch (getPolaridade()){
			case MAIOR_MELHOR:
				if(getValorMetaSuperiorTotal() != null)
					return getValorMetaSuperiorTotal().toString();
			case MENOR_MELHOR:
				if(getValorMetaInferiorTotal() != null)
					return getValorMetaInferiorTotal().toString();
			case ENTRE_FAIXAS:
				if(getValorMetaSuperiorTotal() != null && getValorMetaInferiorTotal() != null)
					return getValorMetaSuperiorTotal() + " - " + getValorMetaInferiorTotal();		
		}
			
		return "";
	}
}
