package org.geplanes.models.enums;

public enum StatusPrazo {
	NAO_APLICAVEL("Não Aplicável"),
	EM_PLANEJAMENTO("Em planejamento"), 
	ATRASADO("Atrasado"), 
	ATRASO_TOLERAVEL("Atraso Tolerável"), 
	ATRASO_BLOQUEADO("Atraso Bloqueado"), 
	EM_ANDAMENTO("Em Andamento"),
	CONCLUIDO("Concluído"),
	INDETERMINADO("Indeterminado");;
	
	private final String text;

    private StatusPrazo(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
    
    public boolean isAberto(){
    	return this.equals(EM_PLANEJAMENTO) || this.equals(EM_ANDAMENTO);
    }
    
    public boolean isAtrasado(){
    	return this.equals(ATRASADO) || this.equals(ATRASO_BLOQUEADO) || this.equals(ATRASO_TOLERAVEL);
    }
}
