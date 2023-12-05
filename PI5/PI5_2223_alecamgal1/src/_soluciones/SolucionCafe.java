package _soluciones;

import java.util.List;

import org.jgrapht.GraphPath;

import _datos.DatosCafe;
import _datos.DatosCafe.Variedad;
import ejercicios.ejercicio1.CafeEdge;
import ejercicios.ejercicio1.CafeVertex;
import us.lsi.common.List2;

public class SolucionCafe {
	
	//Metodo para crear una solucion a partir de una lista
	public static SolucionCafe of(List<Integer> ls) {
		return new SolucionCafe(ls);
	}
	
	// Ahora en la PI5
	public static SolucionCafe of(GraphPath<CafeVertex, CafeEdge> path) {
		List<Integer> ls = path.getEdgeList().stream().map(e -> e.action()).toList(); //Lista de acciones del graph path de la solucion.
		SolucionCafe res = of(ls);
		res.path = ls;
		return res;
	}
	
	//Metodo para crear una solucion vacia
	public static SolucionCafe empty() {
		return new SolucionCafe();
	}

	private double beneficio;
	private List<Variedad> solucionesVariedades;
	private List<Integer> solucion;
	private List<Integer> path;
	
	//Constructor de solucion vacia
	private SolucionCafe() {
		beneficio = 0.;
		solucionesVariedades = List2.empty();
		solucion = List2.empty();
	}
	
	//Constructor de solucion a partir de una lista
	private SolucionCafe(List<Integer> ls) {
		//Inicializo los atributos vacios
		beneficio = 0.;
		solucionesVariedades = List2.empty();
		solucion = List2.empty();
		for(int n=0; n<ls.size(); n++) {
			if(ls.get(n)>0) {
				//Si se selecciona una variedad y, por tanto, es mayor que 0kgs, se añaden las soluciones.
				beneficio += ls.get(n) * DatosCafe.getBeneficioVariedad(n);
				solucionesVariedades.add(DatosCafe.getVariedades().get(n));
				solucion.add(ls.get(n));
			}
		}
	}
	
	//Metodo para mostrar la solucion
	public String toString() {
		String res = "Variedades de cafe seleccionadas:";
		for (int i = 0; i < solucion.size(); i++) {
			res += String.format("\n%s: %d kgs", solucionesVariedades.get(i).nombre(), solucion.get(i));
		}
		res += "\nBeneficio: " + beneficio;
		return path==null? res: String.format("%s\nPath de la solucion: %s", res, path);	
	}
}
