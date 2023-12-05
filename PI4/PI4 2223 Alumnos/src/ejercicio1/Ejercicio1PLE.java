package ejercicio1;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.jgrapht.alg.util.Pair;

import _datos.DatosCafe;
import _datos.DatosCafe.Tipo;
import _datos.DatosCafe.Variedad;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve.AuxGrammar;


public class Ejercicio1PLE {
	public static List<Tipo> tipos;
	public static List<Variedad> variedades;
	
	//Metodo para devolver la cantidad  de tipos de cade
	public static Integer getNumTipos() {
		return tipos.size(); //cantidad de tipos de cafe
	}
	
	//Metodo para devolver la cantidad de variedades
	public static Integer getNumVariedades() {
		return variedades.size();
	}
	
	//Metodo para obtener la cantidad de un tipo deseado
	public static Integer getCantidadTipo(Integer j) {
		return tipos.get(j).kgDisponibles();
	}
	
	//Metodo para obtener el beneficio de una variedad deseada
	public static Integer getBeneficioVariedad(Integer i) {
		return variedades.get(i).beneficio();
	}
	
	//Metodo para obtener la cantidad de un cafe deseado en una variedad deseada
	public static Double getCantidadCafeEnVariedad(Integer j, Integer i) {
		Variedad variedad = variedades.get(i); //variedad deseada
 		Tipo tipo = tipos.get(j); //cafe deseado
		List<Pair<String, Double>> composiciones = variedad.composiciones().stream()
				.filter(x -> x.getFirst().equals(tipo.nombre())) //filtro buscando que el nombre de la variedad sea el mismo que el deseado
				.collect(Collectors.toList()); //
		if (!composiciones.isEmpty()) { //si existe la composicion buscada
			return composiciones.get(0).getSecond(); //devuelve la cantidad
		} else {
			return 0.0;
		}
	}
	
	//Modelo de ejercicio
	public static void ejercicio1_model(Integer datosEntrada) throws IOException {
		DatosCafe.iniDatos("ficheros/Ejercicio1DatosEntrada"+datosEntrada+".txt"); //Inicializo con los datos del fichero

		//Inicializo los atributos necesarios
		tipos= DatosCafe.getTipos();
		variedades = DatosCafe.getVariedades();
		
		//si cambia el fichero de datos de entrada, cambiar tambien el nº del .lp para no sobreescribirlo
		AuxGrammar.generate(Ejercicio1PLE.class,"lsi_models/Ejercicio1.lsi","gurobi_models/Ejercicio1-"+datosEntrada+".lp");
		GurobiSolution solution = GurobiLp.gurobi("gurobi_models/Ejercicio1-"+datosEntrada+".lp");
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
