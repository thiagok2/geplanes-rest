package org.geplanes.models.enums;

public enum StatusPlanoAcao {
	ANDAMENTO(0), PLANEJADO(1), CONCLUIDO(2), CANCELADO(3);
	
	private Integer codigo;
	
	StatusPlanoAcao(Integer codigo) {
		this.codigo = codigo;
	}
	
	public static StatusPlanoAcao get(Integer codigo){
		switch (codigo){
			case 0:
				return StatusPlanoAcao.ANDAMENTO;
			case 1:
				return StatusPlanoAcao.PLANEJADO;
			case 2:
				return StatusPlanoAcao.CONCLUIDO;
			case 3:
				return StatusPlanoAcao.CANCELADO;
			default:
				return null;
		}
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	
	public boolean isConcluido(){
		return this.equals(StatusPlanoAcao.CONCLUIDO);
	}
	
	public boolean isAberto(){
		return !isConcluido();
	}
}
