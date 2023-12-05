package tests;

import java.util.function.Predicate;

import org.jgrapht.Graph;

import datos.Carretera;
import datos.Ciudad;
import ejemplos.Ejemplo1;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

public class TestEjemplo1 {

	public static void main(String[] args) {
		testEjemplo1("Andalucia");
		testEjemplo1("Castilla La Mancha");
	}

	private static void testEjemplo1(String file) {
		//Recibe string y mediante graphsreader
		//leemos el grafo, concretamente con el metodo new Graph
		
		Graph<Ciudad, Carretera> g = GraphsReader
				.newGraph("ficheros/" + file + ".txt", //fichero de datos
						Ciudad::ofFormat, //factoria para construir vertices
 						Carretera::ofFormat, //factoria aristas
						Graphs2::simpleGraph); //creador del grafo
		
		/*
		 * para poder ver el grafo utilizamos el metodo toDot que nos genera
		 * el grado en formato gv y lo podemos ver con una herramienta llamada graphviz online.
		 * g: graf
		 * label vertices: v->v.nombre()
		 * label aristas: e -> e.nombre()
		 * estilo vertices
		 * estilo aristas
		 */
		
		//2. directorio donde lo vamos a guardar y nombre del archvo
		
		GraphColors.toDot(g, "resultados/ejemplo1/" + file + ".gv",
				v -> v.nombre(), //que etiqueta mostrar en vertices y aristas
				e -> e.nombre(),
				v -> GraphColors.color(Color.black), //color o estilo de vertices y aristas
				e -> GraphColors.color(Color.black));
		System.out.println("\nArchivo " + file + ".txt \n" + "Datos de entrada: " + g);
		
		// a) PrimerPredicado: Ciudades cuyo nombre contiene la letra "e", y carreteras con menos de 200km de distancia
		Predicate<Ciudad> pv1 = c -> c.nombre().contains("e"); //ciudades que contienen e
		Predicate<Carretera> pa1 = ca -> ca.km() < 200;
		
		Ejemplo1.crearVista(file, g, pv1, pa1, " Primer predicado"); //llamamos a ejemplo1
		
		/*
		 * b) SegundoPredicado: Ciudades que poseen menos de 500.000 habitantes y carreteras cuya
		 * ciudad origen o destino tiene un nombre de mas de 5 caracteres y poseen mas de 100km de distantica
		 */
		
		Predicate<Ciudad> pv2 = c -> c.habitantes() < 500000;
		Predicate<Carretera> pa2 = ca -> ca.km() > 100 && (g.getEdgeSource(ca).nombre().length() > 5 ||
				 g.getEdgeTarget(ca).nombre().length() > 5);
		
		//g. getEdgeTarget(ca) -> coudad destino
		// get EdgeSource(ca) -> ciudad origen
		
		Ejemplo1.crearVista(file, g, pv2, pa2, " Segundo predicado");
				
 	}

}
