package ejemplos;

import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.tour.HeldKarpTSP;
import org.jgrapht.graph.SimpleWeightedGraph;

import datos.Carretera;
import datos.Ciudad;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.colors.GraphColors.Style;
import us.lsi.graphs.Graphs2;

public class Ejemplo2 {
	
	/*
	 * 2. A partir de un grafo no dirigido y ponderado cuyos vértices son ciudades y cuyas aristas son carreteras, se pide:
	 * 		
	 * 		A. Obtener un nuevo grafo que contenga los mismos vértices y sea completo. Las nuevas aristas tendrán un peso muy grande. Muestre el grafo resultante
	 * 		configurando su apariencia de forma que se resalten las nuevas aristas y los vértices de dichas aristas.
	 * 
	 * 		B. A partir del grafo original, dados dos vértices v1 y v2 de dicho grafo obtener el camino mínimo para ir de v1 a v2. Muestre el grafo original configurando
	 * 		su apariencia de forma que se resalte el camino mínimo para ir de v1 a v2.
	 * 
	 * 		C. Obtener un nuevo grafo dirigido con los mismos vértices y que por cada arista original tenga dos dirigidas y de sentido opuesto con los mismos pesos.
	 * 
	 * 		D. Calcule las componentes conexas del grafo original. Muestre el grafo original 
	 * 		configurando su apariencia de forma que se coloree cada componente conexa de un color diferente. 
	 */

	/*
	 * A) Obtener un nuevo grafo que contenga los mismos vertices y sea completo. Las nuevas aristas
	 * tendran un peso muy grande. Muestre el grafo resultante configurando su apariencia de forma que
	 * se resalten las nuevas aristas y los vertices de dichas aristas
	 */
	
	//recibe un grafo y nombre del fichero con los resultados
	public static void apartadoA(SimpleWeightedGraph<Ciudad, Carretera> gf, String file) {
		//explicitCOmpleteGraog crea un grafo completo a partir de otro
		Graph<Ciudad, Carretera> g =
				Graphs2.explicitCompleteGraph(
						gf, //grafo
						1000., //peso para las nuevas aristas
						Graphs2::simpleWeightedGraph, //creador del grafo
						() -> Carretera.of(1000.), //creador de las aristas
						Carretera::km); //propiedad para el peso de las aristas
		
		GraphColors.toDot(g, "resultados/ejemplo2/" + file + "A.gv",
				x -> x.nombre(), //me muestre los nombres
				x -> "", //en las aristas no haya nada
				v -> GraphColors.colorIf(Color.blue,
						g.edgesOf(v).stream().anyMatch(e -> ((Carretera)e).km()==1000.)),
				//Si el peso de la arista es igual a 1000: getEdgeWeiht==1000
				e -> GraphColors.colorIf(Color.blue, (g.getEdgeWeight(e)==1000.)));
	}
	
	/*
	 * B) A partir del grafo original, dados dos vertices v1 y v2 de dicho grafo obtener
	 * el camino minimo para ir de v1 a v2. Muestre el grafo original configurando su apariencia
	 * de forma que se restlate el camino minimo para ir de v1 a v2
	 * Mostrar grafo original pero resaltando camino originañ
	 */
	
	public static Ciudad ciudad(Graph<Ciudad, Carretera> graph, String nombre) {
		return graph.vertexSet().stream().filter(c -> c.nombre().equals(nombre))
				.findFirst().get();
	}
	
	public static void apartadoB(SimpleWeightedGraph<Ciudad, Carretera> gf,
			String file, String c1, String c2) {
		//recibe grafo, nombre del fichero resultado, ciudad origen, ciudad destino
		
		var alg = new DijkstraShortestPath<>(gf);
		//algoritmo para calcular el camino minimo y le pasamos nuestro grafo
		Ciudad origen = ciudad(gf, c1); //obtenemos la ciudad de origen como objeto
		Ciudad destino = ciudad(gf, c2); //ciudad destino como objeto
		
		GraphPath<Ciudad, Carretera> gp = alg.getPath(origen, destino);
		
		List<Ciudad> circuito= new HeldKarpTSP<Ciudad, Carretera>().getTour(gf).getVertexList();
		//obtenemos el camino minimo entre esas dos ciudades
		
		GraphColors.toDot(gf, "resultados/emeplo2/" + file + "B.gv",
				x -> x.nombre(), x -> x.nombre(),
				v -> GraphColors.styleIf(Style.bold, gp.getVertexList().contains(v)),
				e -> GraphColors.styleIf(Style.bold, gp.getEdgeList().contains(e)));
		
		System.out.println(file + "B.gv generado en " + "resultados/ejemplo2");
	}
	
	/*
	 * C) Obtener un nuevo grafo dirigido con los mismos vertices y que pod cada arista
	 * original tenga dos dirigidas y de sentido opuesto con los mismos pesos
	 */
	
	public static void apartadoC(SimpleWeightedGraph<Ciudad, Carretera> gf, String file) {
		Graph<Ciudad, Carretera> g =
				Graphs2.toDirectedWeightedGraph(gf, (Carretera x) -> Carretera.of(x.km(), x.nombre()));
				
		GraphColors.toDot(g, "resultados/ejemplo2/" + file + "C.gv",
				x -> x.nombre(),
				x -> x.nombre(),
				v -> GraphColors.color(Color.black),
				e -> GraphColors.style(Style.solid));
		
		System.out.println(file + "C.gv generado en " + "resultados/ejemplo2");
				
	}
	
	/*
	 * D) Calcule las componentes conexas del grafo Original. Muestre el grafo original
	 * configurando su apariencia de forma que se coloree cada componente conexa de un color diferente
	 */
	
	public static void apartadoD(SimpleWeightedGraph<Ciudad, Carretera> gf, String file) {
		
		var alg = new ConnectivityInspector<>(gf);
		List<Set<Ciudad>> ls = alg.connectedSets();
		System.out.println("Hay " + ls.size() + " componentes conexas.");
		
		GraphColors.toDot(gf, "resultados/ejemplo2/" + file + "D.gv",
				x-> x.nombre(),
				x -> x.nombre(),
				v -> GraphColors.color(asignaColor(v, ls, alg)),
				e -> GraphColors.color(asignaColor(gf.getEdgeSource(e), ls, alg)));
		
		System.out.println(file + "D.gv generado en " + "resultados/ejemplo2");
	}

	private static Color asignaColor(Ciudad v, List<Set<Ciudad>> ls,
			ConnectivityInspector<Ciudad, Carretera> alg) {
		Color[] vc = Color.values();
		Set<Ciudad> s = alg.connectedSetOf(v);
		return vc[ls.indexOf(s)];
	}
}
