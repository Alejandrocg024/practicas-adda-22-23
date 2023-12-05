package ejemplos.ejemplo2;

import java.util.function.Predicate;
import java.util.stream.IntStream;

import _datos.DatosSubconjuntos;
import us.lsi.common.List2;

public class SubconjuntosHeuristic {
	
	//lo mas optimista: si todavia quedan por cubrir, el de menor peso de los que quedna lo hara
	// Se explica en practicas.
	public static Double heuristic(SubconjuntosVertex v1, Predicate<SubconjuntosVertex> goal, SubconjuntosVertex v2) {
		return v1.remaining().isEmpty()? 0.: 
			IntStream.range(v1.index(), DatosSubconjuntos.getNumSubconjuntos())
			.filter(i -> !List2.intersection(v1.remaining(), 
					DatosSubconjuntos.getElementos(i)).isEmpty())
			//Penalizacion estilo genetico con el orElse
			.mapToDouble(i -> DatosSubconjuntos.getPeso(i)).min().orElse(100.);
	}	

}

