package ejemplos;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import us.lsi.tiposrecursivos.Tree;
import us.lsi.tiposrecursivos.Tree.TEmpty;
import us.lsi.tiposrecursivos.Tree.TLeaf;
import us.lsi.tiposrecursivos.Tree.TNary;

public class Ejemplo5 {

	//Comprobaremos un predicado que es una lista con booleans que en cada nivel comprueba
	//Por ejemplo si son pares en cada nivel, si son primos
	
	public static <E> List<Boolean> solucion_recursiva(Tree<E> tree, Predicate<E> pred){
		return recursivo(tree, pred, 0, new ArrayList<>());
	}
	
	//Switch usando expresiones
	private static <E> List<Boolean> recursivo(Tree<E> tree, Predicate<E> pred, Integer i, List<Boolean> res){
		//res es un objeto mutable que vamos a ir consturyendo
		if (res.size()<=i) {
			res.add(true);
		}
		return switch(tree) {
		//Caso nodo vacio
		case TEmpty<E> t -> res;
			
		//Caso hoja
		//Comprobar si se cumple el predicado
		case TLeaf<E> t -> {
			Boolean r = pred.test(t.label()) && res.get(i);
			res.set(i, r);
			yield res;
		}
			
		//Comprobar si se cumple el predicado: pred.test() --> boolean
		//Llamada recursiva de todos los nodos hijos
		case TNary<E> t -> {
			Boolean r = pred.test(t.label()) && res.get(i);
			res.set(i, r);
			t.elements().forEach(tc -> recursivo(tc, pred, i+1, res));
			yield res; //yield es como return dentro del switch
			}
		default -> res;
		};
	}

}
