package ejercicios.ejercicio2;

import _datos.DatosCursos;
import us.lsi.graphs.virtual.SimpleEdgeAction;

//aqui no tocamos acsi nada con respecto a los ejemplos
public record CursosEdge(CursosVertex source, 
		CursosVertex target, 
		Integer action, //Si se elige el curso 1, si no se elige 0
		Double weight) //Precio de inscripción
		implements SimpleEdgeAction<CursosVertex, Integer> {

	public static CursosEdge of(CursosVertex v1, CursosVertex v2, Integer a) {
		
		return new CursosEdge(v1, v2, a, a * DatosCursos.getCosteCurso(v1.index()));
	}

	public String toString() {
		return String.format("%d; %.1f", action, weight);
	}

}

