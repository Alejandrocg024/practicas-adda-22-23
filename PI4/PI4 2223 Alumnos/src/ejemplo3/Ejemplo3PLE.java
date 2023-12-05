package ejemplo3;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import _datos.DatosAlumnos;
import _datos.DatosAlumnos.Alumno;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve.AuxGrammar;


public class Ejemplo3PLE {
	public static List<Alumno> alumnos;
	
	public static Integer getNumAlumnos() {
		return alumnos.size();
	}
	
	public static Integer getNumGrupos() {
		return alumnos.get(0).afinidades().size();
	}
	
	public static Integer getTamGrupo() {
		return getNumAlumnos()/getNumGrupos();
	}
	
	public static Integer getAfinidad(Integer i, Integer j) {
		return alumnos.get(i).getAfinidad(j);
	}
	
	public static void ejemplo3_model() throws IOException {
		DatosAlumnos.iniDatos("ficheros/Ejercicio1DatosEntrada1.txt");

		alumnos = DatosAlumnos.getAlumnos();
		
		//si cambia el fichero de datos de entrada, cambiar tambien el nº del .lp para no sobreescribirlo
		AuxGrammar.generate(Ejemplo3PLE.class,"lsi_models/Ejercicio1.lsi","gurobi_models/Ejercicio-1.lp");
		GurobiSolution solution = GurobiLp.gurobi("gurobi_models/Ejercicio1.lp");
		Locale.setDefault(new Locale("en", "US"));
		System.out.println(solution.toString((s,d)->d>0.));
	}
	
	public static void main(String[] args) throws IOException {	
		ejemplo3_model();
	}

}
