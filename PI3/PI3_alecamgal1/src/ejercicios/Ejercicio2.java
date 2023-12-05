package ejercicios;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.tour.HeldKarpTSP;

import datos.Ciudades;
import datos.Trayecto;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.colors.GraphColors.Style;
import us.lsi.common.List2;
import us.lsi.common.Map2;
import us.lsi.graphs.views.SubGraphView;

public class Ejercicio2 {
	/*
	 * 2. Un grupo de amigos desea visitar una serie de ciudades haciendo uso de un tipo de 
	 * 	transporte que sólo relaciona algunas de ellas. Se tiene un grafo no dirigido y ponderado
	 * 	cuyos vértices son dichas ciudades y cuyas aristas representan los posibles trayectos entre
	 * 	ellas. De cada Ciudad se conoce su puntuación (valor entero en [1,5]), basada en el interés
	 * 	que tienen dicho grupo de personas en visitarla, y de cada Trayecto se conoce su precio y duración. 
	 */
	
	/*
	 * 		A). Determine cuántos grupos de ciudades hay y cuál es su composición. Dos
	 * 		ciudades pertenecen al mismo grupo si están relacionadas directamente entre sí o
	 * 		si existen algunas ciudades intermedias que las relacionan. Muestre el grafo
	 * 		configurando su apariencia de forma que se coloree cada grupo de un color diferente.
	 */
	
	public static List<Set<Ciudades>> apartadoA(Graph<Ciudades, Trayecto> g, String file) {
		
		var alg = new ConnectivityInspector<>(g); 
		List<Set<Ciudades>> ls = alg.connectedSets(); //Utilizo el algoritmo para obtener todas las componentes conexas
		
		//toDot en el que guardaremos el grafo original pero con los vertices y aristas de la solucion modificados
		GraphColors.toDot(g, "resultados/ejercicio2/" + file + "_ApartadoA.gv",
				x-> x.nombre(),
				x -> "",
				v -> GraphColors.color(asignaColor(v, ls, alg)), 
				e -> GraphColors.color(asignaColor(g.getEdgeSource(e), ls, alg)));
		
		System.out.println("Usando los datos de entrada: " + file + ".txt -> Grafo " + file 
				+ "_ApartadoA.gv generado en " + "resultados/ejercicio2");
		
		return ls;
	}

	//Metodo que devuelve el color de una Ciudad. Las Ciudades conexas del grafo tendran el mismo color
	private static Color asignaColor(Ciudades v, List<Set<Ciudades>> ls,
			ConnectivityInspector<Ciudades, Trayecto> alg) {
		Color[] vc = Color.values(); //Obtengo todos los colores
		Set<Ciudades> s = alg.connectedSetOf(v); //Obtengo las ciudades conexas
		return vc[ls.indexOf(s)]; //Devuelvo el color para la ciudad conexa dependiendo de la lista de componentes conexas
	}
	
	/*
	 * 		B). Determine cuál es el grupo de ciudades a visitar si se deben elegir las ciudades
	 * 		conectadas entre sí que maximice la suma total de las puntuaciones. Muestre el
	 * 		grafo configurando su apariencia de forma que se resalten dichas ciudades.
	 */
	public static Set<Ciudades> apartadoB(Graph<Ciudades, Trayecto> g, String file) {
		
		var alg = new ConnectivityInspector<>(g);
		Map<Set<Ciudades>, Integer> ciudadesConexasConPuntuacion = Map2.empty();
		List<Set<Ciudades>> ls = alg.connectedSets();
		for (Set<Ciudades> ciudadesConexas : ls) { //Analiza cada componente conexa
			Integer suma = 0;
			for (Ciudades c : ciudadesConexas) {
				suma += c.puntuacion();
			}
			ciudadesConexasConPuntuacion.put(ciudadesConexas, suma); //Agrega la clave de las ciudades y sus puntuaciones
		}
		
		//De un map en el que las claves son las ciudades y los valores son las puntuaciones 
		//obtengo la clave que tiene mayor valor que es la mayor puntuacion
		Set<Ciudades> res = ciudadesConexasConPuntuacion.entrySet().stream()
				.max(Comparator.comparing(entry -> entry.getValue()))
				.get() 
				.getKey();
		
		
		//toDot en el que guardaremos el grafo original pero con los vertices y aristas de la solucion modificados
		GraphColors.toDot(g, "resultados/ejercicio2/" + file + "_ApartadoB.gv",
				x-> x.toString() + "\n" + String.valueOf(x.puntuacion()) + " puntos",
				x -> "",
				v -> GraphColors.colorIf(Color.blue, res.contains(v)),
				e -> GraphColors.colorIf(Color.blue, res.contains(g.getEdgeSource(e))));
		
		System.out.println("Usando los datos de entrada: " + file + ".txt -> Grafo " + file + "_ApartadoB.gv generado en " + "resultados/ejercicio2");
		
		return res;
	}
	
	
	/* 		C). Determine cuál es el grupo de ciudades a visitar si se deben elegir las ciudades
	 * 		conectadas entre sí que den lugar al camino cerrado de menor precio que pase por
	 * 		todas ellas. Muestre el grafo configurando su apariencia de forma que se resalte dicho camino.
	 */

