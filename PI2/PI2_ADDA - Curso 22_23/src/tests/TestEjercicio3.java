package tests;

import java.util.List;

import ejercicios.Ejercicio3;
import us.lsi.common.Files2;
import us.lsi.common.Pair;
import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.Tree;

public class TestEjercicio3 {

	public static void main(String[] args) {
		testEjercicio3Binario();
		testEjercicio3Nario();
	}

	private static void testEjercicio3Binario() {
		String file = "ficheros/Ejercicio3DatosEntradaBinario.txt";
		System.out.println("Arboles binarios");

		// Creo una lista que contiene parejas de arboles binarios y caracteres para
		// posteriormente evaluarlos desde el archivo dado
		List<Pair<BinaryTree<Character>, Character>> inputs = Files2.streamFromFile(file) 
				// stream con las lineas del archivo
				.map(linea -> { // transformo las lineas en las parejas
					String[] aux = linea.split("#"); // divido por el #
					return Pair.of(BinaryTree.parse(aux[0], s -> s.charAt(0)), aux[1].charAt(0));
				}).toList();

		// Recorro las parejas para dar solucion a cada una de ellas
		for (Pair<BinaryTree<Character>, Character> par : inputs) {
			BinaryTree<Character> tree = par.first();
			Character charact = par.second();
			System.out.println("Arbol: " + tree + "  Caracter: " + charact + "   ["
					+ Ejercicio3.solucionRecursivaBinario(tree, charact) + "]");
		}
		System.out.println("............................................................................................................................");
	}

	private static void testEjercicio3Nario() {
		String file = "ficheros/Ejercicio3DatosEntradaNario.txt";
		System.out.println("Arboles n-arios");

		// Creo una lista que contiene parejas de arboles n-arios y caracteres para
		// posteriormente evaluarlos desde el archivo dado
		List<Pair<Tree<Character>, Character>> inputs = Files2.streamFromFile(file).map(linea -> { 
			// transformo cada linea en la pareja
			String[] aux = linea.split("#");
			return Pair.of(Tree.parse(aux[0], s -> s.charAt(0)), aux[1].charAt(0));
		}).toList();

		// Recorro las parejas para dar solucion a cada una de ellas
		for (Pair<Tree<Character>, Character> par : inputs) {
			Tree<Character> tree = par.first();
			Character charact = par.second();
			System.out.println("Arbol: " + tree + "  Caracter: " + charact + "   ["
					+ Ejercicio3.solucionRecursivaNario(tree, charact) + "]");
		}
		System.out.println("............................................................................................................................");
	}

}
