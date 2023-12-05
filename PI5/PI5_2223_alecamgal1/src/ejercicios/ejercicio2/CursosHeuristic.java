package ejercicios.ejercicio2;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import _datos.DatosCursos;

public class CursosHeuristic {
// Se explica en practicas.
	// Lo más optimista: los cursos elegidos darán la mejor solución posible
	public static Double heuristic(CursosVertex v1, Predicate<CursosVertex> goal, CursosVertex v2) {
		return v1.remaining().isEmpty() ? 0.
				: IntStream.range(v1.index(), DatosCursos.getNumCursos()) //Todos los cursos restantes por decidir si cursar o no
						.filter(i -> cursoTrataTematicaRestante(i, v1.remaining())) //Filtro por los que no tratan alguna tematica restante
						.mapToDouble(i -> DatosCursos.getCosteCurso(i)).min().orElse(100.); //Siempre escojo el curso que tiene menos coste o penalizo
	}
	
	//Metodo para comprobar si el curso trata alguna tematica restante
	private static Boolean cursoTrataTematicaRestante(Integer curso, Set<Integer> tematicasRestantes) {
		Integer res = 0;
		for(Integer tematica : tematicasRestantes) {
			res += DatosCursos.trataTematica(curso, tematica);
		}
		return res==0 ? false : true;
	}	
}
