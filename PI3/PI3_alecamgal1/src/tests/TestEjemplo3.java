package tests;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import ejemplos.Ejemplo3;
import us.lsi.common.Files2;
import us.lsi.graphs.Graphs2;

public class TestEjemplo3 {

	public static void main(String[] args) {
		testsEjemplo3("Comensales");

	}

	private static void testsEjemplo3(String file) {
		//SEGUIMOS UNA ESTRATEGIA DIStinta para generar el grafo:
		
		//primero nos definimos un grafo sin tipos concretos para las aristas y los vertices
		Graph<String, DefaultEdge> g =
				Graphs2.simpleGraph(String::new, //metodo factoria para crear vertices
									DefaultEdge::new, //metodo factoria para crear aristas
									false); //como no necesitamos pesos ponemos eso a false
		
		Files2.streamFromFile("ficheros/" + file + ".txt").forEach(linea -> {
			//CADA VERTIe representa un comensale
			//cada arista representa la incompatibilidad entre dos personas
			String[] v = linea.split(",");
			g.addVertex(v[0]);
			g.addVertex(v[1]);
			g.addEdge(v[0], v[1]); //añadimos la arista
		});
		
		Ejemplo3.apartados(g, file);
		
	}

}
