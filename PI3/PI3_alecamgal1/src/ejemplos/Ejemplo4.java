 package ejemplos;

import java.util.Set;
import java.util.function.Predicate;

import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.interfaces.SpanningTreeAlgorithm.SpanningTree;
import org.jgrapht.alg.spanning.KruskalMinimumSpanningTree;
import org.jgrapht.alg.vertexcover.GreedyVCImpl;

import datos.Pasillo;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.views.SubGraphView;

public class Ejemplo4 {
	
	/*4. Se desean ubicar cámaras de seguridad en un supermercado de forma que todos los
	 * 	pasillos estén vigilados. Se podrá poner una cámara en cada uno de los cruces entre
	 * 	pasillos. Una cámara situada en un cruce puede vigilar todos los pasillos adyacentes.
	 * 
	 * 		A). Determine cuántas cámaras poner y dónde ponerlas de forma que se minimice 
	 * 		el coste total (es decir, el número de cámaras).
	 * 
	 * 		B. Una vez determinado dónde ubicar las cámaras, se desea realizar la instalación eléctrica para darles soporte. 
	 * 		Para ello, se instalarán equipos de soporte/gestión en algunas cámaras, de forma que cada equipo podrá dar
	 * 		soporte a la cámara donde esté instalado y a aquellas cámaras conectadas con ella a través de pasillos cableados. 
	 * 		Sólo se podrán cablear pasillos que tengan cámaras a ambos extremos. 
	 * 		¿Cuántos equipos son necesarios? ¿Cuántos metros de cable son necesarios?
	 * 
	 * 		C. Muestre el grafo que representa el problema configurando su apariencia de
	 * 		forma que se resalten los cruces en los que hay cámara y los pasillos cableados.
	 */

	public static Set<String> apartadoA(Graph<String, Pasillo> gf, String file) {
		//gredyVCimpl: Algoritmo para encontrar una cobertura mínima de vertices que cubren todas las aristas, en nuestro caso cada vertice seria un punto para poner en camara
		
		var algA = new GreedyVCImpl<>(gf);
		Set<String> cruces = algA.getVertexCover();
		//Obtenemos el nombre de los vertices involucrados, es decir los puntos donde habria que ponerlas para minimizar los
		return cruces;
	}

	public static void apartadoB(Graph<String, Pasillo> gf, String file) {
		Set<String> cruces = apartadoA(gf, file);
		Predicate<Pasillo> pe = p -> cruces.contains(gf.getEdgeSource(p)) && cruces.contains(gf.getEdgeTarget(p));
		//pasillos con camaras en ambos extremos
		Graph<String, Pasillo> sgf = SubGraphView.of(gf, e -> true, pe); //grafo con camaras en ambos extremos
		
		var algB1 = new ConnectivityInspector<>(sgf); //n de componentes conexas
		System.out.println("Numero de equipos necesarios: " + algB1.connectedSets().size());
		
		//El algoritmo de Kruskal busca un bosque de recubrimiento minimo de un gradfo y devuelve el conjunto de aristas del recubrimiento
		//Es aplicable para grafos no conexos o conexos (en ese caso devuelve un arbol de recubrimiento minimo
		
		var algB2 = new KruskalMinimumSpanningTree<>(sgf); //requiere como entrada el grafo sobre el que se quiere aplicar
		var tree = algB2.getSpanningTree();
		System.out.println(String.format("Metros de cable necesarios: %.1f", tree.getWeight()));
		//la suma total de los pesos será igual a los metros de cables necesarios
		
		//Apartado c)
		GraphColors.toDot(gf, "resultados/ejemplo4/" + file + ".gv", c ->"", v->"",
				v -> GraphColors.colorIf(Color.blue, Color.blank, cruces.contains(v)),
				e -> GraphColors.colorIf(Color.blue, Color.blank, tree.getEdges().contains(e)));
	}

}
