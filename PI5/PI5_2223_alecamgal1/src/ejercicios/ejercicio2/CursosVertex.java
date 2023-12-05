package ejercicios.ejercicio2;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import _datos.DatosCursos;
import us.lsi.common.List2;
import us.lsi.common.Set2;
import us.lsi.graphs.virtual.VirtualVertex;

public record CursosVertex(Integer index, Set<Integer> remaining, Set<Integer> centros)
		implements VirtualVertex<CursosVertex, CursosEdge, Integer> {
	//El Vértice será: (curso, tematicas por cubrir, centros elegidos). 

	public static CursosVertex of(Integer i, Set<Integer> set, Set<Integer> centros) {
		return new CursosVertex(i, set, centros);
	}

	public static CursosVertex initial() {
		return of(0, Set2.copy(DatosCursos.getTematicas()), Set2.empty());
	}

	public static Predicate<CursosVertex> goal() {
		return v -> v.index() == DatosCursos.getNumCursos();
	}

	public static Predicate<CursosVertex> goalHasSolution() {
		return v -> v.remaining().isEmpty();
	}

	@Override
	public List<Integer> actions() {

		List<Integer> alternativas = List2.empty();
		if (index < DatosCursos.getNumCursos()) {
			alternativas = remaining.isEmpty() ? List2.empty() : List2.of(0); //Si no hay tematicas restantes, no hay alternativas
			Set<Integer> restantesActualizados = Set2.difference(remaining, DatosCursos.getTematicasCurso(index)); //Si le asignas que te queda
			if (centroEsPosible(index, centros) && cursoTrataTematicaRestante(index, remaining) 
					&& !restantesActualizados.equals(remaining)) { //Si el centro es posible y el curso trata alguna tematica restante
				if (index == DatosCursos.getNumCursos() - 1) { //Si es el ultimo curso
					alternativas = restantesActualizados.isEmpty() ? List2.of(1) : List2.of(0);
					} 	else {
						alternativas.add(1);
					}
				}
			} 
		return alternativas;
	}
	
	//metodo para comprobar si el curso trata alguna de las tematicas restantes
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

	@Override
	public CursosVertex neighbor(Integer a) {
		Set<Integer> remainingNuevo = Set2.copy(remaining);
		Set<Integer> centrosNuevo = Set2.copy(centros);
		if(a==1) {
			remainingNuevo = Set2.difference(remaining, DatosCursos.getTematicasCurso(index));
			centrosNuevo.add(DatosCursos.getCentroCurso(index));
		}

		return of(index + 1, remainingNuevo, centrosNuevo); //Nuevo curso con las tematicas y centros actualizados
	}

	@Override
	public CursosEdge edge(Integer a) {
		return CursosEdge.of(this, this.neighbor(a), a);
	}

	// el greedy del voraz:
	public CursosEdge greedyEdge() {
		
		CursosEdge res = null;
		Set<Integer> restantesActualizados = Set2.difference(remaining, DatosCursos.getTematicasCurso(index));

		if (centros.contains(DatosCursos.getCentroCurso(index)) || (centros.size() < DatosCursos.maxCentros)) {
			res = restantesActualizados.equals(remaining) ? edge(0) : edge(1);
		} else {
			res = edge(0);
		}
		return res;
	}

	public String toString() {
		return String.format("%d; %d , %d", index, remaining.size(), centros.size());
	}

}
