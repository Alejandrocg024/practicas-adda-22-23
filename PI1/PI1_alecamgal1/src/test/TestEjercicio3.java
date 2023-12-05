package test;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import ejercicios.Ejercicio1;
import ejercicios.Ejercicio2;
import ejercicios.Ejercicio3;
import us.lsi.common.Files2;
import us.lsi.geometria.Punto2D;
import us.lsi.streams.Stream2;

public class TestEjercicio3 {

	private final static Integer NUM_PARES_ARCHIVOS = 3;
	
	public static void main(String[] args) {
		//Ejercicio1
		testE3_SolucionIterativa();
		testE3_SolucionRecFinal();
		testE3_SolucionFuncional();
	}
	
	private static void testE3_SolucionIterativa() {
		System.out.println("1) Solucion Iterativa:");
		for (int i=1; i<=NUM_PARES_ARCHIVOS; i++) {
			String fileA = "ficheros/PI1Ej3DatosEntrada" + i + "A.txt";
			String fileB = "ficheros/PI1Ej3DatosEntrada" + i + "B.txt";
			List<Punto2D> res = Ejercicio3.solucionIterativa(fileA, fileB);
			System.out.println("Test " + i + ": Los siguientes " + res.size() + " puntos:\n" + res + "\n");	

			
		}
		System.out.println();	
	}	
	
	private static void testE3_SolucionRecFinal() {
		System.out.println("2) Solucion Recursiva Final:");
		for (int i=1; i<=NUM_PARES_ARCHIVOS; i++) {
			String fileA = "ficheros/PI1Ej3DatosEntrada" + i + "A.txt";
			String fileB = "ficheros/PI1Ej3DatosEntrada" + i + "B.txt";
			List<Punto2D> res = Ejercicio3.solucionRecFinal(fileA, fileB);
			System.out.println("Test " + i + ": Los siguientes " + res.size() + " puntos:\n" + res + "\n");	
		}
		System.out.println();	
	}	
	
	private static void testE3_SolucionFuncional() {
		System.out.println("3) Solucion Funcional:");
		for (int i=1; i<=NUM_PARES_ARCHIVOS; i++) {
			String fileA = "ficheros/PI1Ej3DatosEntrada" + i + "A.txt";
			String fileB = "ficheros/PI1Ej3DatosEntrada" + i + "B.txt";
			List<Punto2D> res = Ejercicio3.solucionFuncional(fileA, fileB);
			System.out.println("Test " + i + ": Los siguientes " + res.size() + " puntos:\n" + res + "\n");	
		}
		System.out.println();	
	}	
}
