package test;

import java.util.List;
import java.util.function.Function;

import ejemplos.*;
import us.lsi.common.IntPair;
import us.lsi.geometria.Punto2D;
import us.lsi.streams.Stream2;

public class TestsEjemplos {

	public static void main(String[] args) {
		//testEjemplo1();
		//testEjemplo2();
		testEjemplo3();
	}
	
	private static void testEjemplo1() {
		Function<String, Punto2D> parsePunto = s -> { 
			String [] v = s.split(",");
			return  Punto2D.of(Double.valueOf(v[0]), Double.valueOf(v[1]));
		};
		String file = "ficheros/Ejemplo1DatosEntrada2.txt";
		List<Punto2D> ls = Stream2.file(file).map(parsePunto).toList();
		
		System.out.println("1) Solucion Funcional1:\n" + Ejemplo1.solucionFuncional(ls));
		System.out.println("2) Solucion Iterativa:\n" + Ejemplo1.solucionIterativa(ls));
		System.out.println("1) Solucion Rec. Final:\n" + Ejemplo1.solucionRecursivaFinal(ls));
		System.out.println("............................................................................................................................");
	}

	//En el ejemplo 2 nos dan pares de datos que almacenamos una lista que nos devuelve pares de punto y para cada punto llamo a las funciones llamadas anteriormente
	private static void testEjemplo2 () {
		String file = "ficheros/Ejemplo2DatosEntrada.txt";
		List<IntPair> ls = Stream2.file(file).map(IntPair::parse).toList();
		
		System.out.println("Soluciones ejemplo 2:\n");
		ls.forEach(par -> {
			Integer a = par.first(), b=par.second();
			System.out.println("1) Solucion R. NO Final: " + Ejemplo2.solucionRecursivaNoFinal(a, b));
			System.out.println("1) Solucion R. Final: " + Ejemplo2.solucionRecursivaFinal(a, b));
			System.out.println("1) Solucion R. Iterativa: " + Ejemplo2.solucionIterativa(a, b));
			System.out.println("1) Solucion R. Funcional: " + Ejemplo2.solucionFuncional(a, b));
			System.out.println("_________________________________________");
		});
		System.out.println("............................................................................................................................");
	}
	
	private static void testEjemplo3 () {
		String file = "ficheros/Ejemplo3DatosEntrada.txt";
		List<IntPair> ls = Stream2.file(file).map(IntPair::parse).toList();
		
		System.out.println("Soluciones ejemplo 3:\n");
		ls.forEach(par -> {
			Integer a = par.first(), b=par.second();
			System.out.println("1) Solucion R. Sin Mem.:" +
			Ejemplo3.solucionRecursivaSinMemoria(a,b));
			System.out.println("2) Solucion R. Con Mem.:" +
			Ejemplo3.solucionRecursivaConMemoria(a,b));
			System.out.println("3) Solucion Iterativa:" +
			Ejemplo3.solucionIterativa(a,b));
			System.out.println("_________________________________________");
		});
		System.out.println("............................................................................................................................");
	}
}
