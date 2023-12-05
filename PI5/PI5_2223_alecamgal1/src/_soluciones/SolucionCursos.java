package _soluciones;

import java.util.List;
import org.jgrapht.GraphPath;

import _datos.DatosCursos;
import _datos.DatosCursos.Curso;
import ejercicios.ejercicio2.CursosEdge;
import us.lsi.common.List2;
import ejercicios.ejercicio2.CursosVertex;

public class SolucionCursos implements Comparable<SolucionCursos> {

	//Metodo para crear una solucion a partir de una lista
	public static SolucionCursos of_Range(List<Integer> ls) {
		return new SolucionCursos(ls);
	}
	
	// Ahora en la PI5
	public static SolucionCursos of(GraphPath<CursosVertex, CursosEdge> path) {
		List<Integer> ls = path.getEdgeList().stream().map(e -> e.action()).toList();
		SolucionCursos res = of_Range(ls);
		res.path = ls;
		return res;
	}

	private Double coste;
	private List<Integer> solucion;
	
	// Ahora en la PI5
	private List<Integer> path;
	
	//Constructor de solucion vacia
	private SolucionCursos() {
		coste = 0.;
		solucion = List2.empty();
	}
	
	//Constructor de solucion a partir de una lista
	private SolucionCursos(List<Integer> ls) {
		System.out.println(ls);
		//Inicializo los atributos vacios
		coste = 0.;
		solucion = ls;
		for(int n=0; n<ls.size(); n++) { //bucle para recorrer cursos
			if(ls.get(n)>0) {//Si se selecciona un curso y, por tanto, es mayor que 0
				//Se añaden las soluciones
				Curso c = DatosCursos.getCursos().get(n);
				coste += c.coste();
			}
		}
	}
	
	//Metodo para crear una solucion vacia
	public static SolucionCursos empty() {
		return new SolucionCursos();
	}
	
	//Metodo para mostrar la solucion
	public String toString() {
		String res = "Cursos elegidos: {";
		for (int i = 0; i < solucion.size(); i++) {
			if(solucion.get(i)== 1) { //si el curso se ha escogido
				res += "S" + i + ","; //se añade
			}
		}
		res += "}\nCoste Total: " + coste;
		return path == null ? res : String.format("%s\nPath de la solucion: %s", res, path);
	}
	
	@Override
	public int compareTo(SolucionCursos o) {
		return coste.compareTo(o.coste);
	}
}
