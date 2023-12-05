package test;

import java.util.List;

import ejercicios.Ejercicio1;
import us.lsi.common.Files2;

public class TestEjercicio1 {
	
	private final static String FILE = "ficheros/PI1Ej1DatosEntrada.txt";

	public static void main(String[] args) {
		//Ejercicio1
		testE1_SolucionFuncional();
		testE1_SolucionIterativa();
		testE1_SolucionRecFinal();
	}
	
	private static void testE1_SolucionFuncional() {
		System.out.println("1) Solucion Imperativa:");
		//Lineas a una lista de String
		List<String> lineas = Files2.linesFromFile(FILE);
		//Cada String correspondiente a una linea tratarlo separando cada parte en un for y mostrando solución
		for (int i=0; i<lineas.size(); i++) {
			String [] linea = lineas.get(i).split(",");
			Integer varA = Integer.parseInt(linea[0].trim());
			String varB = linea[1].trim();
			Integer varC = Integer.parseInt(linea[2].trim());
			String varD = linea[3].trim();
			Integer varE = Integer.parseInt(linea[4].trim());
			
			System.out.println("Test " + (i+1) + ":" + Ejercicio1.ejercicioA(varA, varB, varC, varD, varE));
		}
		System.out.println();
				
	}
	
	private static void testE1_SolucionIterativa() {
		System.out.println("2) Solucion Iterativa:");
		//Lineas a una lista de String
		List<String> lineas = Files2.linesFromFile(FILE);
		//Cada String correspondiente a una linea tratarlo separando cada parte en un for y mostrando solución
		for (int i=0; i<lineas.size(); i++) {
			String [] linea = lineas.get(i).split(",");
			Integer varA = Integer.parseInt(linea[0].trim());
			String varB = linea[1].trim();
			Integer varC = Integer.parseInt(linea[2].trim());
			String varD = linea[3].trim();
			Integer varE = Integer.parseInt(linea[4].trim());
			
			System.out.println("Test " + (i+1) + ":" + Ejercicio1.solucionIterativa(varA, varB, varC, varD, varE));
		}
		System.out.println();
				
	}
	
	private static void testE1_SolucionRecFinal() {
		System.out.println("3) Solucion Recursiva Final:");
		//Lineas a una lista de String
		List<String> lineas = Files2.linesFromFile(FILE);
		//Cada String correspondiente a una linea tratarlo separando cada parte en un for y mostrando solución
		for (int i=0; i<lineas.size(); i++) {
			String [] linea = lineas.get(i).split(",");
			Integer varA = Integer.parseInt(linea[0].trim());
			String varB = linea[1].trim();
			Integer varC = Integer.parseInt(linea[2].trim());
			String varD = linea[3].trim();
			Integer varE = Integer.parseInt(linea[4].trim());
			
			System.out.println("Test " + (i+1) + ":" + Ejercicio1.solucionRecursivaFinal(varA, varB, varC, varD, varE));
		}
		System.out.println();
				
	}

}
