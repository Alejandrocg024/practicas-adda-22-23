package tests;

import java.util.List;

import ejercicios.Ejercicio4;
import us.lsi.common.Files2;
import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.Tree;

public class TestEjercicio4 {

	public static void main(String[] args) {
		testEjercicio4Binario();
		testEjercicio4Nario();
	}
	
	private static void testEjercicio4Binario() {
		String file = "ficheros/Ejercicio4DatosEntradaBinario.txt";
		System.out.println("Arboles binarios");
		
		// Creo una lista que contiene los arboles binarios para posteriormente evaluarlos
		List<BinaryTree<String>> inputs = Files2.streamFromFile(file) //stream con el archivo
				.map(linea -> BinaryTree.parse(linea)) //convierto cada linea en un arbol binario
				.toList();
		
		//Recorro los arboles y muestro la solucion de cada uno
		for(BinaryTree<String> tree : inputs) {
			System.out.println(tree + ": " + Ejercicio4.solucionRecursivaBinario(tree));
		}
		System.out.println("............................................................................................................................");
	}
	
	private static void testEjercicio4Nario() {
		String file = "ficheros/Ejercicio4DatosEntradaNario.txt";
		System.out.println("Arboles N-arios");
		
		// Creo una lista que contiene los arboles n-arios para posteriormente evaluarlos
		List<Tree<String>> inputs = Files2.streamFromFile(file)
				.map(linea -> Tree.parse(linea)) //convierto cada linea en un arbol n-ario
				.toList();
		
		//Recorro los arboles y muestro la solucion de cada uno
		for(Tree<String> tree : inputs) {
			System.out.println(tree + ": " + Ejercicio4.solucionRecursivaNario(tree));
		}
		System.out.println("............................................................................................................................");
	}

}
