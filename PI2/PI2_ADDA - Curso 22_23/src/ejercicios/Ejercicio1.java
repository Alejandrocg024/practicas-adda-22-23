package ejercicios;

import java.math.BigInteger;

public class Ejercicio1 {
	
	/*
	 * 1. Analizar los tiempos de ejecución de las versiones recursiva e iterativa para el cálculo
	 * del factorial. Comparar según los resultados sean de tipo Double o BigInteger.
	 */
	
	//Calculo de factorial de forma Recursiva utilizando Double
	public static Double factorialDoubleRec(Integer n) {
		Double res = 1.;
		if(n>1) {
			res = n*factorialDoubleRec(n-1);
		}
		return res;
	}
	
	//Calculo de factorial de forma Iterativa utilizando Double
	public static Double factorialDoubleIter(Integer n) {
		Double res = 1.;
		while(n>1) {
			res = res * n;
			n -= 1;
		}
		return res;
	}
	
	//Calculo de factorial de forma Recursiva utilizando BigInteger
	public static BigInteger factorialBigIntegerRec(Integer n) {
		BigInteger res = BigInteger.ONE;
		if(n>1) {
			res = BigInteger.valueOf(n).multiply(factorialBigIntegerRec(n-1));
		}
		return res;
	}
	
	//Calculo de factorial de forma Iterativa utilizando BigInteger
	public static BigInteger factorialBigIntegerIter(Integer n) {
		BigInteger res = BigInteger.ONE;
		while(n>1) {
			res = res.multiply(BigInteger.valueOf(n));
			n -= 1;
		}
		return res;
	}

}
