package ejercicios.ejercicio1;

import _datos.DatosCafe;
import us.lsi.graphs.virtual.SimpleEdgeAction;

public record CafeEdge(CafeVertex source, //
		CafeVertex target, 
		Integer action, //Cuantos kgs se han realizado
		Double weight) //Beneficio
               implements SimpleEdgeAction<CafeVertex,Integer> {

	
	public static CafeEdge of(CafeVertex v1, CafeVertex v2, Integer a) {	
		double beneficio = DatosCafe.getBeneficioVariedad(v1.index()); //Beneficio de esa variedad
		return new CafeEdge(v1, v2, a, beneficio);
	}

	@Override
	public String toString() {
		return String.format("%d; %.1f", action, weight);
	}

}


