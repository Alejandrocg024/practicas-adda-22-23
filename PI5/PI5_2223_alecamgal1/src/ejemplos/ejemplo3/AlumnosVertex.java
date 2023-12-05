package ejemplos.ejemplo3;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import _datos.DatosAlumnos;
import us.lsi.common.List2;
import us.lsi.graphs.virtual.VirtualVertex;

// Uso el segundo modelo
public record AlumnosVertex(Integer index, List<Integer> remaining) 
     implements VirtualVertex<AlumnosVertex,AlumnosEdge,Integer> {

	public static AlumnosVertex initial() {
		return of(0, List2.ofTam(DatosAlumnos.getTamGrupo(), DatosAlumnos.getNumGrupos()));
	}
	
	public static AlumnosVertex of(Integer i, List<Integer> rest) {
		return new AlumnosVertex(i, rest);
	}
	
	public static Predicate<AlumnosVertex> goal(){
		return v -> v.index() == DatosAlumnos.getNumAlumnos();
	}
	
	public static Predicate<AlumnosVertex> goalHasSolution() {
		return v -> v.remaining().stream().allMatch(e -> e.equals(0));
	}
	
	@Override
	public List<Integer> actions() {
		// TODO Alternativas de un vertice
		//A cuantos grupos puedo mandarlos y de todos los posibles descarto afinidad 0 y que ya no puedan entrar mas
		List<Integer> alternativas = List2.empty();
		if(index < DatosAlumnos.getNumAlumnos()) {
			alternativas = IntStream.range(0, DatosAlumnos.getNumGrupos())
					.filter(j -> DatosAlumnos.getAfinidad(index, j)>0 && remaining.get(j)>0)
					.boxed()
					.toList();
		}
		List<Integer> alternativastr = alternativas;
		return alternativas;
	}

	@Override
	public AlumnosVertex neighbor(Integer a) {
		// TODO Vertice siguiente al actual segun la alternativa a 
		return of(index+1, List2.setElement(remaining, a, remaining.get(a)-1)); //Si hemos metido al alumno, restar 1 en la posicion a del remaining
	}

	@Override
	public AlumnosEdge edge(Integer a) {
		return AlumnosEdge.of(this,this.neighbor(a),a);
	}
	
	// Se explica en practicas. Algoritmo voraz
	public AlumnosEdge greedyEdge() {
		Comparator<Integer> cmp = Comparator.comparing(j -> DatosAlumnos.getAfinidad(index, j));
		
		Integer a = IntStream.range(0, DatosAlumnos.getNumGrupos())
		.filter(j -> DatosAlumnos.getAfinidad(index, j)>0 && remaining.get(j)>0)
		.boxed().max(cmp).orElse(0);
		
		return edge(a);
	}

	@Override
	public String toString() {
		return String.format("%d", index);
	}
	
	
}
