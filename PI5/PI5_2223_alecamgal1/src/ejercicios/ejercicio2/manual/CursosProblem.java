package ejercicios.ejercicio2.manual;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import _datos.DatosCursos;
import ejercicios.ejercicio2.CursosVertex;
import us.lsi.common.List2;
import us.lsi.common.Set2;

public record CursosProblem(Integer index, Set<Integer> remaining, Set<Integer> centros) {
	//la gran mayoria de los procedimientos son iguales a los realizados para CursosVertex
	public static CursosProblem of(Integer ind, Set<Integer> rem, Set<Integer> cen) {
		return new CursosProblem(ind, rem, cen);
	}

	public static CursosProblem initial() {
		return of(0, Set2.copy(DatosCursos.getTematicas()), Set2.empty());
	}

	public List<Integer> actions() {
		List<Integer> alternativas = List2.empty();
		if (index < DatosCursos.getNumCursos()) {
			alternativas = remaining.isEmpty() ? List2.empty() : List2.of(0);
			Set<Integer> restantesActualizados = Set2.difference(remaining, DatosCursos.getTematicasCurso(index)); //Si le asignas que te queda
			if (centroEsPosible(index, centros) && cursoTrataTematicaRestante(index, remaining) && !restantesActualizados.equals(remaining)) { //Si el centro es posible y el curso trata alguna tematica restante
				if (index == DatosCursos.getNumCursos() - 1) { //Si es el ultimo curso
					alternativas = restantesActualizados.isEmpty() ? List2.of(1) : List2.of(0);
					} 	else {
						alternativas.add(1);
					}
				}
			} 
		return alternativas;
		
	}
	
	private static Boolean cursoTrataTematicaRestante(Integer curso, Set<Integer> tematicasRestantes) {
		Integer res = 0;
		for(Integer tematica : tematicasRestantes) {
			res += DatosCursos.trataTematica(curso, tematica);
		}
		return res==0 ? false : true;
	}	
	
	private static Boolean centroEsPosible(Integer curso, Set<Integer> centros) { 
		return(centros.contains(DatosCursos.getCentroCurso(curso)) || (centros.size() < DatosCursos.getMaxCentros())) ? true : false; //Si el centro ya está cogido o no se ha superado el maximo
	}	

	public CursosProblem neighbor(Integer a) {
		Set<Integer> remainingNuevo = Set2.copy(remaining);
		Set<Integer> centrosNuevo = Set2.copy(centros);
		if(a==1) {
			remainingNuevo = Set2.difference(remaining, DatosCursos.getTematicasCurso(index));
			centrosNuevo.add(DatosCursos.getCentroCurso(index));
		}

		return of(index + 1, remainingNuevo, centrosNuevo);
	}

	public Double heuristic(CursosVertex v1, Predicate<CursosVertex> goal, CursosVertex v2) {
		return v1.remaining().isEmpty() ? 0.
				: IntStream.range(v1.index(), DatosCursos.getNumCursos())
						.filter(i -> cursoTrataTematicaRestante(i, v1.remaining()))
						.mapToDouble(i -> DatosCursos.getCosteCurso(i)).min().orElse(100.);
	}

}