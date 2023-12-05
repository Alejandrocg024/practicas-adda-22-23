package _datos;

import java.util.List;
import java.util.Set;

import us.lsi.common.Files2;
import us.lsi.common.List2;
import us.lsi.common.Set2;
import us.lsi.common.String2;

public class DatosCurso {
	public static List<Curso> cursos;
	public static Integer maxCentros;
	
	//Creo Tipo con el que almacenare los cursos
	public static record Curso(Set<Integer> tematicas, Double coste, Integer centro) { 
		public static Curso create(String l) {
			//Ejemplo de linea "{1,2,3,4}:10.0:0"
			Set<Integer> tematicas = Set2.empty();
			String[] linea = l.split(":");
			String[] tem = linea[0].replace("{", "").replace("}", "").trim().split(",");
			for(String t: tem) {
				tematicas.add(Integer.parseInt(t));
			}
 			return new Curso(tematicas, Double.parseDouble(linea[1]), Integer.parseInt(linea[2]));
		}
		
		@Override
		public String toString() {		
			return String.format("Curso con tematicas= %s, con coste= %f impartido en el centro= %d", tematicas, coste, centro);
		}		
	}
	
	//Metodo para, desde un fichero, inicializar los datos
	public static void iniDatos(String fichero) { //
		cursos = List2.empty();
		for(String linea: Files2.linesFromFile(fichero)) { //Recorro cada linea del fichero
			if(linea.startsWith("M")) { //Si empieza por M, será la indicacion del máximo de cursos
				maxCentros = Integer.parseInt(linea.split("=")[1].trim()); //obtengo el valor entero de los maximos cursos
			} else {
				Curso c = Curso.create(linea);
				cursos.add(c);
			}
		}

		toConsole();
	}
	
	//Metodo para obtener los distintos cursos
	public static List<Curso> getCursos(){
		return cursos;
	}
	
	//Metodo para obtener el numero maximo de centros en el que te puedes matricular
	public static Integer getMaxCentros(){
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

	// Metodo para obtener la cantidad de cursos
	public static Integer getNumCursos() {
		return cursos.size();
	}

	// Metodo para obtener la cantidad de tematicas
	public static Integer getNumTematicas() {
		return getTematicas().size();
	}

	// metodo para obtener la cantidad de centros
	public static Integer getNumCentros() {
		return getCentros().size();
	}

	// metodo que devuelve 1 si el curso i trata la tematica j
	public static Integer trataTematica(Integer i, Integer j) {
		Integer res = 0;
		if (cursos.get(i).tematicas().contains(getTematicas().get(j))) {
			res = 1;
		}
		return res;
	}

	// metodo que devuelve el coste del curso i
	public static Double getCosteCurso(Integer i) {
		return cursos.get(i).coste();
	}

	// metodo que devuelve 1 si se imparte el curso i en el centro k
	public static Integer imparteCurso(Integer i, Integer k) {
		Integer res = 0;
		if (cursos.get(i).centro().equals(getCentros().get(k))) {
			res = 1;
		}
		return res;
	}

	// Metodo para mostrar en la consola
	public static void toConsole() {
		String2.toConsole(cursos,"cursos");
		String2.toConsole("Numero maximo de centros diferentes = " + maxCentros);
		String2.toConsole(String2.linea());	
	}
	
	// Test de la lectura del fichero
	public static void main(String[] args) {
		iniDatos("ficheros/Ejercicio2DatosEntrada1.txt");		
	}
	
}