	public static Map<List<Ciudades>, Double> apartadoC(Graph<Ciudades, Trayecto> g, String file) {
		Map<GraphPath<Ciudades, Trayecto>, Double> res = Map2.empty();
		
		var alg = new ConnectivityInspector<>(g);
		List<Set<Ciudades>> ls = alg.connectedSets(); //Componentes conexas
		
		for(Set<Ciudades> componenteConexa : ls) { //Analizo cada componente conexa
			
			Graph<Ciudades, Trayecto> subgrafoConexo = SubGraphView.of(g, v -> componenteConexa.contains(v), null); 
			
			//Algoritmo para obtener el camino minimo cerrado que recorra todas las aristas
			var alg2 = new HeldKarpTSP<Ciudades, Trayecto>(); 
			GraphPath<Ciudades, Trayecto> caminoCiudades = alg2.getTour(subgrafoConexo); //Obtengo el camino
			Double precioTrayecto = alg2.getTour(subgrafoConexo).getWeight(); //Peso del camino
			res.put(caminoCiudades, precioTrayecto); //Guardo la pareja Camino-Peso del camino
		}
		
		//Obtengo el camino con menor precio
		Entry<GraphPath<Ciudades, Trayecto>, Double> caminoMinimo = res.entrySet().stream()
				.min(Comparator.comparing(entry -> entry.getValue()))
				.get();
		
		//Del camino obtengo las aristas, vertices y el peso
		List<Ciudades> caminoMinimoCiudades = caminoMinimo.getKey().getVertexList();
		List<Trayecto> caminoMinimoTrayectos = caminoMinimo.getKey().getEdgeList();
		Double caminoMinimoPeso = caminoMinimo.getValue();
		
		//toDot en el que guardaremos el grafo original pero con los vertices y aristas de la solucion modificados
		GraphColors.toDot(g, "resultados/ejercicio2/" + file + "_ApartadoC.gv",
				x-> x.toString(),
				x -> String.valueOf(x.precio()) + " euros",
				v -> GraphColors.colorIf(Color.blue, caminoMinimoCiudades.contains(v)),
				e -> GraphColors.colorIf(Color.blue, caminoMinimoTrayectos.contains(e)));
		
		System.out.println("Usando los datos de entrada: " + file + ".txt -> Grafo " + file 
				+ "_ApartadoC.gv generado en " + "resultados/ejercicio2");
	
		return Map2.of(caminoMinimoCiudades, caminoMinimoPeso); //Devuelvo el camino minimo cerrado y su precio
	}
	
	/* 		D). De cada grupo de ciudades, determinar cuáles son las 2 ciudades (no conectadas
	 * 		directamente entre sí) entre las que se puede viajar en un menor tiempo. Muestre
	 * 		el grafo configurando su apariencia de forma que se resalten las ciudades y el camino entre ellas.
	 */
	
	public static Map<List<Ciudades>, Double> apartadoD(Graph<Ciudades, Trayecto> g, String file){
		Map<List<Ciudades>, Double> res = Map2.empty();
		
		var alg = new ConnectivityInspector<>(g);
		List<Set<Ciudades>> ls = alg.connectedSets(); //Componentes conexas
		var alg2 = new DijkstraShortestPath<>(g); //Algoritmo dijkstra para obtener camino mas corto
		
		for (Set<Ciudades> componenteConexa : ls) { //Recorro las componentes conexas
			//map en el que guardare todos los caminos mínimos de la componente conexa que no esten directamente conectadas 
			//junto con su tiempo
			Map<List<Ciudades>, Double> resConexo = Map2.empty(); 
			Double sumaTiempo = 0.;
			for (Ciudades ciudadDestino : componenteConexa) { //Ciudad destino a analizar
				for (Ciudades ciudadOrigen : componenteConexa) { 
					//Vuelvo a recorrer la componente conexa para analizar la ciudad de destino con las ciudades origen
					GraphPath<Ciudades, Trayecto> gp = alg2.getPath(ciudadOrigen, ciudadDestino); //Camino entre las ciudades
					if (!g.containsEdge(ciudadOrigen, ciudadDestino) && !ciudadOrigen.equals(ciudadDestino)) { 
						//Si no hay una arista entre las dos ciudades y no es la misma ciudad
						sumaTiempo = gp.getWeight();
						resConexo.put(List.of(ciudadOrigen, ciudadDestino), sumaTiempo); 
						//Guardo las ciudades de origen y destino junto con su tiempo
					}
				}
			}
			//De entre todas las parejas ciudades-tiempo, me quedo con la de menor tiempo
			List<Ciudades> caminoMinTiempo = resConexo.entrySet().stream()
					.min(Comparator.comparing(entry -> entry.getValue()))
					.get()
					.getKey();
			res.put(caminoMinTiempo, resConexo.get(caminoMinTiempo));
		}
		//Guardo las ciudades del resultado en una lista para colorearlas luego
		List<Ciudades> ciudadesResultado = List2.empty();
		res.keySet().forEach(x -> ciudadesResultado.addAll(x));
		//Guardo las aristas para colorearlas posteriormente
		List<Trayecto> trayectosResultado = List2.empty();
		res.keySet().forEach(x -> trayectosResultado.addAll(alg2.getPath(x.get(0), x.get(1)).getEdgeList()));
		
		//toDot en el que guardaremos el grafo original pero con los vertices y aristas de la solucion modificados
		GraphColors.toDot(g, "resultados/ejercicio2/" + file + "_ApartadoD.gv",
				x-> x.toString(),
				x -> String.valueOf(x.tiempo()) + " minutos",
				v -> GraphColors.styleIf(Style.bold, ciudadesResultado.contains(v)),
				e -> GraphColors.styleIf(Style.bold, trayectosResultado.contains(e)));
		
		System.out.println("Usando los datos de entrada: " + file + ".txt -> Grafo " + file 
				+ "_ApartadoD.gv generado en " + "resultados/ejercicio2");
		
		return res;
	}
}
