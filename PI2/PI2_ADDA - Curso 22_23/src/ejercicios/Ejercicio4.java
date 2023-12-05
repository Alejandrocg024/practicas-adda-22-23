package ejercicios;

import java.util.List;

import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.BinaryTree.BEmpty;
import us.lsi.tiposrecursivos.BinaryTree.BLeaf;
import us.lsi.tiposrecursivos.BinaryTree.BTree;
import us.lsi.tiposrecursivos.Tree;
import us.lsi.tiposrecursivos.Tree.TEmpty;
import us.lsi.tiposrecursivos.Tree.TLeaf;
import us.lsi.tiposrecursivos.Tree.TNary;

public class Ejercicio4 {
	/*
	 * 4. Dado un árbol binario de cadena de caracteres, diseñe un algoritmo que devuelva cierto si se cumple que, 
	 * para todo nodo, el número total de vocales contenidas en el subárbol izquierdo es igual al del 
	 * subárbol derecho. Proporcione una solución también para árboles n-arios
	 */
	
	public static Boolean solucionRecursivaBinario(BinaryTree<String> tree) {
		return recursivoBinario(tree, true); //inicializo con el resultado a true para que vaya calculando
	}
	
	//Llamada recursiva que tiene como parametros el tree y
	//un boolean resultado que es el que voy a pasar en cada llamada para que evalue
	private static Boolean recursivoBinario(BinaryTree<String> tree, Boolean res) {
		// Switch con expresiones que tendran tres casos nodo vacio, nodo hoja y nodo arbol
		return switch(tree) {
		
		// Caso vacio que devuelve el mismo boolean que se da como parametro
		case BEmpty<String> t -> res;
		
		// Caso hoja que devuelve el mismo boolean que se da como parametro porque no hay hijos
		case BLeaf<String> t -> res;
		
		// Caso arbol, Devuelvo el resultado and de las siguientes operaciones:
		//	-res, dado en la llamada recursiva anterior
		// 	-llamada recursiva al nodo hijo izquierdo
		//	-llamada recursiva al nodo hijo derecho
		//	-si las vocales del nodo hijo izquierdo y las del nodo hijo derecho son las mismas
		case BTree<String> t -> {
			yield res && 
			recursivoBinario(t.left(), res) && 
			recursivoBinario(t.right(), res) &&
			(cuentaVocalesBinario(t.left()) == cuentaVocalesBinario(t.right()));
		}
		
		};
	}

	private static Integer cuentaVocalesBinario(BinaryTree<String> tree) {
		List<String> vocales = List.of("a","e","i","o","u");
		return cuentaVocalesBinarioRecursivo(tree, vocales, 0); //inicializo con las vocales y el contador a 0
	}
	private static Integer cuentaVocalesBinarioRecursivo(BinaryTree<String> tree, List<String> vocales, 
			Integer res) {
		// Switch con expresiones que tendran tres casos nodo vacio, nodo hoja y nodo arbol
		return switch(tree) {
		
		// Caso vacio que devuelve el mismo contador que se da como parametro
		case BEmpty<String> t -> res;
		
		// Caso hoja que recorre el string del nodo letra por letra y cuenta las vocales del mismo
		case BLeaf<String> t -> {
			String label=t.label();
			for(int i=0; i<label.length();i++) {
				if(vocales.contains(String.valueOf(label.charAt(i)))) {
					res++;
				}
			}
			yield res;
		}
		
		// Caso hoja que recorre el string del nodo letra por letra y cuenta las vocales del mismo
		// devuelve las vocales del nodo + llamada recursiva para contar las vocales de los nodos hijos
		case BTree<String> t -> {
			String label=t.label();
			for(int i=0; i<label.length();i++) {
				if(vocales.contains(String.valueOf(label.charAt(i)))) {
					res++;
				}
			}
			yield res + cuentaVocalesBinarioRecursivo(t.left(), vocales, res) + 
			cuentaVocalesBinarioRecursivo(t.right(), vocales, res);
		}
		};
	}

	public static Boolean solucionRecursivaNario(Tree<String> tree) {
		return recursivoNario(tree, true); //inicializo con el resultado a true para que vaya calculando
	}
	
	//Llamada recursiva que tiene como parametros el tree y
	//un boolean resultado que es el que voy a pasar en cada llamada para que evalue
	private static Boolean recursivoNario(Tree<String> tree, Boolean res) {
		return switch(tree) {
		
		// Caso vacio que devuelve el mismo boolean que se da como parametro
		case TEmpty<String> t -> res;
		
		// Caso hoja que devuelve el mismo boolean que se da como parametro porque no hay hijos
		case TLeaf<String> t -> res;
		
		// Caso arbol, Devuelvo el resultado and de las siguientes operaciones:
				//	-res, dado en la llamada recursiva anterior
				// 	-si las vocales que tiene el primer hijo son las mismas que las 
				// del hijo recorrido en el for(al que le hago la llamada recursiva para contarlas)
		case TNary<String> t -> {
			List<Tree<String>> hijos = t.elements();
			Integer vocalesPrimerHijo = cuentaVocalesNario(hijos.get(0)); //cantidad de vocales del primer hijo
			for (int i=1;i<hijos.size();i++) {
				//evaluo si tiene las mismas vocales el primer hijo que los demas
				res = res && (vocalesPrimerHijo==cuentaVocalesNario(hijos.get(i))); 
			}
			yield res;
		}
		
		};
	}
	
	private static Integer cuentaVocalesNario(Tree<String> tree) {
		List<String> vocales = List.of("a","e","i","o","u");
		return cuentaVocalesNarioRecursivo(tree, vocales, 0); //inicializo con las vocales y el contador a 0
	}
	private static Integer cuentaVocalesNarioRecursivo(Tree<String> tree, List<String> vocales, 
			Integer res) {
		// Switch con expresiones que tendran tres casos nodo vacio, nodo hoja y nodo arbol
		return switch(tree) {
		// Caso vacio que devuelve el mismo contador que se da como parametro
		case TEmpty<String> t -> res;
		
		// Caso hoja que recorre el string del nodo letra por letra y cuenta las vocales del mismo
		case TLeaf<String> t -> {
			String label=t.label();
			for(int i=0; i<label.length();i++) {
				if(vocales.contains(String.valueOf(label.charAt(i)))) {
					res++;
				}
			}
			yield res;
		}
		
		// Caso hoja que recorre el string del nodo letra por letra y cuenta las vocales del mismo
		// devuelve las vocales del nodo + recorre los nodos hijos y realiza llamadas recursivas para
		// contar las vocales de los mismos y las va sumando
		case TNary<String> t -> {
			String label=t.label();
			for(int i=0; i<label.length();i++) { 
				if(vocales.contains(String.valueOf(label.charAt(i)))) {
					res++;
				}
			}
			for (Tree<String> child: t.elements()) {
				res += cuentaVocalesNarioRecursivo(child, vocales, res);
			}
			yield res;
		}
		
		};
	}
}