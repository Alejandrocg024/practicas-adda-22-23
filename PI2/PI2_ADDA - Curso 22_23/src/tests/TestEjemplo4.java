package tests;

import java.util.ArrayList;
import java.util.List;

import ejemplos.Ejemplo4;
import us.lsi.common.Files2;
import us.lsi.common.Pair;
import us.lsi.tiposrecursivos.BinaryTree;

public class TestEjemplo4 {

	public static void main(String[] args) {
		testEjemplo4();
	}

	private static List<Character> stringListToCharList(String s){
		//String [A,B,C] -> String ABC
		String letras = s.replace("[", "").replace("]", "").replace(",", "");
		//A partir del string ABC --> Lista de caracteres [A,B,C]
		List<Character> res = new ArrayList<Character>();
		for (int i = 0; i < letras.length(); i++) {
			res.add(letras.charAt(i));
		}
		return res;
	}
	
	private static void testEjemplo4() {
		String file = "ficheros/Ejemplo4DatosEntrada.txt"; 
		
		//Cada linea es 'A(B,C)#[A,B,C]'
		List<Pair<BinaryTree<Character>, List<Character>>> inputs = 
				Files2.streamFromFile(file)
				.map(linea -> { //varias lineas en la expresion, tiene que haber un return
					String[] aux = linea.split("#");
					return Pair.of(BinaryTree.parse(aux[0], s -> s.charAt(0)), 
							stringListToCharList(aux[1]));
				}).toList();
		
		for(Pair<BinaryTree<Character>, List<Character>> par : inputs) {
			BinaryTree<Character> tree = par.first();
			List<Character> chars = par.second();
			System.out.println("Arbol: " + tree + "--> " + chars + " [" + Ejemplo4.solucion_recursiva(tree, chars) + "]");
		}
	}

}
