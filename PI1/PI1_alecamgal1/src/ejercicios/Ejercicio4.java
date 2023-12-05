package ejercicios;

import java.util.HashMap;
import java.util.Map;

import us.lsi.common.IntPair;
import us.lsi.common.IntTrio;

public class Ejercicio4 {
	/*
	 * 4. Dada la siguiente definición recursiva de la función g (que toma como entrada 3
	 * números enteros positivos y devuelve una cadena): ****
	 * siendo + un operador que representa la concatenación de cadenas, y toString(i) un método
	 * que devuelve una cadena a partir de un entero.
	 * 
	 * Proporcione una solución recursiva sin memoria, otra recursiva con
	 * memoria, y otra iterativa.
	 */
	public static String solucionRecSinMem(Integer a, Integer b, Integer c) {
		String res = null;
		if (a < 2 && b <= 2 || c < 2) {
			res = String.format("(%d+%d+%d)", a, b, c);
		} else if (a < 3 || b < 3 && c <= 3) {
			res = String.format("(%d-%d-%d)", c, b, a);
		} else if (b%a == 0 && (a%2 == 0 || b%3 == 0)) {
			res = "(" + solucionRecSinMem(a-1, b/a, c-1) + "*" + solucionRecSinMem(a-2, b/2, c/2) + ")";
		} else {
			res = "(" + solucionRecSinMem(a/2, b-2, c/2) + "/" + solucionRecSinMem(a/3, b-1, c/3) + ")";
		}
		return res;
	}
	
	public static String solucionRecConMem(Integer a, Integer b, Integer c) {
		return gRecConMem(a, b, c, new HashMap<>());
	}
		
	
	private static String gRecConMem(Integer a, Integer b, Integer c, Map<IntTrio, String> m) {
		String res = null;
		IntTrio key = IntTrio.of(a, b, c);
		if (m.containsKey(key)) {
			res = m.get(key);
		} else {
			if (a < 2 && b <= 2 || c < 2) {
				res = String.format("(%d+%d+%d)", a, b, c);
			} else if (a < 3 || b < 3 && c <= 3) {
				res = String.format("(%d-%d-%d)", c, b, a);
			} else if (b%a == 0 && (a%2 == 0 || b%3 == 0)) {
				res = "(" + solucionRecSinMem(a-1, b/a, c-1) + "*" + solucionRecSinMem(a-2, b/2, c/2) + ")";
			} else {
				res = "(" + solucionRecSinMem(a/2, b-2, c/2) + "/" + solucionRecSinMem(a/3, b-1, c/3) + ")";
			}
			m.put(key, res);
		}
		return res;
	}
	
	public static String solucionIterativa(Integer a, Integer b, Integer c) {
		Map<IntTrio, String> m = new HashMap<>(); //creamos diccionario para guardar los resultados en memoria
		
		String res = null;
		for (int i = 0; i <= a; i++) {
			for (int j = 0; j <= b; j++) {
				for (int k = 0; k <= c; k++) {
					if (i < 2 && j <= 2 || k < 2) {
						res = String.format("(%d+%d+%d)", i, j, k);
					} else if (i < 3 || j < 3 && k <= 3) {
						res = String.format("(%d-%d-%d)", k, j, i);
					} else if (j%i == 0 && (i%2 == 0 || j%3 == 0)) {
						res = "(" + m.get(IntTrio.of(i-1, j/i, k-1)) + "*" + m.get(IntTrio.of(i-2, j/2, k/2)) + ")";
					} else {
						res = "(" + m.get(IntTrio.of(i/2, j-2, k/2)) + "/" + m.get(IntTrio.of(i/3, j-1, k/3)) + ")";
					}
					if (i == 17) {
						m = m;
					}
					m.put(IntTrio.of(i, j, k), res);
				}
			}
		}
		
		return m.get(IntTrio.of(a, b, c));
	}

}
