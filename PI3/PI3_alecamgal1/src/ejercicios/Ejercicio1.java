package ejercicios;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.vertexcover.GreedyVCImpl;

import datos.Hijo;
import datos.Parentesco;
import datos.Persona;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.views.SubGraphView;

public class Ejercicio1 {
		
	/*
	 * 1. Se tiene un grafo dirigido no ponderado que representa relaciones familiares entre
	 * 	distintas personas. En concreto, en dicho grafo hay una arista de a hacia b si b es hijo/a
	 * 	de a. De cada persona se conoce un identificador numérico único, su nombre, año y
	 * 	ciudad de nacimiento.
	 * 		A). Obtenga una vista del grafo que sólo incluya las personas cuyos padres aparecen
	 * 		en el grafo, y ambos han nacido en la misma ciudad y en el mismo año. Muestre
	 * 		el grafo configurando su apariencia de forma que se resalten los vértices y las aristas de la vista.
	 * */
	public static Set<Persona> apartadoA(Graph<Persona, Hijo> g, String file) {
		
		Predicate<Persona> pv = v -> { //Predicado que devolvera true si los padres han nacido en la misma cidad y el mismo año
			List<Persona> padres= Graphs.predecessorListOf(g, v); //Con predecessorListOf obtengo padres de una Persona
			return padres.size() == 2 && 
					padres.get(0).anyoNacimiento().equals(padres.get(1).anyoNacimiento()) && 
					padres.get(0).ciudadNacimiento().equalsIgnoreCase(padres.get(1).ciudadNacimiento());
		};

		Graph<Persona, Hijo> vista = SubGraphView.of(g,  pv, e -> true); //Vista del grafo original aplicando el predicado

				
		//toDot en el que guardaremos el grafo original pero con los vertices y aristas de la solucion modificados
		GraphColors.toDot(g, "resultados/ejercicio1/" + file + "_ApartadoA.gv", //ruta donde guardamos el resultados
			v -> v.nombre(), //nos indica el nombre de los vertices
			a -> "", //nos indica el nombre de las aristas
			v -> GraphColors.colorIf(Color.red, vista.containsVertex(v)), //Se colorean los vertices que estén en a vista
			a -> GraphColors.colorIf(Color.red, vista.containsEdge(a))); //Se colorean las aristas pertenecientes a la vista
				
			System.out.println("Usando los datos de entrada: " + file + ".txt -> Grafo " + file 
					+ "_ApartadoA.gv generado en " + "resultados/ejercicio1");
			
			Set<Persona> res = vista.vertexSet(); //Devuelvo los padres que cumplen el predicado
			return res;
	}
	
	/*
	 *		B). Implemente un algoritmo que dada una persona devuelva un conjunto con todos
	 * 		sus ancestros que aparecen en el grafo. Muestre el grafo configurando su
	 * 		apariencia de forma que se resalte la persona de un color y sus ancestros de otro.
	 */
	public static List<Persona> apartadoB(Graph<Persona, Hijo> g, String file, Persona persona) {
		Set<Persona> personas= g.vertexSet(); //Obtengo todas las personas del grafo
		List<Persona> ancestros = new ArrayList<Persona>(); //Lista en la que almacenare los resultados
		var alg = new DijkstraShortestPath<>(g);
		for (Persona p: personas) { //Recorro todas las personas del grafo
			GraphPath<Persona, Hijo> gp = alg.getPath(p, persona); //Camino entre las dos personas
			if (gp != null && !p.equals(persona)) { //Si no hay camino será null, esas dos personas no están conectadas
				ancestros.add(gp.getStartVertex());
			}
		}
		
		
		//toDot en el que guardaremos el grafo original pero con los vertices y aristas de la solucion modificados
		GraphColors.toDot(g, "resultados/ejercicio1/" + file + "_ApartadoB.gv", //ruta donde guardamos el resultados
			v -> v.nombre(), //nos indica el nombre de los vertices
			a -> "", //nos indica el nombre de las aristas
			//El color If de los vertices tiene dos condiciones.
				//Si el vertice es la persona que damos en el enunciado lo colorea rojo
				//Si el vertice es alguno de sus ancestros, lo coloreamos de azul
			v -> GraphColors.colorIf(List.of(v.equals(persona), ancestros.contains(v)), List.of(Color.red, Color.blue)),
			a -> GraphColors.color(Color.black));
				
		System.out.println("Usando los datos de entrada: " + file + ".txt -> Grafo " + file 
				+ "_ApartadoB.gv generado en " + "resultados/ejercicio1");
		return ancestros;
	}
	
