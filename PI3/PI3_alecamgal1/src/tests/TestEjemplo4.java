package tests;

import org.jgrapht.Graph;

import datos.Pasillo;
import ejemplos.Ejemplo4;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

public class TestEjemplo4 {

	public static void main(String[] args) {
		testEjemplo4("Supermercado1");
		testEjemplo4("Supermercado2");
		testEjemplo4("Supermercado3");
	}
	
	public static void testEjemplo4(String file) {
		Graph<String, Pasillo> grafo = GraphsReader.newGraph("ficheros/" + file + ".txt", 
				v -> v[0], //constructor de vertices
				Pasillo::ofFormat, //constructor de aristas
				Graphs2::simpleWeightedGraph, //tipo de grafo
				Pasillo::mts); //peso
		
		System.out.println("\nArchivo" + file + ".txt \n" + "Datos de entrada: " + grafo);
		
		System.out.println("Apartado A):");
		System.out.println("Las camaras deben colocarse en los siguientes " + Ejemplo4.apartadoA(grafo, file).size()+ "cruces:");
		Ejemplo4.apartadoA(grafo, file).forEach(c -> System.out.println("\t- Cruce " + c));
		
		System.out.println("Apartado B):");
		Ejemplo4.apartadoB(grafo, file);
		
		System.out.println("Apartado C):");
		System.out.println(file + "C.gv generado en " + "resultados/ejemplo4");;
	}
}
