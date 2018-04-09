package org.geplanes.models.enums;

public enum StatusResultado {
	NAO_APLICAVEL("Não Aplicável"),
	INDETERMINADO("Indeterminado"),
	SUCESSO("Sucesso"), SUCESSO_PARCIAL("Sucesso - Parcial"), 
	INSUCESSO_ABAIXO("Insucesso - Abaixo da Meta"),INSUCESSO_ACIMA("Insucesso - Acima da Meta"), 
	INSUCESSO("Insucesso");
	
	public static double TOLERANCIA_INFERIOR = 0.85;
	
	private final String text;

    private StatusResultado(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
    
    public boolean isSucesso(){
    	return this.equals(StatusResultado.SUCESSO) || this.equals(StatusResultado.SUCESSO_PARCIAL);
    }
    
    public boolean isInsucesso(){
    	return this.equals(StatusResultado.INSUCESSO_ABAIXO) || this.equals(StatusResultado.INSUCESSO_ACIMA) || this.equals(StatusResultado.INSUCESSO);
    }
    
    public boolean isConcluido(){
    	return !this.equals(NAO_APLICAVEL) && !this.equals(INDETERMINADO);
    }
}
