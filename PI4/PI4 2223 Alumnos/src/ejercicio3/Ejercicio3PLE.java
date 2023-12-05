package ejercicio3;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import _datos.DatosInvestigadores;
import _datos.DatosInvestigadores.Investigador;
import _datos.DatosInvestigadores.Trabajo;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve.AuxGrammar;


public class Ejercicio3PLE {
	public static List<Investigador> investigadores;
	public static List<Trabajo> trabajos;
	
	public static Integer getNumInvestigadores() {
		return investigadores.size();
	}
	
	public static Integer getNumEspecialidades() {
		return trabajos.get(0).diasNecesarios().size();
	}
	
	public static Integer getNumTrabajos() {
		return trabajos.size();
	}
	
	public static Integer trabajadorTieneEspecialidad(Integer i, Integer k) {
		Integer res = 0;
		if (investigadores.get(i).especialidad().equals(k)) {
			res = 1;
		}
		return res;
	}
	public static Integer diasDisponiblesTrabajador(Integer i) {
		return investigadores.get(i).capacidad();
	}
	
	public static Integer diasNecesariosTrabajoEspecialidad(Integer j, Integer k) {
		return trabajos.get(j).diasNecesarios().get(k);
	}
	
	public static Integer getCalidadTrabajo(Integer j) {
		return trabajos.get(j).calidad();
	}
	
	//Modelo de ejercicio
	public static void ejercicio1_model(Integer datosEntrada) throws IOException {
		DatosInvestigadores.iniDatos("ficheros/Ejercicio3DatosEntrada"+datosEntrada+".txt"); //Inicializo con los datos del fichero

		//Inicializo los atributos necesarios
		investigadores= DatosInvestigadores.getInvestigadores();
		trabajos = DatosInvestigadores.getTrabajos();
		
		//si cambia el fichero de datos de entrada, cambiar tambien el nº del .lp para no sobreescribirlo
		AuxGrammar.generate(Ejercicio3PLE.class,"lsi_models/Ejercicio3.lsi","gurobi_models/Ejercicio3-"+datosEntrada+".lp");
		GurobiSolution solution = GurobiLp.gurobi("gurobi_models/Ejercicio3-"+datosEntrada+".lp");
		Locale.setDefault(new Locale("en", "US"));
		System.out.println(solution.toString((s,d)->d>0.));
	}
	
	
	public static void main(String[] args) throws IOException {	
		System.out.println("####################### APARTADO 1-PLE #######################");
		ejercicio1_model(1);
		System.out.println("\n\n####################### APARTADO 2-PLE #######################");
		ejercicio1_model(2);
		System.out.println("\n\n####################### APARTADO 3-PLE #######################");
		ejercicio1_model(3);
	}

}
