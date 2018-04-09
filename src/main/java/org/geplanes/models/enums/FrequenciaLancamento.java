package org.geplanes.models.enums;

public enum FrequenciaLancamento {
	 MENSAL(1), TRIMESTRAL(2), QUINZENAL(3);
	
	private Integer codigo;
	private String texto;
	
	FrequenciaLancamento(Integer codigo){
		this.codigo = codigo; 
		this.texto = name().toLowerCase().replaceFirst(
				String.valueOf(name().charAt(0)), 
				String.valueOf(name().charAt(0)).toUpperCase());
	}
	
	public static FrequenciaLancamento get(Integer numero){
		switch (numero){
			case 1:
				return FrequenciaLancamento.MENSAL;
			case 2:
				return FrequenciaLancamento.TRIMESTRAL;
			case 3:
				return FrequenciaLancamento.QUINZENAL;
				
			default:
				return FrequenciaLancamento.MENSAL;
				
		}
	}
	
	public Integer getCodigo(){
		return codigo;
	}
	
	public String getTexto(){
		return texto;
	}
}
