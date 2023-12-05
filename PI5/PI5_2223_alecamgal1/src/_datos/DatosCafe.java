package _datos;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.jgrapht.alg.util.Pair;

import us.lsi.common.Files2;
import us.lsi.common.List2;
import us.lsi.common.String2;

public class DatosCafe {
	public static List<Tipo> tipos;
	public static List<Variedad> variedades;
	
	//Creo Tipo con el que almacenare los tipos de cafe
	public static record Tipo(String nombre, Integer kgDisponibles) { 
		public static Tipo create(String l) {
			//Ejemplo de linea "C01: kgdisponibles=5";
			String[] linea = l.split(":");
			String[] lineaMenor = linea[1].split("=");
 			return new Tipo(linea[0].trim(), Integer.parseInt(lineaMenor[1].replace(";","").trim()));
		}
		
		@Override
		public String toString() {		
			return String.format("%s: kgdisponibles=%d", nombre, kgDisponibles);
		}		
	}
	
	//Creo Variedad con la que almacenaré las variedades
	public static record Variedad(String nombre, Integer beneficio, List<Pair<String, Double>> composiciones) { 
		public static Variedad create(String s) {
			//Ejemplo de linea "P02 -> beneficio=10; comp=(C04:0.2),(C05:0.8)";
			String[] linea = s.split(" ");
			String[] beneficio = linea[2].split("=");
			String[] comps = linea[3].replace("comp=", "").replace(";", "").split(",");
			List<Pair<String, Double>> composiciones = List2.empty();
			for(String c : comps) {
				String[] res = c.replace("(", "").replace(")", "").trim().split(":");
				composiciones.add(Pair.of(res[0], Double.parseDouble(res[1])));
			}
			return new Variedad(linea[0], Integer.parseInt(beneficio[1].replace(";", "").trim()), composiciones);
		}
		
		@Override
		public String toString() {		
			return String.format("%s -> beneficio=%d. Compuesto de %s", nombre, beneficio, composiciones);
		}		
	}
	
	//Metodo para, desde un fichero, inicializar los datos
	public static void iniDatos(String fichero) { 
		tipos = List2.empty();
		variedades = List2.empty();
		for(String linea: Files2.linesFromFile(fichero)) { //Recorro cada linea del fichero
			if(linea.startsWith("C")) { //Si empieza por C, será un tipo
				Tipo t = Tipo.create(linea);
				tipos.add(t);
			} else if(linea.startsWith("P")) { //Si empieza por P, será una variedad
				Variedad p = Variedad.create(linea);
				variedades.add(p);
			}
		}

		toConsole();
	}
	
	public static List<Tipo> getTipos(){
		return tipos;
	}
	
	public static List<Variedad> getVariedades(){
		return variedades;
	}
	
	public static Integer getNumTipos() {
		return tipos.size(); 
	}
		
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
				.collect(Collectors.toList()); 
		if (!composiciones.isEmpty()) { //si existe la composicion buscada
			return composiciones.get(0).getSecond(); //devuelve la cantidad
		} else {
			return 0.0;
		}
	}
	
	//Metodo para obtener la cantidad maxima de kg de una variedad dada que puedo obtener
	public static Integer getMaxCantidadVariedad(Integer i) {
		List<Double> listaMax = List2.empty();
		for(int j = 0; j<tipos.size(); j++) {
			listaMax.add(getCantidadTipo(j)/getCantidadCafeEnVariedad(j,i)); //calculo cuantos kg de esa variedad puedo hacer con los kg totales de ese tipo
		}
		listaMax.sort(Comparator.naturalOrder()); //Ordeno por cantidad
		return listaMax.get(0).intValue();
	}
	
	public static void toConsole() {
		String2.toConsole(tipos,"tipos");
		String2.toConsole(variedades,"variedades");
		String2.toConsole(String2.linea());	
	}
	
	public static void main(String[] args) {
		iniDatos("ficheros/Ejercicio1DatosEntrada1.txt");	
	}
	
}
