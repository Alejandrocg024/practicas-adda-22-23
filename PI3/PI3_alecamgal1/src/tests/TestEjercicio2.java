package tests;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.graph.SimpleWeightedGraph;

import datos.Ciudades;
import datos.Trayecto;
import ejercicios.Ejercicio2;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

public class TestEjercicio2 {

	public static void main(String[] args) {
		String fileA = "PI3E2_DatosEntrada";
		System.out.println("\nFichero " + fileA 
				+ ".txt\n----------------------------------------------------------------------------------");
		SimpleWeightedGraph<Ciudades, Trayecto> grafoA = leerGrafoPesoPrecio(fileA); //Grafo en el qe el peso es el precio
		SimpleWeightedGraph<Ciudades, Trayecto> grafoB = leerGrafoPesoTiempo(fileA); //Grafo en el que el peso es el tiempo
		
		System.out.println("\nApartadoA\n----------------------------------------------------------------------------------");
		testApartadoA(grafoA, fileA);
		
		System.out.println("\nApartadoB\n----------------------------------------------------------------------------------");
		testApartadoB(grafoA, fileA);
		
		System.out.println("\nApartadoC\n----------------------------------------------------------------------------------");
		testApartadoC(grafoA, fileA);
		
		System.out.println("\nApartadoD\n----------------------------------------------------------------------------------");
		testApartadoD(grafoB, fileA);
	}
	
	private static void testApartadoD(SimpleWeightedGraph<Ciudades, Trayecto> grafo, String file) {
		Map<List<Ciudades>, Double> res = Ejercicio2.apartadoD(grafo, file);
		System.out.println("Las ciudades no conectadas directamente entre las que se puede viajar en menor tiempo son: ");
		res.forEach((x, y) -> System.out.println(x.get(0) + " y  " + x.get(1) + " --> Tiempo: " + y + " minutos"));
	}

	private static void testApartadoC(SimpleWeightedGraph<Ciudades, Trayecto> grafo, String file) {
		Map<List<Ciudades>, Double> res = Ejercicio2.apartadoC(grafo, file);
		res.forEach((x, y) -> System.out.println("Grupo de ciudades a visitar que dan lugar al camino cerrado de menor precio: " 
				+ x + " --> " + y + " euros"));
	}
	
	private static void testApartadoB(SimpleWeightedGraph<Ciudades, Trayecto> grafo, String file) {
		Set<Ciudades> res = Ejercicio2.apartadoB(grafo, file);
		System.out.println("Grupo de ciudades que maximiza la suma de puntuaciones: " + res);
	}
	
	private static void testApartadoA(SimpleWeightedGraph<Ciudades, Trayecto> grafo, String file) {
		List<Set<Ciudades>> res = Ejercicio2.apartadoA(grafo, file);
		System.out.println("Hay " + res.size() + " grupos de ciudades");
		for (int i = 0; i < res.size(); i++) {
			System.out.println("Grupo numero " + i + ": " + res.get(i));
		}
	}

	private static SimpleWeightedGraph<Ciudades, Trayecto> leerGrafoPesoPrecio(String file) {
		SimpleWeightedGraph<Ciudades, Trayecto> g = GraphsReader.newGraph( //Grafo ponderado
				"ficheros/" + file + ".txt", 
				Ciudades::ofFormat, //factoria para construir vertices de tipo Ciudad
				Trayecto::ofFormat, //factoria aristas de tipo Trayecto
				Graphs2::simpleWeightedGraph,
				Trayecto::precio); //peso
		
		GraphColors.toDot(g,  //grafo
				"resultados/ejercicio2/" + file + ".gv",
				p -> p.toString() + "\n" + String.valueOf(p.puntuacion()) + " puntos", //etiqueta vertices
				a -> a.toString(), //etiqueta aristas
				v -> GraphColors.color(Color.black), //color vertices
				e -> GraphColors.color(Color.black)); //color aristas
		
		System.out.println("Usando los datos de entrada: " + file + ".txt -> Grafo " 
				+ file + "_ApartadoA.gv generado en " + "resultados/ejercicio2");
		
		return g;
	}
	
	private static SimpleWeightedGraph<Ciudades, Trayecto> leerGrafoPesoTiempo(String file) {
		SimpleWeightedGraph<Ciudades, Trayecto> g = GraphsReader.newGraph( //Grafo ponderado
				"ficheros/" + file + ".txt", 
				Ciudades::ofFormat, //factoria para construir vertices de tipo Ciudad
				Trayecto::ofFormat, //factoria aristas de tipo Trayecto
				Graphs2::simpleWeightedGraph,
				Trayecto::tiempo); //peso

		return g;
	}

}
