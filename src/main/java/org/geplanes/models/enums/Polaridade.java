package org.geplanes.models.enums;

public enum Polaridade {
	MAIOR_MELHOR(0), MENOR_MELHOR(2), ENTRE_FAIXAS(1);
	
	private Integer numero;
	private String texto;
	
	
	Polaridade(Integer numero){
		this.numero = numero;
		switch (numero){
			case 0:
				texto = "Maior Melhor";
			case 1:
				texto = "Entre Faixas";
			case 2:
				texto = "Menor Melhor";
		}
	}
	
	public static Polaridade get(Integer numero){
		switch (numero){
			case 0:
				return Polaridade.MAIOR_MELHOR;
			case 1:
				return Polaridade.ENTRE_FAIXAS;
			case 2:
				return Polaridade.MENOR_MELHOR;
			default:
				return Polaridade.MAIOR_MELHOR;
				
		}
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}
	
}
