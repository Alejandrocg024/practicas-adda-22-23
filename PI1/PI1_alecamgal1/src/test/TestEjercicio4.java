package test;

import java.util.List;

import ejercicios.Ejercicio1;
import ejercicios.Ejercicio2;
import ejercicios.Ejercicio4;
import us.lsi.common.Files2;

public class TestEjercicio4 {
	
	private final static String FILE = "ficheros/PI1Ej4DatosEntrada.txt";

	public static void main(String[] args) {
		testE4_solucionRecSinMem();
		testE4_solucionRecConMem();
		testE4_solucionIterativa();
	}
	
	private static void testE4_solucionRecSinMem() {
		System.out.println("1) Solucion Recursiva Sin Memoria:");
		//Lineas a una lista de String
		List<String> lineas = Files2.linesFromFile(FILE);
		//Cada String correspondiente a una linea tratarlo separando cada parte en un for y mostrando solución
		for (int i=0; i<lineas.size(); i++) {
			String [] linea = lineas.get(i).split(",");
			Integer a = Integer.parseInt(linea[0].trim());
			Integer b = Integer.parseInt(linea[1].trim());
			Integer c = Integer.parseInt(linea[2].trim());
			
			System.out.println("Test " + (i+1) + ":" + Ejercicio4.solucionRecSinMem(a, b, c));
		} 
		System.out.println();	
	}
	
	private static void testE4_solucionRecConMem() {
		System.out.println("1) Solucion Recursiva Con Memoria:");
		//Lineas a una lista de String
		List<String> lineas = Files2.linesFromFile(FILE);
		//Cada String correspondiente a una linea tratarlo separando cada parte en un for y mostrando solución
		for (int i=0; i<lineas.size(); i++) {
			String [] linea = lineas.get(i).split(",");
			Integer a = Integer.parseInt(linea[0].trim());
			Integer b = Integer.parseInt(linea[1].trim());
			Integer c = Integer.parseInt(linea[2].trim());
			
			System.out.println("Test " + (i+1) + ":" + Ejercicio4.solucionRecConMem(a, b, c));
		} 
		System.out.println();	
	}
	private static void testE4_solucionIterativa() {
		System.out.println("1) Solucion Iterativa:");
		//Lineas a una lista de String
		List<String> lineas = Files2.linesFromFile(FILE);
		//Cada String correspondiente a una linea tratarlo separando cada parte en un for y mostrando solución
		for (int i=0; i<lineas.size(); i++) {
			String [] linea = lineas.get(i).split(",");
			Integer a = Integer.parseInt(linea[0].trim());
			Integer b = Integer.parseInt(linea[1].trim());
			Integer c = Integer.parseInt(linea[2].trim());
			
			System.out.println("Test " + (i+1) + ":" + Ejercicio4.solucionIterativa(a, b, c));
		} 
		System.out.println();	
	}
	
}
