package tests;

import java.util.List;
import java.util.function.Predicate;

import ejemplos.Ejemplo5;
import us.lsi.common.Files2;
import us.lsi.math.Math2;
import us.lsi.tiposrecursivos.Tree;

public class TestEjemplo5 {
	
	private static final Predicate<Integer> PAR = x -> x%2==0;
	private static final Predicate<Integer> PRIMOS = x -> Math2.esPrimo(x);

	public static void main(String[] args) {
		testsEjemplo5();
	}
	
	private static void testsEjemplo5() {
		String file = "ficheros/Ejemplo5DatosEntrada.txt"; 
		
		//String 3(2,4,6) --> Tree<Integer>
		List<Tree<Integer>> inputs =
				Files2.streamFromFile(file)
				.map(linea -> Tree.parse(linea, s -> Integer.parseInt(s)))
				.toList();
		
		System.out.println("Solucion recursiva - PAR:");
		inputs.stream().forEach(x -> System.out.println(x + ": " + Ejemplo5.solucion_recursiva(x, PAR)));
		
		System.out.println("\nSolucion recursiva - PRIMO:");
		inputs.stream().forEach(x -> System.out.println(x + ": " + Ejemplo5.solucion_recursiva(x, PRIMOS)));
	}

}
