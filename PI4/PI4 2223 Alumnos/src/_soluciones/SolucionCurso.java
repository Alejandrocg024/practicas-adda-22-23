package _soluciones;

import java.util.List;

import _datos.DatosCurso;
import _datos.DatosCurso.Curso;

public class SolucionCurso {
	
	//Metodo para crear una solucion a partir de una lista
	public static SolucionCurso of(List<Integer> ls) {
		return new SolucionCurso(ls);
	}
	
	//Metodo para crear una solucion vacia
	public static SolucionCurso empty() {
		return new SolucionCurso();
	}

	private Double coste;
	private List<Integer> solucion;
	
	//Constructor de solucion vacia
	private SolucionCurso() {
		coste = 0.;
	}
	
	//Constructor de solucion a partir de una lista
	private SolucionCurso(List<Integer> ls) {
		System.out.println(ls);
		//Inicializo los atributos vacios
		coste = 0.;
		solucion = ls;
		for(int n=0; n<ls.size(); n++) { //bucle para recorrer cursos
			if(ls.get(n)>0) {//Si se selecciona un curso y, por tanto, es mayor que 0
				//Se añaden las soluciones
				Curso c = DatosCurso.getCursos().get(n);
				coste += c.coste();
			}
		}
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
		return res;
	}
}
