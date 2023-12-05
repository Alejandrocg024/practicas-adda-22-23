package ejercicio2;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import _datos.DatosCurso;
import _datos.DatosCurso.Curso;
import us.lsi.common.List2;
import us.lsi.common.Set2;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve.AuxGrammar;


public class Ejercicio2PLE {
	public static List<Curso> cursos;
	public static Integer maxCentros;
	
	// Metodo para obtener los distintos cursos
	public static List<Curso> getCursos() {
		return cursos;
	}

	// Metodo para obtener el numero maximo de centros en el que te puedes matricular
	public static Integer getMaxCentros() {
		return maxCentros;
	}
	
	//Metodo que devuelve todas las  tematicas
	public static List<Integer> getTematicas(){
		Set<Integer> res = Set2.empty();
		for(Curso c: cursos) {
			res.addAll(c.tematicas());
		}
		//res.sort(Comparator.naturalOrder()); //las ordeno de menor a mayor por nos la dan mezcladas
		return List2.ofCollection(res);
	}
	
	// Metodo que devuelve todos los centros
	public static List<Integer> getCentros() {
		Set<Integer> res = Set2.empty();
		for (Curso c : cursos) {
			res.add(c.centro());
		}
		// res.sort(Comparator.naturalOrder()); //las ordeno de menor a mayor por nos la
		// dan mezcladas
		return List2.ofCollection(res);
	}
	
	//Metodo para obtener la cantidad de cursos
	public static Integer getNumCursos() {
		return cursos.size();
	}
	
	//Metodo para obtener la cantidad de tematicas
	public static Integer getNumTematicas() {
		return getTematicas().size();
	}
	
	//metodo para obtener la cantidad de centros
	public static Integer getNumCentros() {
		return getCentros().size();
	}
	
	//metodo que devuelve 1 si el curso i trata la tematica j
	public static Integer trataTematica(Integer i, Integer j) {
		Integer res = 0;
		if(cursos.get(i).tematicas().contains(getTematicas().get(j))) {
			res = 1;
		}
		return res;
	}
	
	//metodo que devuelve el coste del curso i
	public static Double getCosteCurso(Integer i) {
		return cursos.get(i).coste();
	}
	
	//metodo que devuelve 1 si se imparte el curso i en el centro k
	public static Integer imparteCurso(Integer i, Integer k) {
		Integer res = 0;
		if(cursos.get(i).centro().equals(getCentros().get(k))) {
			res = 1;
		}
		return res;
	}

	
	//Modelo de ejercicio
	public static void ejercicio2_model(Integer datosEntrada) throws IOException {
		DatosCurso.iniDatos("ficheros/Ejercicio2DatosEntrada"+datosEntrada+".txt"); //Inicializo con los datos del fichero

		//Inicializo los atributos necesarios
		cursos = DatosCurso.getCursos();
		maxCentros = DatosCurso.getMaxCentros();
		
		//si cambia el fichero de datos de entrada, cambiar tambien el nº del .lp para no sobreescribirlo
		AuxGrammar.generate(Ejercicio2PLE.class,"lsi_models/Ejercicio2.lsi","gurobi_models/Ejercicio2-"+datosEntrada+".lp");
		GurobiSolution solution = GurobiLp.gurobi("gurobi_models/Ejercicio2-"+datosEntrada+".lp");
		Locale.setDefault(new Locale("en", "US"));
		System.out.println(solution.toString((s,d)->d>0.));
	}
	
	
	public static void main(String[] args) throws IOException {	
		System.out.println("####################### APARTADO 1 #######################");
		ejercicio2_model(1);
		System.out.println("\n\n####################### APARTADO 2 #######################");
		ejercicio2_model(2);
		System.out.println("\n\n####################### APARTADO 3 #######################");
		ejercicio2_model(3);
	}

}
