package test;

import java.util.List;

import ejercicios.Ejercicio1;
import ejercicios.Ejercicio2;
import ejercicios.Ejercicio3;
import ejercicios.Ejercicio4;
import us.lsi.common.Files2;
import us.lsi.geometria.Punto2D;

public class TestEjercicios {
	private final static String FILE_E1 = "ficheros/PI1Ej1DatosEntrada.txt";
	private final static String FILE_E2 = "ficheros/PI1Ej2DatosEntrada.txt";
	private final static Integer NUM_PARES_ARCHIVOS_E3 = 3;
	private final static String FILE_E4 = "ficheros/PI1Ej4DatosEntrada.txt";

	public static void main(String[] args) {
		testE1();
		testE2();
		testE3();
		testE4();
	}

	private static void testE1() {
		System.out.println("Ejercicio1");
		List<String> lineas = Files2.linesFromFile(FILE_E1);
		for (int i = 0; i < lineas.size(); i++) {
			String[] linea = lineas.get(i).split(",");
			Integer varA = Integer.parseInt(linea[0].trim());
			String varB = linea[1].trim();
			Integer varC = Integer.parseInt(linea[2].trim());
			String varD = linea[3].trim();
			Integer varE = Integer.parseInt(linea[4].trim());

			System.out.println("\nTest " + (i + 1) + ":");
			System.out.println("1) Solucion Ejercicio A:");
			System.out.println(Ejercicio1.ejercicioA(varA, varB, varC, varD, varE));
			System.out.println("2) Solucion Iterativa:");
			System.out.println(Ejercicio1.solucionIterativa(varA, varB, varC, varD, varE));
			System.out.println("3) Solucion Recursiva Final:");
			System.out.println(Ejercicio1.solucionRecursivaFinal(varA, varB, varC, varD, varE));
		}
		System.out.println(
				"............................................................................................................................"
						+ "\n\n");

	}

	private static void testE2() {
		System.out.println("Ejercicio2");
		List<String> lineas = Files2.linesFromFile(FILE_E2);
		for (int i = 0; i < lineas.size(); i++) {
			String[] linea = lineas.get(i).split(",");
			Integer a = Integer.parseInt(linea[0].trim());
			Integer b = Integer.parseInt(linea[1].trim());
			String s = linea[2].trim();

			System.out.println("\nTest " + (i + 1) + ":");
			System.out.println("1) Solucion Iterativa:");
			System.out.println(Ejercicio2.solucionIterativa(a, b, s));
			System.out.println("2) Solucion Recursiva No Final:");
			System.out.println(Ejercicio2.solucionRecNoFinal(a, b, s));
			System.out.println("3) Solucion Recursiva Final:");
			System.out.println(Ejercicio2.solucionRecFinal(a, b, s));
			System.out.println("4) Solucion Funcional:");
			System.out.println(Ejercicio2.solucionFuncional(a, b, s));
		}
		System.out.println(
				"............................................................................................................................"
						+ "\n\n");
	}

	private static void testE3() {
		System.out.println("Ejercicio3");
		for (int i = 1; i <= NUM_PARES_ARCHIVOS_E3; i++) {
			String fileA = "ficheros/PI1Ej3DatosEntrada" + i + "A.txt";
			String fileB = "ficheros/PI1Ej3DatosEntrada" + i + "B.txt";

			System.out.println("\nTest " + (i + 1) + ":");
			System.out.println("1) Solucion Iterativa:");
			List<Punto2D> resIt = Ejercicio3.solucionIterativa(fileA, fileB);
			System.out.println("Los siguientes " + resIt.size() + " puntos:\n" + resIt);

			System.out.println("2) Solucion Recursiva Final:");
			List<Punto2D> resRecFin = Ejercicio3.solucionRecFinal(fileA, fileB);
			System.out.println("Los siguientes " + resRecFin.size() + " puntos:\n" + resRecFin);

			System.out.println("3) Solucion Funcional:");
			List<Punto2D> resFunc = Ejercicio3.solucionFuncional(fileA, fileB);
			System.out.println("Los siguientes " + resFunc.size() + " puntos:\n" + resFunc);

		}
		System.out.println(
				"............................................................................................................................"
						+ "\n\n");
	}

	private static void testE4() {
		System.out.println("Ejercicio4");
		List<String> lineas = Files2.linesFromFile(FILE_E4);
		for (int i = 0; i < lineas.size(); i++) {
			String[] linea = lineas.get(i).split(",");
			Integer a = Integer.parseInt(linea[0].trim());
			Integer b = Integer.parseInt(linea[1].trim());
			Integer c = Integer.parseInt(linea[2].trim());

			System.out.println("Test " + (i + 1) + ":");
			System.out.println("1) Solucion Recursiva Sin Memoria:");
			System.out.println(Ejercicio4.solucionRecSinMem(a, b, c));
			System.out.println("2) Solucion Recursiva Con Memoria:");
			System.out.println(Ejercicio4.solucionRecConMem(a, b, c));
			System.out.println("3) Solucion Iterativa:");
			System.out.println(Ejercicio4.solucionIterativa(a, b, c));
		}
		System.out.println(
				"............................................................................................................................"
						+ "\n\n");
	}

}
