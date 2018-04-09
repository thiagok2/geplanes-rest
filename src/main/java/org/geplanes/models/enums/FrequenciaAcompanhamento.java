package org.geplanes.models.enums;

public enum FrequenciaAcompanhamento {
	DIARIA(0), SEMANAL(1), QUINZENAL(2), MENSAL(3), TRIMESTRAL(4), SEMESTRAL(5), ANUAL(6);
	
	private Integer codido;
	private String texto;
	
	FrequenciaAcompanhamento(Integer codigo){
		this.codido = codigo;
		this.texto = name().toLowerCase().replaceFirst(
				String.valueOf(name().charAt(0)), 
				String.valueOf(name().charAt(0)).toUpperCase());
	}
	
	public static FrequenciaAcompanhamento get(Integer numero){
		switch (numero){
			case 0:
				return FrequenciaAcompanhamento.DIARIA;
			case 1:
				return FrequenciaAcompanhamento.SEMANAL;
			case 2:
				return FrequenciaAcompanhamento.QUINZENAL;
			case 3:
				return FrequenciaAcompanhamento.MENSAL;
			case 4:
				return FrequenciaAcompanhamento.TRIMESTRAL;
			case 5:
				return FrequenciaAcompanhamento.SEMESTRAL;
			case 6:
				return FrequenciaAcompanhamento.ANUAL;
			default:
				return FrequenciaAcompanhamento.MENSAL;
				
		}
	}

	public Integer getCodido() {
		return codido;
	}

	public String getTexto() {
		return texto;
	}

}
