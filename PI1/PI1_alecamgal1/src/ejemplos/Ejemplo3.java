package ejemplos;

import java.util.HashMap;
import java.util.Map;

import us.lsi.common.IntPair;

public class Ejemplo3 {
	/* Secuencia: es a y b que termina cuando a<2 y b<2, nuestra funcion sig elemento sera la llamada recurviva
	 * Acum: estamos acumulando enteros con un valor inicial 0, suma.
	 * 
	 * RECURSIVA SIN MEMORIA
	 * Una vez que la funcion reciba esos valores comprueba si es caso base y si no, saltamos a la llaada recursiva y cambiamos el estado de las variables.
	 * 
	 */
	public static Integer solucionRecursivaSinMemoria(Integer a, Integer b) { //Definición del enunciado
		Integer r = null; //Valor inicial acumulador
		if (a < 2 || b < 2) { //es caso base?
			r = a*a + b; //solucion caso base
		} else {
			//caso recursion
			r = solucionRecursivaSinMemoria(a/2, b-1) + solucionRecursivaSinMemoria(a/3, b-2);
		}
		return r;
	}
	
	/*
	 * RECURSIVA CON MEMORIA
	 * Necesitamos un mapa para cambiar de sin memoria acon memoria, relacionar pares a y b con respecto a la suma que generan.
	 * Haremos dos llamadas, una primera en la que actualizamos el diccionario y pasamos los valores y otra version en la que ya empezamos a actuailzar estas llamadas
	 * En la llamada recursiva tendremos que almacenar el diccionario para que este disponible en la siguiente llamada
	 * 
	 * Primero pasamos a y b e interpretamos nuestro mapa que es la memoria
	 * 
	 * Lo que vamos a hacer es, existe este valor en memoria, si existe devuelvemelo y si no existe, vemos si es caso base y si no es el caso recursivo pues llama. Si no esta contenido el valor, lo almacenamos y final
	 * finalmente, devolvemos r.
	 * 
	 * 
	 */
	public static Integer solucionRecursivaConMemoria(Integer a, Integer b) {
		return gRecConMem(a, b, new HashMap<>());
		//para la solucion recursiva con memoria necesitamos un diccionario.
		//este se define con el tipo HashMap
	}
	
	private static Integer gRecConMem(Integer a, Integer b, Map<IntPair, Integer> m) { //integramos la memoria en cada llamada
		Integer r = null;
		IntPair key = IntPair.of(a, b);
		//vamos a guardar en memoria el estado y los resultados, para ello utilizamos el par clave (a,b)
		
		if(m.containsKey(key)) {
			//si el valor ya se ha calculado previamente solo cargamos su valor en memoria
			r = m.get(key);
		} else {
			if (a <2 || b < 2) { //caso base?
				r = a*a + b; //solucion caso base
			} else { //caso recursivo
				r = gRecConMem(a/2, b-1, m) + gRecConMem(a/3, b-2, m); //funcion combinacion 
			}
			m.put(IntPair.of(a, b), r); //Lo añadimos al diccionario
		}
		return r;
	}
	
	/*
	 * ITERATIVA CON MEMORIA
	 * Desde el caso base al caso que queremos, definir tantos bucles como variable tengamos y dentro de estos las condiciones de que llegamos al caso que queremos
	 * Pasamos nuestros valores, definimos el ac y entonces vamos desde el caso base al que necesitamos y para hacer eso i<=a, j<=b
	 * 
	 * Calculamos caso base y luego empezamos a hacer el bucle y obtener resultados en memoria que los iremos actualizando e el map.
	 * 
	 * Iremos rellenando nuestro diccionario a la inversa, desde el caso base al que neceistamos que es cuando termina. Cuando terminan los bucles devolvemos los resultados
	 */
	public static Integer solucionIterativa(Integer a, Integer b) {
		Map<IntPair, Integer> map = new HashMap<>(); //creamos diccionario para guardar los resultados en memoria
		
		Integer v=null; //inicializamos el acumulador
		//vamos a necesitar un nº de bucles igual al nº de variables que cambian de estado
		for (int i = 0; i <= a; i++) {
			for (int j = 0; j <=b; j++) {
				if (i < 2 || j < 2) { //caso base
					v = i*i + j;
				} else { //caso recursivo
					v = map.get(IntPair.of(i/2, j-1)) + map.get(IntPair.of(i/3, j-2));
				}
				map.put(IntPair.of(i,j), v);
			}
		}
		return map.get(IntPair.of(a, b));
	}
	
}
