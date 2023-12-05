package ejemplos;

import java.util.List;

import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.BinaryTree.BEmpty;
import us.lsi.tiposrecursivos.BinaryTree.BLeaf;
import us.lsi.tiposrecursivos.BinaryTree.BTree;

public class Ejemplo4 {
	/*
	 * 4. Implemente una función booleana que, dados un árbol binario de caracteres y una lista
de caracteres, determine si existe un camino en el árbol de la raíz a una hoja que sea igual
a la lista.
	 */
	
	public static Boolean solucion_recursiva(BinaryTree<Character> tree,
			List<Character> list) {
		return recursivo(tree, list, 0); //inicializamos a 0 que nos sirve para indicar en el nivel en el que estamos
	}

	//switch usando expresiones // version recomendada
	private static Boolean recursivo(BinaryTree<Character> tree, List<Character> list, Integer i) {
		Integer n = list.size();
		return switch(tree) {
		//Switch con expresiones que tendran tres casos nodo vacio, nodo hoja y nodo arbol
		
		//Caso vacio
		case BEmpty<Character> t -> false;
		
		//Caso hoja
		//comprobar que el caracter de la lista en el que estamos es igual a la etiqueta del nodo
		//Comprobar que estamos en un nodo hoja (en n tenemos el tamaño de la lista. n-i se tienee que cumplir que 1 porque ya estamos en el nodo hoja)
		case BLeaf<Character> t -> (n-1==i) && (list.get(i).equals(t.label()));
		
		//Caso arbol: 
		//comprobar que el caracter de la lista en el que estamos es igual a la etiqueta del nodo
		//Llamada recursiva a los nodos hijos (izq y derecho)
		case BTree<Character> t -> list.get(i).equals(t.label()) && 
			(recursivo(t.left(), list, i+1) || recursivo(t.right(), list, i+1)); //que se cumpla lo primero y lo segundo (Uno recursivo u otro)
		};
	}
	
	//switch usando sentencias
	private static Boolean recursivo2(BinaryTree<Character> tree, List<Character> list, Integer i) {
		Integer n = list.size();
		switch(tree) {
			case BEmpty<Character> t : return false;
			case BLeaf<Character> t : return (n-1==1) && (list.get(i).equals(t.label()));
			case BTree<Character> t : return list.get(i).equals(t.label()) &&
				(recursivo2(t.left(), list, i+1) || recursivo2(t.right(), list, i+1));
		}
	}
		
	//switch usando asignaciones
	private static Boolean recursivo3(BinaryTree<Character> tree, List<Character> list, Integer i) {
		Integer n = list.size();
		Boolean r;
		switch(tree) {
			case BEmpty<Character> t : r = false; break;
			case BLeaf<Character> t : r = (n-1==1) && (list.get(i).equals(t.label())); break;
			case BTree<Character> t : r = list.get(i).equals(t.label()) && (recursivo3(t.left(), list, i+1) || recursivo3(t.right(), list, i+1));
		}
		return r;
	}
}
