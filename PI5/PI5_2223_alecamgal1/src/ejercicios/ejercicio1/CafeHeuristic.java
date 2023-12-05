package ejercicios.ejercicio1;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import _datos.DatosCafe;

public class CafeHeuristic {
	
	// Lo más optimista: las combinaciones que quedan serán lo mejor posible
	public static Double heuristic(CafeVertex v1, Predicate<CafeVertex> goal, CafeVertex v2) {
		return IntStream.range(v1.index(), DatosCafe.getNumVariedades()) //Todas las posibilidades restantes
		         .mapToDouble(i -> mejorOpcion(i, v1.remaining())).sum(); //Escojo la mejor
	}

	private static Double mejorOpcion(Integer i, List<Double> remaining) {
		CafeVertex v = CafeVertex.of(i, remaining);
		return IntStream.range(0, DatosCafe.getMaxCantidadVariedad(i))
				.filter(j -> v.esPosibleFabricarCantidad(j)) //Filtrar por las que no sea posible producir
				.boxed()
				.mapToDouble(j -> DatosCafe.getBeneficioVariedad(v.index())) //Convierto las opciones en sus beneficios
				.max().orElse(-1000.); //Escojo la mejor o añado un error si no he podido coger la mejor opción
	}	
}