	/*
	 * 		C). Implemente un algoritmo que dadas dos personas devuelva un valor entre los
	 * 		posibles del enumerado {Hermanos, Primos, Otros} en función de si son
	 * 		hermanos, primos hermanos, o ninguna de las dos cosas. Tenga en cuenta que 2
	 * 		personas son hermanas en caso de que tengan al padre o a la madre en común, y
	 * 		primas en caso de tener al menos un abuelo/a en común.
	 */
	public static Parentesco apartadoC(Graph<Persona, Hijo> g, String file, Persona persona1, Persona persona2){
		//Mediante predecessorListOf obtengo todos los padres y abuelos de las personas dadas en el enunciado
		List<Persona> padresPersona1 = Graphs.predecessorListOf(g, persona1);
		List<Persona> padresPersona2 = Graphs.predecessorListOf(g, persona2);
		List<Persona> abuelosPersona1 = new ArrayList<Persona>();
		padresPersona1.forEach(padre -> abuelosPersona1.addAll(Graphs.predecessorListOf(g, padre)));
		List<Persona> abuelosPersona2 = new ArrayList<Persona>();
		padresPersona2.forEach(padre -> abuelosPersona2.addAll(Graphs.predecessorListOf(g, padre)));
		
		Parentesco res = Parentesco.OTROS; //Por defecto, será otros si no es hermano o primo
		for(Persona padre : padresPersona1) {
			if(padresPersona2.contains(padre)) { //Si coinciden alguno de sus padres
				return Parentesco.HERMANOS;
			} else {
				for (Persona abuelo : abuelosPersona1) {
					if (abuelosPersona2.contains(abuelo)){ //Si coinciden alguno de sus abuelos
						return Parentesco.PRIMOS;
					}
				}	
			}
		}
		return res;
	}
	/*
	 * 		D). Implemente un algoritmo que devuelva un conjunto con todas las personas que
	 * 		tienen hijos/as con distintas personas. Muestre el grafo configurando su apariencia
	 * 		de forma que se resalten las personas de dicho conjunto.
	 */
	public static Set<Persona> apartadoD(Graph<Persona, Hijo> g, String file){
		Set<Persona> res = new HashSet<Persona>();
		Set<Persona> personas= g.vertexSet();
		for (Persona p : personas) { //Recorro todas las personas
			List<Persona> hijos = Graphs.successorListOf(g, p); //Obtengo los hijos de una persona
			if (hijos.size() > 1) { //Si tiene mas de un hijo la persona, si no puede tener hijos con distintas personas
				List<Persona> predecesoresHijo1 = Graphs.predecessorListOf(g, hijos.get(0)); 
				for (Persona hijo: hijos) {
					List<Persona> predecesoresHijo2 = Graphs.predecessorListOf(g, hijo);
					if (!predecesoresHijo1.equals(predecesoresHijo2)) { //Comparo los padres de un hijo y del otro y 
						res.add(p); //si no son los mismos, son de distintos padres y los añado al resultado
					}
				}
			}
		}
		
		//toDot en el que guardaremos el grafo original pero con los vertices y aristas de la solucion modificados
		GraphColors.toDot(g, "resultados/ejercicio1/" + file + "_ApartadoD.gv", //ruta donde guardamos el resultados
			v -> v.nombre(), //nos indica el nombre de los vertices
			a -> "", //nos indica el nombre de las aristas
			v -> GraphColors.colorIf(Color.blue, res.contains(v)), //Coloreo el vertice si está en la solucion
			a -> GraphColors.color(Color.black));
				
		System.out.println("Usando los datos de entrada: " + file + ".txt -> Grafo " + file 
				+ "_ApartadoD.gv generado en " + "resultados/ejercicio1");
		
		return res;
	}
	
	/*
	 * 		E). Se desea seleccionar el conjunto mínimo de personas para que se cubran todas
	 * 		las relaciones existentes. Implemente un método que devuelva dicho conjunto.
	 * 		Muestre el grafo configurando su apariencia de forma que se resalten las personas de dicho conjunto.
	 */
	public static Set<Persona> apartadoE(Graph<Persona, Hijo> g, String file) {
		Graph<Persona, Hijo> g2 = Graphs.undirectedGraph(g); //Metodo necesario para la cobertura del grafo
		var algA = new GreedyVCImpl<>(g2); 	//Encontrar una cobertura mínima de vertices que cubren todas las aristas
		Set<Persona> res = algA.getVertexCover(); //Vertices necesarios para la cobertura minima del grafo

		//toDot en el que guardaremos el grafo original pero con los vertices y aristas de la solucion modificados
		GraphColors.toDot(g, "resultados/ejercicio1/" + file + "_ApartadoE.gv", //ruta donde guardamos el resultados
			v -> v.nombre(), //nos indica el nombre de los vertices
			a -> "", //nos indica el nombre de las aristas
			v -> GraphColors.colorIf(Color.blue, res.contains(v)), //Coloreo si el vertice esta en la solucion
			a -> GraphColors.color(Color.black));
					
		System.out.println("Usando los datos de entrada: " + file + ".txt -> Grafo " + file 
				+ "_ApartadoE.gv generado en " + "resultados/ejercicio1");
		
		return res;
	}
}
