package tests;

import java.util.List;
import java.util.Set;

import org.jgrapht.graph.SimpleDirectedGraph;

import datos.Persona;
import ejercicios.Ejercicio1;
import datos.Hijo;
import datos.Parentesco;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

public class TestEjercicio1 {
	
	public static void main(String[] args) {
		String fileA = "PI3E1A_DatosEntrada";
		System.out.println("\nFichero " + fileA 
				+ ".txt\n----------------------------------------------------------------------------------");
		SimpleDirectedGraph<Persona, Hijo> grafoA = leerGrafo(fileA);
		
		System.out.println("\nApartadoA\n----------------------------------------------------------------------------------");
		testApartadoA(grafoA, fileA);
		
		System.out.println("\nApartadoB\n----------------------------------------------------------------------------------");
		testApartadoB(grafoA, fileA, Persona.of(13,"Maria",2008,"Sevilla"));
		
		System.out.println("\nApartadoC\n----------------------------------------------------------------------------------");
		testApartadoC(grafoA, fileA, Persona.of(16,"Rafael",2020,"Malaga"), Persona.of(14,"Sara",2015,"Jaen"));
		testApartadoC(grafoA, fileA, Persona.of(13,"Maria",2008,"Sevilla"), Persona.of(12,"Patricia",2010,"Cordoba"));
		testApartadoC(grafoA, fileA, Persona.of(8,"Carmen",1989,"Jaen"), Persona.of(16,"Rafael",2020,"Malaga"));
		
		System.out.println("\nApartadoD\n----------------------------------------------------------------------------------");
		testApartadoD(grafoA, fileA);
		
		System.out.println("\nApartadoE\n----------------------------------------------------------------------------------");
		testApartadoE(grafoA, fileA);
		
		String fileB = "PI3E1B_DatosEntrada";
		System.out.println("\n----------------------------------------------------------------------------------\nFichero" 
				+ fileB + ".txt\n----------------------------------------------------------------------------------");
		SimpleDirectedGraph<Persona, Hijo> grafoB = leerGrafo(fileB);
		
		System.out.println("\nApartadoA\n----------------------------------------------------------------------------------");
		testApartadoA(grafoB, fileB);
		
		System.out.println("\nApartadoB\n----------------------------------------------------------------------------------");
		testApartadoB(grafoB, fileB, Persona.of(13,"Raquel",1993,"Sevilla"));
		
		System.out.println("\nApartadoC\n----------------------------------------------------------------------------------");
		testApartadoC(grafoB, fileB, Persona.of(14,"Julia",1996,"Jaen"), Persona.of(6,"Angela",1997,"Sevilla"));
		testApartadoC(grafoB, fileB, Persona.of(15,"Alvaro",2000,"Sevilla"), Persona.of(13,"Raquel",1993,"Sevilla"));
		testApartadoC(grafoB, fileB, Persona.of(3,"Laura",1965,"Jerez"), Persona.of(13,"Raquel",1993,"Sevilla"));
		
		System.out.println("\nApartadoD\n----------------------------------------------------------------------------------");
		testApartadoD(grafoB, fileB);
		
		System.out.println("\nApartadoE\n----------------------------------------------------------------------------------");
		testApartadoE(grafoB, fileB);
	}

	private static void testApartadoE(SimpleDirectedGraph<Persona, Hijo> g, String file) {
		Set<Persona> res = Ejercicio1.apartadoE(g, file);
		System.out.println("Conjunto minimo de personas para cubrir todas las relaciones:  " + res);
	}

	private static void testApartadoD(SimpleDirectedGraph<Persona, Hijo> g, String file) {
		Set<Persona> res = Ejercicio1.apartadoD(g, file);
		System.out.println("Personas que tienen hijos/as con distintas personas " + res);
	}

	private static void testApartadoC(SimpleDirectedGraph<Persona, Hijo> g, String file, Persona persona1, Persona persona2) {
		Parentesco res = Ejercicio1.apartadoC(g, file, persona1, persona2);
		System.out.println(persona1.nombre() + " y "  + persona2.nombre() + " son " + res);
	}
	
	private static void testApartadoB(SimpleDirectedGraph<Persona, Hijo> g, String file, Persona persona) {
		List<Persona> ancestros = Ejercicio1.apartadoB(g, file, persona);
		System.out.println("Ancestros de " + persona.nombre() + " : " + ancestros);
	}

	private static void testApartadoA(SimpleDirectedGraph<Persona, Hijo> grafo, String file) {
		Set<Persona> res = Ejercicio1.apartadoA(grafo, file);
		System.out.println("Personas cuyos padres aparecen en el grafo y cumplen los requisitos:  " + res);
	}

	private static SimpleDirectedGraph<Persona, Hijo> leerGrafo(String file) {
		SimpleDirectedGraph<Persona, Hijo> g = GraphsReader.newGraph( //Grafo dirigido no ponderado
				"ficheros/" + file + ".txt", 
				Persona::ofFormat, //factoria para construir vertices de tipo Persona
				Hijo::ofFormat, //factoria aristas de tipo Hjo
				Graphs2::simpleDirectedGraph);
		
		GraphColors.toDot(g,  //grafo
				"resultados/ejercicio1/" + file + ".gv",
				p ->  p.nombre() + "\n" + p.ciudadNacimiento() + " " + p.anyoNacimiento(), //etiqueta vertices
				a -> "", //etiqueta aristas
				v -> GraphColors.color(Color.black), //color vertices
				e -> GraphColors.color(Color.black)); //color aristas
		
		System.out.println("Usando los datos de entrada: " + file + ".txt -> Grafo " + file 
					+ "_ApartadoA.gv generado en " + "resultados/ejercicio1");
		
		return g;
	}
}
