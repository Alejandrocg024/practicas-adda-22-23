package tests;

import org.jgrapht.graph.SimpleWeightedGraph;

import datos.Carretera;
import datos.Ciudad;
import ejemplos.Ejemplo2;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

public class TestEjemplo2 {

	public static void main(String[] args) {
		testEjemplo2("Andalucia", "Sevilla", "Almeria");
	}

	private static void testEjemplo2(String file, String origen, String destino) {
		//Recibe string y mediante graphsreader
		//leemos el grafo, concretamente con el metodo new Graph
		
		SimpleWeightedGraph<Ciudad, Carretera> g = GraphsReader
				.newGraph("ficheros/" + file + ".txt", //fichero de datos
						Ciudad::ofFormat, //factoria para construir vertices
 						Carretera::ofFormat, //factoria aristas
						Graphs2::simpleWeightedGraph, //el tipo de grafo cambia
						Carretera::km); //funcion para el peso de las aristas
	
		System.out.println("Archivo de entrada " + file + ".txt \n" + "Datos de entrada: " + g);
		
		System.out.println("Apartado A):");
		Ejemplo2.apartadoA(g, file);
		
		System.out.println("Apartado B):");
		Ejemplo2.apartadoB(g, file, origen, destino);
		
		System.out.println("Apartado C):");
		Ejemplo2.apartadoC(g, file);
		
		System.out.println("Apartado D):");
		Ejemplo2.apartadoD(g, file);
	
	}
}
