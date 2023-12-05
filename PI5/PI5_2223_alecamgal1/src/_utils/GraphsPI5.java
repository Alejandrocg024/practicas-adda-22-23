package _utils;

import java.util.function.Predicate;

import ejemplos.ejemplo1.MulticonjuntoEdge;
import ejemplos.ejemplo1.MulticonjuntoHeuristic;
import ejemplos.ejemplo1.MulticonjuntoVertex;
import ejemplos.ejemplo2.SubconjuntosEdge;
import ejemplos.ejemplo2.SubconjuntosHeuristic;
import ejemplos.ejemplo2.SubconjuntosVertex;
import ejemplos.ejemplo3.AlumnosEdge;
import ejemplos.ejemplo3.AlumnosHeuristic;
import ejemplos.ejemplo3.AlumnosVertex;
import ejercicios.ejercicio1.CafeEdge;
import ejercicios.ejercicio1.CafeHeuristic;
import ejercicios.ejercicio1.CafeVertex;
import ejercicios.ejercicio2.CursosEdge;
import ejercicios.ejercicio2.CursosHeuristic;
import ejercicios.ejercicio2.CursosVertex;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraphBuilder;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

// Clase Factoria para crear los constructores de los grafos de los ejemplos y ejercicios
public class GraphsPI5 {
	
	// Ejemplo1: Builder
	public static EGraphBuilder<MulticonjuntoVertex, MulticonjuntoEdge>
	multisetBuilder(MulticonjuntoVertex v_inicial, Predicate<MulticonjuntoVertex> es_terminal) { 
		return EGraph.virtual(v_inicial, es_terminal, PathType.Sum, Type.Min)
				// TODO Defina en el tipo vertice un m. static / Predicate para los vertices solucion
				.goalHasSolution(MulticonjuntoVertex.goalHasSolution())
				.heuristic(MulticonjuntoHeuristic::heuristic);
	}
	
	// Ejemplo2: Builder
	public static EGraphBuilder<SubconjuntosVertex, SubconjuntosEdge>
	subsetBuilder(SubconjuntosVertex v_inicial, Predicate<SubconjuntosVertex> es_terminal) { 
		return EGraph.virtual(v_inicial, es_terminal, PathType.Sum, Type.Min)
				// TODO Defina en el tipo vertice un m. static / Predicate para los vertices solucion
				.goalHasSolution(SubconjuntosVertex.goalHasSolution())
				.heuristic(SubconjuntosHeuristic::heuristic);
	}
	
	// Ejemplo3: Builder
	public static EGraphBuilder<AlumnosVertex, AlumnosEdge>
	alumnosBuilder(AlumnosVertex v_inicial, Predicate<AlumnosVertex> es_terminal) { 
		return EGraph.virtual(v_inicial, es_terminal, PathType.Sum, Type.Max)
				// TODO Defina en el tipo vertice un m. static / Predicate para los vertices solucion
				.goalHasSolution(AlumnosVertex.goalHasSolution())
				.heuristic(AlumnosHeuristic::heuristic);
	}
	
	// Ejercicio1: Builder
	public static EGraphBuilder<CafeVertex, CafeEdge>
	cafeBuilder(CafeVertex v_inicial, Predicate<CafeVertex> es_terminal) { 
		return EGraph.virtual(v_inicial, es_terminal, PathType.Sum, Type.Max) //Estamos maximizando
				.goalHasSolution(CafeVertex.goalHasSolution())
				.heuristic(CafeHeuristic::heuristic);
	}
	
	// Ejercicio2: Builder
	public static EGraphBuilder<CursosVertex, CursosEdge> 
	cursosBuilder(CursosVertex v_inicial,Predicate<CursosVertex> es_terminal) {
		return EGraph.virtual(v_inicial, es_terminal, PathType.Sum, Type.Min) //Estamos minimizando
				.goalHasSolution(CursosVertex.goalHasSolution())
				.heuristic(CursosHeuristic::heuristic);
	}
	
}
