package _datos;

import java.util.List;

import us.lsi.common.Files2;
import us.lsi.common.List2;
import us.lsi.common.String2;

public class DatosInvestigadores {
	public static List<Investigador> investigadores;
	public static List<Trabajo> trabajos;
	
	//Creo Tipo con el que almacenare los tipos de cafe
	public static record Investigador(Integer id, Integer capacidad, Integer especialidad) { 
		public static Investigador create(String l) {
			//Ejemplo de linea "INV1: capacidad=6; especialidad=0;"
			String[] linea = l.split(":");
			String[] lineaMenor = linea[1].split(";");
 			return new Investigador(Integer.parseInt(linea[0].replace("INV", "").trim()) //numero id
 					, Integer.parseInt(lineaMenor[0].split("=")[1].trim()), //Capacidad
 					Integer.parseInt(lineaMenor[1].split("=")[1].trim())); //Especialidad
		}
		
		@Override
		public String toString() {		
			return String.format("INV%d: capacidad=%d; especialidad=%d;", id, capacidad, especialidad);
		}		
	}
	
	//Creo Variedad con la que almacenaré las variedades
	public static record Trabajo(Integer id, Integer calidad, List<Integer> diasNecesarios) { 
		public static Trabajo create(String l) {
			//Ejemplo de linea "T01 -> calidad=5; reparto=(0:6),(1:0),(2:0);"
			String[] linea = l.split("-");
			String[] lineaMenor = linea[1].split(";");
			List<Integer> dias = List2.empty();
			String[] diasString = lineaMenor[1].split("=")[1].trim().split(",");
			for(String d : diasString) {
				dias.add(Integer.parseInt(d.split(":")[1].replace(")", "").trim()));
			}
 			return new Trabajo(Integer.parseInt(linea[0].replace("T", "").trim()) //numero id
 					, Integer.parseInt(lineaMenor[0].split("=")[1].trim()), //Calidad
 					dias); //Dias de trabajo para realizar ese trabajo
		}
		
		@Override
		public String toString() {		
			return String.format("T%d -> calidad=%d; reparto=%s;", id, calidad, diasNecesarios);
		}		
	}
	
	//Metodo para, desde un fichero, inicializar los datos
	public static void iniDatos(String fichero) { //
		investigadores = List2.empty();
		trabajos = List2.empty();
		for(String linea: Files2.linesFromFile(fichero)) { //Recorro cada linea del fichero
			if(linea.startsWith("INV")) { //Si empieza por INV, será un investigador
				Investigador i = Investigador.create(linea);
				investigadores.add(i);
			} else if(linea.startsWith("T")) { //Si empieza por T, será un trabajo
				Trabajo t = Trabajo.create(linea);
				trabajos.add(t);
			}
		}

		toConsole();
	}
	
	public static List<Investigador> getInvestigadores() {
		return investigadores;
	}
	
	public static List<Trabajo> getTrabajos() {
		return trabajos;
	}
	
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
	
	//Metodo para mostrar en la consola
	public static void toConsole() {
		String2.toConsole(investigadores,"investigadores");
		String2.toConsole(trabajos,"trabajos");
		String2.toConsole(String2.linea());	
	}
	
	// Test de la lectura del fichero
	public static void main(String[] args) {
		iniDatos("ficheros/Ejercicio3DatosEntrada1.txt");		
	}
	
}
