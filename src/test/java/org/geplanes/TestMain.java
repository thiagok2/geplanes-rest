package org.geplanes;

import java.lang.reflect.Method;
import java.time.LocalDate;

import org.geplanes.models.PlanoGestao;
import org.geplanes.models.enums.StatusPrazo;

public class TestMain {
	public static void main(String[] args) throws Exception {
		System.out.println(Integer.valueOf((0/3) + 1));
		System.out.println(Integer.valueOf((1/3) + 1));
		System.out.println(Integer.valueOf((2/3) + 1));
		System.out.println(Integer.valueOf((3/3) + 1));
		System.out.println(Integer.valueOf((4/3) + 1));
		System.out.println(Integer.valueOf((5/3) + 1));
		System.out.println(Integer.valueOf((6/3) + 1));
		System.out.println(Integer.valueOf((7/3) + 1));
		System.out.println(Integer.valueOf((8/3) + 1));
		System.out.println(Integer.valueOf((9/3) + 1));
		System.out.println(Integer.valueOf((10/3) + 1));
		System.out.println(Integer.valueOf((11/3) + 1));
		
		/**
		 * 	LocalDate dataLimiteLanc = LocalDate.from(planoGestao.getDataLimitelancamentoResultados1t().toInstant());
						LocalDate dataTravaLanc =  LocalDate.from(planoGestao.getDataTravarLancamentoResultados1t().toInstant());
						if(hoje.isAfter(dataLimiteLanc) && hoje.isBefore(dataTravaLanc))
							return StatusPrazo.ATRASO_TOLERAVEL;
						else if(hoje.isAfter(dataTravaLanc))
							return StatusPrazo.ATRASO_BLOQUEADO;
		 */
	}

}
