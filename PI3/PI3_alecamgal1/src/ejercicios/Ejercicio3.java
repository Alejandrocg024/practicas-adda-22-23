package ejercicios;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.alg.color.GreedyColoring;
import org.jgrapht.alg.interfaces.VertexColoringAlgorithm.Coloring;
import org.jgrapht.graph.DefaultEdge;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Style;

public class Ejercicio3 {
	/*
	 * 3. En un taller de extraescolares se ofertan una serie de actividades. Se conocen los 
	 * 	alumnos que están interesados en acudir, de forma que cada alumno ha indicado las
	 * 	actividades a las que asistirá. Para minimizar el tiempo en el que el taller estará abierto,
	 * 	se van a impartir actividades en paralelo de forma que se minimicen las franjas horarias con actividades.
	 * 		A). Determine qué actividades deberían impartirse en paralelo y cuántas franjas
	 * 		horarias son necesarias, teniendo en cuenta que no se pueden poner en paralelo
	 * 		actividades que tengan alumnos en común.
	 */
	
	public static List<Set<String>> apartadoA(Graph<String, DefaultEdge> g, String file) {

		//Algoritmo para realizar una vertice-coloracion del grafo 
		//en la que se colearan todas las aristas con el mínimo de colores 
		//con la condicion de que las aristas adyacentes tienen que tener distinto color
		//Es decir, en nuestro ejercicio, las aristas que tengan el mismo color se podrán impartir en paralelo 
		//y la cantidad de colores son las franjas horarias disponibles
		var alg = new GreedyColoring<>(g); 
	
		Coloring<String> coloring = alg.getColoring(); //obtenemos el número de colores distintos necesarios
		var composicion = coloring.getColorClasses(); //Lista con los distintos conjuntos de vertices que pueden tener el mismo color, es decir, actividades que se pueden impartir en paralelo
		
		return composicion;
	}
	
	/*
	 * 		B). Muestre el problema como un grafo en el que los vértices son las actividades, y
	 * 		configure su apariencia de forma que se muestren los vértices coloreados en	función 
	 * 		de la franja horaria en la que se impartan.
	 */
	public static void apartadoB(Graph<String, DefaultEdge> g, String file) {
		//Mismo que en apartado A
		var alg = new GreedyColoring<>(g);
		Coloring<String> coloring = alg.getColoring();
		Map<String, Integer> map = coloring.getColors(); //Map en el que guardamos un vertice y su color.
		
		//toDot en el que guardaremos el grafo original pero con los vertices y aristas de la solucion modificados
		GraphColors.toDot(g, "resultados/ejercicio3/" + file + "_ApartadoB.gv",
				v -> v.toString(),
				e -> "",
				v -> GraphColors.color(map.get(v)), //Coloreo segun el map 
				e -> GraphColors.style(Style.solid));
		
		System.out.println("Usando los datos de entrada: " + file + ".txt -> Grafo " + file + "_ApartadoB.gv generado en " 
				+ "resultados/ejercicio3");
	}
}
