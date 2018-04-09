package org.geplanes.models;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.geplanes.models.enums.FrequenciaLancamento;
import org.geplanes.models.enums.Polaridade;
import org.geplanes.models.enums.StatusPrazo;
import org.geplanes.models.enums.StatusResultado;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="acompanhamentoindicador")
public class AcompanhamentoIndicador {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="indicador_id")
	private Indicador indicador;
	
	private Integer indice;
	
	@Temporal(TemporalType.DATE)
	@Column(name="datainicial")
	private Date dataInicial;
	
	@Temporal(TemporalType.DATE)
	@Column(name="datafinal")
	private Date dataFinal;
	
	private Boolean naoaplicavel;
	
	private BigDecimal valorlimitesuperior;
	
	private BigDecimal valorreal;
	
	private BigDecimal valorlimiteinferior;
	
	private Boolean valorbaseok;
	
	private Boolean valorrealok;
	
	@Transient
	private StatusPrazo statusPrazo;
	
	@Transient
	private StatusResultado statusResultado;
	
	@Transient
	public StatusPrazo getEStatusPrazo(){
		if(statusPrazo != null)
			return statusPrazo;
		else{
			statusPrazo = StatusPrazo.INDETERMINADO;
			try{
				Date hoje = new Date();
				
				if(naoaplicavel)
					
					statusPrazo = StatusPrazo.NAO_APLICAVEL;
				
				else if(valorreal != null)
					
					statusPrazo = StatusPrazo.CONCLUIDO;
				
				else if(hoje.before(dataInicial))
					
					statusPrazo = StatusPrazo.EM_PLANEJAMENTO;
				
				else if(hoje.after(dataInicial) && hoje.before(dataFinal))
					
					statusPrazo = StatusPrazo.EM_ANDAMENTO;
				
				else if(hoje.after(dataFinal)){
					
					statusPrazo = StatusPrazo.ATRASADO;
					
					PlanoGestao planoGestao = indicador.getUnidade().getPlanoGestao();
					Integer trimestre = getTrimesteLancamento();
					
					if(trimestre >=1 && trimestre<=4){
						String methodSLanc = "getDataLimitelancamentoResultados"+trimestre+"t";
						String methodSTrav = "getDataTravarLancamentoResultados"+trimestre+"t";
						
						Class<PlanoGestao> clazz = PlanoGestao.class;
						Method methodLanc = clazz.getMethod(methodSLanc);
						Method methodTrav = clazz.getMethod(methodSTrav);
						
						Date dataLimiteLanc = (Date)methodLanc.invoke(planoGestao);
						Date dataLimiteTrav = (Date)methodTrav.invoke(planoGestao);	
		
						if(hoje.after(dataLimiteLanc) && hoje.before(dataLimiteTrav))
							statusPrazo = StatusPrazo.ATRASO_TOLERAVEL;
						
						else if(hoje.after(dataLimiteTrav))
							statusPrazo = StatusPrazo.ATRASO_BLOQUEADO;
					}	
					
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
		
		return statusPrazo;
		
		}
		
	}
	
	@Transient
	private Integer getTrimesteLancamento(){
		if(indicador.getEnumFrequenciaLancamento().equals(FrequenciaLancamento.TRIMESTRAL))
			return indice;
		else if(indicador.getEnumFrequenciaLancamento().equals(FrequenciaLancamento.MENSAL)){
			return Integer.valueOf(((indice-1)/3) + 1);
		}
		return 4;
	}
	
	@Transient
	public StatusResultado getStatusResultado(){
		
		if(statusResultado != null)
			return statusResultado;
		else{
			statusResultado = StatusResultado.INDETERMINADO;
			try{
				statusPrazo = getEStatusPrazo();
				
				if(naoaplicavel || statusPrazo.isAberto())
					statusResultado = StatusResultado.NAO_APLICAVEL;
	
				else if(valorreal == null)
					statusResultado = (statusPrazo.isAtrasado()) ? StatusResultado.INSUCESSO : StatusResultado.INDETERMINADO;
	
				else if(statusPrazo == StatusPrazo.CONCLUIDO){
					
					Polaridade polaridadeIndicador = indicador.getPolaridade();
					if(polaridadeIndicador.equals(Polaridade.ENTRE_FAIXAS)){
						if(valorlimiteinferior == null || valorlimitesuperior ==null)
							statusResultado = StatusResultado.INDETERMINADO;
						
						else if(valorreal.doubleValue() >= valorlimiteinferior.doubleValue() &&
								valorreal.doubleValue() <= valorlimitesuperior.doubleValue())
							statusResultado = StatusResultado.SUCESSO;
						
						else if((valorreal.doubleValue() >= (valorlimiteinferior.doubleValue() * StatusResultado.TOLERANCIA_INFERIOR)
								&& valorreal.doubleValue() <= (valorlimitesuperior.doubleValue() * (1 + (1  - StatusResultado.TOLERANCIA_INFERIOR)))))
							statusResultado = StatusResultado.SUCESSO_PARCIAL;
						
						else
							statusResultado = StatusResultado.INSUCESSO;
						
					}else{//MELHOR OU MENOR VALOR
						BigDecimal relacaoValorMeta = BigDecimal.ZERO;
						if(polaridadeIndicador.equals(Polaridade.MAIOR_MELHOR)){
							if(valorreal == null || valorreal.equals(BigDecimal.ZERO))
								statusResultado = StatusResultado.INSUCESSO;
							else if(valorlimitesuperior == null || valorlimitesuperior.equals(BigDecimal.ZERO))
								statusResultado = StatusResultado.INDETERMINADO;
							
							relacaoValorMeta = valorreal.divide(valorlimitesuperior, 2, RoundingMode.HALF_EVEN).setScale(2);
						}else if(polaridadeIndicador.equals(Polaridade.MENOR_MELHOR)){
							if(valorreal.equals(BigDecimal.ZERO)) 
								statusResultado = StatusResultado.SUCESSO;
							
							else if(valorlimiteinferior == null)
								statusResultado = StatusResultado.INDETERMINADO;
							
							relacaoValorMeta = valorlimiteinferior.divide(valorreal, 2, RoundingMode.HALF_EVEN).setScale(2);
						}
							
						if(relacaoValorMeta.compareTo(BigDecimal.ONE)>=0) 
							statusResultado = StatusResultado.SUCESSO;
						
						else if(relacaoValorMeta.compareTo(BigDecimal.valueOf(StatusResultado.TOLERANCIA_INFERIOR))>=0) 
							statusResultado = StatusResultado.SUCESSO_PARCIAL;
						
						else if(relacaoValorMeta.doubleValue() < StatusResultado.TOLERANCIA_INFERIOR)
							statusResultado = StatusResultado.INSUCESSO_ABAIXO;
					}
						
				}
			}catch(Exception e){
				e.printStackTrace();
				throw e;
			}
		}
		

		return statusResultado;
	}
	
	@Transient
	@JsonIgnore
	public boolean isAtrasado(){
		return getEStatusPrazo().isAtrasado();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Indicador getIndicador() {
		return indicador;
	}

	public void setIndicador(Indicador indicador) {
		this.indicador = indicador;
	}

	public Integer getIndice() {
		return indice;
	}

	public void setIndice(Integer indice) {
		this.indice = indice;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public Boolean getNaoaplicavel() {
		return naoaplicavel;
	}
	
	@Transient
	@JsonIgnore
	public boolean isAplicavel(){
		return !naoaplicavel;
	}

	public void setNaoaplicavel(Boolean naoaplicavel) {
		this.naoaplicavel = naoaplicavel;
	}

	public BigDecimal getValorlimitesuperior() {
		return valorlimitesuperior;
	}

	public void setValorlimitesuperior(BigDecimal valorlimitesuperior) {
		this.valorlimitesuperior = valorlimitesuperior;
	}

	public BigDecimal getValorreal() {
		return valorreal;
	}

	public void setValorreal(BigDecimal valorreal) {
		this.valorreal = valorreal;
	}

	public BigDecimal getValorlimiteinferior() {
		return valorlimiteinferior;
	}

	public void setValorlimiteinferior(BigDecimal valorlimiteinferior) {
		this.valorlimiteinferior = valorlimiteinferior;
	}

	public Boolean getValorbaseok() {
		return valorbaseok;
	}

	public void setValorbaseok(Boolean valorbaseok) {
		this.valorbaseok = valorbaseok;
	}

	public Boolean getValorrealok() {
		return valorrealok;
	}

	public void setValorrealok(Boolean valorrealok) {
		this.valorrealok = valorrealok;
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
		AcompanhamentoIndicador other = (AcompanhamentoIndicador) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AcompanhamentoIndicador [id=" + id + ", indicador=" + indicador + ", indice=" + indice
				+ ", dataInicial=" + dataInicial + ", dataFinal=" + dataFinal + ", naoaplicavel=" + naoaplicavel
				+ ", valorlimitesuperior=" + valorlimitesuperior + ", valorreal=" + valorreal + ", valorlimiteinferior="
				+ valorlimiteinferior + ", valorbaseok=" + valorbaseok + ", valorrealok=" + valorrealok + "]";
	}
}
