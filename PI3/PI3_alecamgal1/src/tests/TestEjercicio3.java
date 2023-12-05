package tests;

import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import ejercicios.Ejercicio3;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.common.Files2;
import us.lsi.graphs.Graphs2;

public class TestEjercicio3 {

	public static void main(String[] args) {
		String fileA = "PI3E3A_DatosEntrada";
		System.out.println("\nFichero " + fileA + ".txt"
				+ "\n----------------------------------------------------------------------------------");
		Graph<String, DefaultEdge> grafoA = leerGrafo(fileA);
		
		System.out.println("\nApartadoA\n----------------------------------------------------------------------------------");
		testApartadoA(grafoA, fileA);
		
		System.out.println("\nApartadoB\n----------------------------------------------------------------------------------");
		Ejercicio3.apartadoB(grafoA, fileA);

		String fileB = "PI3E3B_DatosEntrada";
		System.out.println("\n----------------------------------------------------------------------------------\nFichero" 
				+ fileB + ".txt\n----------------------------------------------------------------------------------");
		Graph<String, DefaultEdge> grafoB = leerGrafo(fileB);
		
		System.out.println("\nApartadoA\n----------------------------------------------------------------------------------");
		testApartadoA(grafoB, fileB);
		
		System.out.println("\nApartadoB\n----------------------------------------------------------------------------------");
		Ejercicio3.apartadoB(grafoB, fileB);
	}

	private static void testApartadoA(Graph<String, DefaultEdge> grafo, String file) {
		List<Set<String>> res = Ejercicio3.apartadoA(grafo, file);
		System.out.println("Numero de franjas horarias necesarias: " + res.size() 
			+ "\nActividades para impartirse en paralelo por franja horaria:");
		for (int i = 0; i < res.size(); i++) {
			System.out.println("Franja numero " + i + ": " + res.get(i));
		}
	}

	private static SimpleGraph<String, DefaultEdge> leerGrafo(String file) {
		//primero nos definimos un grafo sin tipos concretos para las aristas y los vertices
		SimpleGraph<String, DefaultEdge> g = 
				Graphs2.simpleGraph(String::new, //metodo factoria para crear vertices
						DefaultEdge::new, //metodo factoria para crear aristas
						false); //no necesitamos pesos
		
		Files2.streamFromFile("ficheros/" + file + ".txt").forEach(linea -> {
			//Alumno1: Actividad1, Actividad2, Actividad3
			String lineaSinEspacio = linea.replaceAll(" ", "");
			String[] s1 = lineaSinEspacio.split(":");
			String[] s2 = s1[1].split(",");
			
			for(String s : s2) { //Añadir vertices que serán las actividades
				if(!g.vertexSet().contains(s)) {
					g.addVertex(s);
				}
			}
			
			//Añadir aristas que serán las actividades que comparten alumno, ya que no se podrán impartir a la misma hora
			for (int i = 0; i < s2.length - 1; i++) { 
				for (int j = i+1; j < s2.length; j++) {
					g.addEdge(s2[i], s2[j]);
				}
			}
		});
		
		//Mostrar grafo
		GraphColors.toDot(g,
				"resultados/ejercicio3/" + file + ".gv",
				p -> p.toString(), //etiqueta vertices
				a -> "", //etiqueta aristas
				v -> GraphColors.color(Color.black),
				e -> GraphColors.color(Color.black));
		
		System.out.println("Usando los datos de entrada: " + file + ".txt -> Grafo " + file + ".gv generado en " 
				+ "resultados/ejercicio3");
		
		return g;
		
	}

}
