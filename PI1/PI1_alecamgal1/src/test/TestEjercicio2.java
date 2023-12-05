package test;

import java.util.List;

import ejercicios.Ejercicio1;
import ejercicios.Ejercicio2;
import us.lsi.common.Files2;

public class TestEjercicio2 {
	
	private final static String FILE = "ficheros/PI1Ej2DatosEntrada.txt";

	public static void main(String[] args) {
		//Ejercicio1
		testE2_SolucionIterativa();
		testE2_SolucionRecNoFinal();
		testE2_SolucionRecFinal();
		testE2_SolucionFuncional();
	}
	
	private static void testE2_SolucionIterativa() {
		System.out.println("1) Solucion Iterativa:");
		//Lineas a una lista de String
		List<String> lineas = Files2.linesFromFile(FILE);
		//Cada String correspondiente a una linea tratarlo separando cada parte en un for y mostrando solución
		for (int i=0; i<lineas.size(); i++) {
			String [] linea = lineas.get(i).split(",");
			Integer a = Integer.parseInt(linea[0].trim());
			Integer b = Integer.parseInt(linea[1].trim());
			String s = linea[2].trim();
			
			System.out.println("Test " + (i+1) + ":" + Ejercicio2.solucionIterativa(a, b, s));
		}
		System.out.println();	
	}
	
	private static void testE2_SolucionRecNoFinal() {
		System.out.println("2) Solucion Recursiva No Final:");
		//Lineas a una lista de String
		List<String> lineas = Files2.linesFromFile(FILE);
		//Cada String correspondiente a una linea tratarlo separando cada parte en un for y mostrando solución
		for (int i=0; i<lineas.size(); i++) {
			String [] linea = lineas.get(i).split(",");
			Integer varA = Integer.parseInt(linea[0].trim());
			Integer varB = Integer.parseInt(linea[1].trim());
			String varC = linea[2].trim();
			
			System.out.println("Test " + (i+1) + ":" + Ejercicio2.solucionRecNoFinal(varA, varB, varC));
		}
		System.out.println();	
	}
	
	private static void testE2_SolucionRecFinal() {
		System.out.println("3) Solucion Recursiva Final:");
		//Lineas a una lista de String
		List<String> lineas = Files2.linesFromFile(FILE);
		//Cada String correspondiente a una linea tratarlo separando cada parte en un for y mostrando solución
		for (int i=0; i<lineas.size(); i++) {
			String [] linea = lineas.get(i).split(",");
			Integer varA = Integer.parseInt(linea[0].trim());
			Integer varB = Integer.parseInt(linea[1].trim());
			String varC = linea[2].trim();
			
			System.out.println("Test " + (i+1) + ":" + Ejercicio2.solucionRecFinal(varA, varB, varC));
		}
		System.out.println();	
	}
	
	private static void testE2_SolucionFuncional() {
		System.out.println("4) Solucion Funcional:");
		//Lineas a una lista de String
		List<String> lineas = Files2.linesFromFile(FILE);
		//Cada String correspondiente a una linea tratarlo separando cada parte en un for y mostrando solución
		for (int i=0; i<lineas.size(); i++) {
			String [] linea = lineas.get(i).split(",");
			Integer varA = Integer.parseInt(linea[0].trim());
			Integer varB = Integer.parseInt(linea[1].trim());
			String varC = linea[2].trim();
			
			System.out.println("Test " + (i+1) + ":" + Ejercicio2.solucionFuncional(varA, varB, varC));
		}
		System.out.println();	
	}
	
}
