package ejercicios.ejercicio1;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import _datos.DatosCafe;
import us.lsi.common.List2;
import us.lsi.graphs.virtual.VirtualVertex;

public record CafeVertex(Integer index, List<Double> remaining) 
     implements VirtualVertex<CafeVertex,CafeEdge,Integer> {
	//El Vértice será: (variedad por fabricar, cantidades de cada tipo restantes)

	public static CafeVertex initial() {
		return of(0, DatosCafe.tipos.stream().map(t -> t.kgDisponibles().doubleValue()).toList());
	}
	
	public static CafeVertex of(Integer i, List<Double> rest) {
		return new CafeVertex(i, rest);
	}
	
	public static Predicate<CafeVertex> goal(){
		return v -> v.index() == DatosCafe.getNumVariedades(); //Se cumple cuando se han pasado por todas las variedades
	}
	
	public static Predicate<CafeVertex> goalHasSolution() {
		return v -> true; //No nos dicen que sea necesario que se gasten todos los kg.
	}
	
	@Override
	public List<Integer> actions() {
		List<Integer> alternativas = List2.empty();
		if(index < DatosCafe.getNumVariedades()) {
			alternativas = IntStream.range(0, DatosCafe.getMaxCantidadVariedad(index) + 1) //Una alternativa por cantidad posible de producir
					.filter(j -> esPosibleFabricarCantidad(j)) //Si la cantidad que quiero hacer sobrepasa la disponible, no se toma esa alternativa
					.boxed()
					.toList();
		}
		return alternativas;
	}

	public Boolean esPosibleFabricarCantidad(Integer cantidad) {//Metodo auxiliar para combrobar si puedo fabricar la cantidad dada
		Boolean res = true;
		Double cantidadHecha;
		Double cantidadDisponible;
		for(int i=0; i< DatosCafe.getNumTipos() ; i++) {
			cantidadHecha = DatosCafe.getCantidadCafeEnVariedad(i, index) * cantidad;
			cantidadDisponible = remaining.get(i);
			if (cantidadHecha > cantidadDisponible) {
				res = false; //Si no la puedo fabricar
			}
		}
		return res;
	}

	@Override
	public CafeVertex neighbor(Integer a) {
		List<Double> remainingNuevo = List2.empty();
		for(int i=0; i< DatosCafe.getNumTipos(); i++) {
			Double cantidadHecha = a * DatosCafe.getCantidadCafeEnVariedad(i, index);
			remainingNuevo.add(remaining.get(i) - cantidadHecha);
		}
		return of(index+1, remainingNuevo); //actualizo los kgs restantes y la variedad
	}

	@Override
	public CafeEdge edge(Integer a) {
		return CafeEdge.of(this,this.neighbor(a),a);
	}
	
	// Se explica en practicas. Algoritmo voraz
	public CafeEdge greedyEdge() {
		Comparator<Integer> cmp = Comparator.comparing(j -> j);
		
		Integer a = IntStream.range(0, DatosCafe.getMaxCantidadVariedad(index) + 1) //Una alternativa por cantidad posible de producir
		.filter(j -> esPosibleFabricarCantidad(j)) //Compruebo que lo pueda producir
		.boxed().max(cmp).orElse(0); //Me quedo con el mayor posible
		
		return edge(a);
	}

	@Override
	public String toString() {
		return String.format("%d", index);
	}
	
	
}
