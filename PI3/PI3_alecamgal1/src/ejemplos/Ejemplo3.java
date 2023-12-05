package ejemplos;

import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.alg.color.GreedyColoring;
import org.jgrapht.alg.interfaces.VertexColoringAlgorithm.Coloring;
import org.jgrapht.graph.DefaultEdge;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Style;

public class Ejemplo3 {
	
	/*
	 * 3. Se desea ubicar un conjunto de n comensales en mesas, de forma que hay ciertos
	 *  çomensales que no se pueden sentar en la misma mesa por ser incompatibles entre
	 *  ellos. Existe simetría en las incompatibilidades.
	 *  
	 *  	a. Diseñe un algoritmo que minimice el número de mesas necesarias para sentar
	 *  	 a todos los comensales teniendo en cuenta las incompatibilidades.
	 *  
	 *  	b. Muestre el tamaño y la composición de cada una de las mesas.
	 *  
	 *  	c. Muestre el grafo configurando su apariencia de forma que todos los
	 *  	 comensales de la misma mesa se muestren del mismo color. 
	 */
	
	
	
	/*
	 * A) Diseñe un algoritmo que minimice el numero de mesas necesarias para
	 * sentar a todos los comensales teniendo en cuenta las incompatibilidades.
	 */
	
	public static void apartados(Graph<String, DefaultEdge> gf, String file) {
		
		var alg = new GreedyColoring<>(gf);
		
		/*
		 * Partiendo de un grafo con V vertices, la idea de greedy color es:
		 * 1. COlorea el primer vertice del primer color
		 * 2. Haz lo siguiente para los vertices v restantes;
		 * 	Consderar el vertice actualmente elegido y colorearlo con un color que no lo tengan los vertices adyacentes a el
		 * 	aparecen en los vertices adyacentes a v, asignar un nuevo color él
		 */
		
		GraphColors.toDot(gf, "resultados/ejemplo3/" + "graforig" + ".gv",
				v -> v.toString(),
				e -> "",
				v -> GraphColors.style(Style.solid),
				e -> GraphColors.style(Style.solid));
		
		
		//apartado B
		Coloring<String> coloring = alg.getColoring(); //obtenemos el número de colores distintos necesarios para cumplir gree
		System.out.println("Mesas necesarias: " + coloring.getNumberColors());
		
		System.out.println(coloring.getColorClasses() + "\nComposicion de las mesas:");
		var composicion = coloring.getColorClasses(); //los vertices asociados a cada color serian las mesas
		for (int i=0; i<composicion.size(); i++) {
			System.out.println("Mesa numero " + (i+1) + ": " + composicion.get(i));
		}
		
		//Apartado C
		Map<String, Integer> map = coloring.getColors(); //obtenemos la mesa para cada vertice
		
		GraphColors.toDot(gf, "resultados/ejemplo3/" + file + ".gv",
				v -> v.toString(),
				e -> "",
				v -> GraphColors.color(map.get(v)),
				e -> GraphColors.style(Style.solid));
		
		System.out.println(file + "C.gv generado en " + "resultados/ejemplo3");
	}
}
