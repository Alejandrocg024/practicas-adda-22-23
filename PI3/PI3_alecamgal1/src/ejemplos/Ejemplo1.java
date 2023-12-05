package ejemplos;

import java.util.function.Predicate;

import org.jgrapht.Graph;

import datos.Carretera;
import datos.Ciudad;
import ejemplos.Ejemplo1;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.views.SubGraphView;

public class Ejemplo1 {
	
	/*
	 * A partir de un grafo no dirigido y ponderado cuyos vértices son ciudades y cuyas aristas son carreteras, se pide:
	 * 
	 * 		A) Obtener un subgrafo con los vértices que cumplen una propiedad y las 
	 * 			aristas que cumplen otra propiedad dada. NO debe crear un grafo nuevo, sino
	 * 			obtener una vista del grafo original. Muestre el subgrafo resultante
	 * 			configurando su apariencia de forma que se muestren los vértices en los que
	 * 			inciden más de 1 arista de un color diferente al resto de vértices.
	 * 
	 * 		B) Realice pruebas con los siguientes predicados usando los grafos de Andalucía y Castilla La Mancha:
	 * 			1. Ciudades cuyo nombre contiene la letra “e”, y carreteras con menos de 200 km de distancia.
	 * 			2. Ciudades que poseen menos de 500.000 habitantes, y carreteras cuya ciudad origen o destino tiene un nombre de más de 5 caracteres y
	 * 			poseen más de 100 km de distancia. 
	 */
	
	/*
	 * crea vista del grado, recibe el strign con el nombre de la vista que vamos a guardar
	 * grafo
	 * predicado sobre el tipo ciudad que vamos a aplicar
	 * predicado sobre el tipo carretera que vamos a aplicar
	 * string con el nombre del subgrafo que vamos a generar
	 */
	
	public static void crearVista(String file, Graph<Ciudad, Carretera> g, Predicate<Ciudad> pv,
			Predicate<Carretera> pa, String nombreVista) {
		
		//este metodo es util para el ejercicio 1a
		Graph<Ciudad, Carretera> vista = SubGraphView.of(g,  pv, pa);
		/*
		 * recibe el grafo, predicado a aplicar los vertices y predicado aplicado a las aristas
		 * si no hace falta algun predicado lo ponemos a true e -> true
		 */
		
		//De nuevo usamos toDot para guardar el subgrafo:
		GraphColors.toDot(vista, "resultados/ejemplo1/" + file + nombreVista + ".gv", //ruta donde guardamos el resultados
				x -> x.nombre(), //nos indica el nombre de los vertices
				x -> x.nombre(), //nos indica el nombre de las aristas
				//vistas.edgeOd(v) devuelce el n de aristas de un vertice
				//Color if (color a aplicar si es true, condicion
				v -> GraphColors.colorIf(Color.red, vista.edgesOf(v).size() > 1),
				//estilo, mediante color if coloreamos un vertice si tiene mas de una arista
				e -> GraphColors.color(Color.black));
		
		System.out.println(file + nombreVista + ".gv generado en " + "resultados/ejemplo1");
	}

}
