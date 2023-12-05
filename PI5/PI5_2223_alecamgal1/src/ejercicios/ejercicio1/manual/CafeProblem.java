package ejercicios.ejercicio1.manual;

import java.util.List;
import java.util.stream.IntStream;

import _datos.DatosCafe;
import ejercicios.ejercicio1.CafeVertex;
import us.lsi.common.List2;

public record CafeProblem(Integer index, List<Double> remaining){
	//La gran mayoria de procedimientos de esta clase son muy parecidas a CafeVertex
	public static CafeProblem initial() {
		return of(0, DatosCafe.tipos.stream().map(t -> t.kgDisponibles().doubleValue()).toList());
	}
	
	public static CafeProblem of(Integer i, List<Double> rest) {
		return new CafeProblem(i, rest);
	}
	
	public List<Integer> actions(){
		List<Integer> alternativas = List2.empty();
		if(index < DatosCafe.getNumVariedades()) {
			alternativas = IntStream.range(0, DatosCafe.getMaxCantidadVariedad(index) + 1)
					.filter(j -> esPosibleFabricarCantidad(j))
					.boxed()
					.toList();
		}
		return alternativas;
	}
	
	public Boolean esPosibleFabricarCantidad(Integer cantidad) {
		Boolean res = true;
		Double cantidadHecha;
		Double cantidadDisponible;
		for(int i=0; i< DatosCafe.getNumTipos() ; i++) {
			cantidadHecha = DatosCafe.getCantidadCafeEnVariedad(i, index) * cantidad;
			cantidadDisponible = remaining.get(i);
			if (cantidadHecha > cantidadDisponible) {
				res = false;
			}
		}
		return res;
	}
	
	public CafeProblem neighbor(Integer a) {
		List<Double> remainingNuevo = List2.empty();
		for(int i=0; i< DatosCafe.getNumTipos(); i++) {
			Double cantidadHecha = a * DatosCafe.getCantidadCafeEnVariedad(i, index);
			remainingNuevo.add(remaining.get(i) - cantidadHecha);
		}
		return of(index+1, remainingNuevo); //Si hemos metido al alumno, restar 1 en la posicion a del remaining
	}

	//Lo mas optimista: si todavia falta, con el mayor de los que quedan puede ser	
	public Double heuristic() {
		return IntStream.range(index, DatosCafe.getNumVariedades())
		         .mapToDouble(i -> mejorOpcion(i, remaining)).sum();
	}

	private Double mejorOpcion(Integer i, List<Double> remaining) {
		CafeVertex v = CafeVertex.of(i, remaining);
		return IntStream.range(0, DatosCafe.getMaxCantidadVariedad(i))
				.filter(j -> esPosibleFabricarCantidad(j))
				.boxed()
				.mapToDouble(j -> DatosCafe.getBeneficioVariedad(v.index())).max().orElse(-1000.);
	}	
	
	
}
