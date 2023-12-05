package ejercicios;

import java.util.ArrayList;
import java.util.List;

import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.BinaryTree.BEmpty;
import us.lsi.tiposrecursivos.BinaryTree.BLeaf;
import us.lsi.tiposrecursivos.BinaryTree.BTree;
import us.lsi.tiposrecursivos.Tree;
import us.lsi.tiposrecursivos.Tree.TEmpty;
import us.lsi.tiposrecursivos.Tree.TLeaf;
import us.lsi.tiposrecursivos.Tree.TNary;

public class Ejercicio3 {
	/*
	 * 3. Dados un árbol binario de caracteres y un carácter, diseñe un algoritmo
	 * que devuelva una lista con todas las cadenas que se forman desde la raíz a
	 * una hoja no vacía, excluyendo aquellas cadenas que contengan dicho carácter.
	 * Proporcione una solución también para árboles n-arios.
	 */
	public static List<String> solucionRecursivaBinario(BinaryTree<Character> tree, Character charact) {
		return recursivaBinario(tree, charact, "", new ArrayList<String>()); // Inicializo el ac y el res vacios
	}

	// Llamada recursiva que tiene como parametros el tree y charact que nos da el
	// enunciado y
	// un ac que es donde voy a ir guardando las cadenas en cada llamada para seguir
	// formando la cadena hasta llegar a una hoja
	// una lista resultado que es donde voy a ir guardando cada cadena cuando llegue
	// a una hoja no vacia
	private static List<String> recursivaBinario(BinaryTree<Character> tree, Character charact, String ac,
			List<String> res) {
		// Switch con expresiones que tendran tres casos nodo vacio, nodo hoja y nodo arbol
		return switch (tree) {

		// Caso vacio que devuelve la misma lista que se da como parametro
		case BEmpty<Character> t -> res;

		// Caso hoja en el que compruebo si el caracter no es el mismo que el dado como
		// parametro y,
		// si no es asi, lo sumo al acumulador que contiene los caracteres anteriores y
		// lo introduzco en la lista resultado
		case BLeaf<Character> t -> {
			if (t.label() != charact) {
				ac += t.label().toString();
				res.add(ac);
			}
			yield res;
		}

		// Caso arbol:
		// Compruebo si el caracter no es el mismo que el dado como parametro y,
		// -Si no es así, lo sumo al acumulador que contiene los caracteres anteriores
		// y realizo laLlamada recursiva a los nodos hijos (izq y derecho)
		case BTree<Character> t -> {
			if (t.label() != charact) {
				ac += t.label().toString();
				recursivaBinario(t.left(), charact, ac, res);
				recursivaBinario(t.right(), charact, ac, res);
			}
			yield res;
		}

		};
	}

	public static List<String> solucionRecursivaNario(Tree<Character> tree, Character charact) {
		return recursivaNario(tree, charact, "", new ArrayList<String>());
	}

	// Llamada recursiva que tiene como parametros el tree y charact que nos da el
	// enunciado y
	// un ac que es donde voy a ir guardando las cadenas en cada llamada para seguir
	// formando la cadena hasta llegar a una hoja
	// una lista resultado que es donde voy a ir guardando cada cadena cuando llegue
	// a una hoja no vacia
	private static List<String> recursivaNario(Tree<Character> tree, Character charact, String ac, 
			List<String> res) {
		return switch (tree) {
		// Switch con expresiones que tendran tres casos nodo vacio, nodo hoja y nodo
		// arbol

		// Caso vacio que devuelve la misma lista que se da como parametro
		case TEmpty<Character> t -> res;

		// Caso hoja en el que compruebo si el caracter no es el mismo que el dado como
		// parametro y,
		// si no es asi, lo sumo al acumulador que contiene los caracteres anteriores y
		// lo introduzco en la lista resultado
		case TLeaf<Character> t -> {
			if (t.label() != charact) {
				ac += t.label().toString();
				res.add(ac);
			}
			yield res;
		}

		// Caso arbol:
		// Compruebo si el caracter no es el mismo que el dado como parametro y,
		// si no es asi, para cada elemento hijo del arbol, realizo una llamada
		// recursiva con este elemto y sumandole el caracter del nodo actual
		case TNary<Character> t -> {
			if (t.label() != charact) {
				final String acFinal = new String(ac);
				t.elements().forEach(tc -> recursivaNario(tc, charact, acFinal + t.label().toString(), res));
			}
			yield res;
		}

		// Por defecto, devuelvo la misma lista que se da como parametro
		default -> res;
		};
	}
}
