package ejercicios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import us.lsi.common.List2;
import us.lsi.geometria.Punto2D;
import us.lsi.geometria.Punto2D.Cuadrante;

public class Ejercicio1 {

	// Ejercicio 1: Analice el código que se muestra y proporcione una solución iterativa y otra recursiva final equivalentes.
	public static Map<Integer, List<String>> ejercicioA(Integer varA, String varB, Integer varC, String varD, Integer varE) {
		
		UnaryOperator<EnteroCadena> nx = elem -> { //UnaryOperator next de nuestro iterate
			return EnteroCadena.of(elem.a() + 2, //Devuelve un EnteroCadena con el primer argumento que será a + 2
					//y el segundo argumento podrá tener dos valores. Si a es divisible entre 3, será s + el toString de a, si no es así, será el string s menos los (a % longitud de s) primeros valores.
					elem.a() % 3 == 0 ?
					elem.s() + elem.a().toString() :
					elem.s().substring(elem.a() % elem.s().length()));  
		};
		return Stream.iterate(EnteroCadena.of(varA, varB), //Elemento raiz del que partiremos que es un EnteroCadena con el Integer varA y el String varB
				elem -> elem.a() < varC, //hasNext, con la condicion de parado cuando Integer varA sea menor que Integer varC
				nx) //next, elemento siguientec que es el UnaryOperator creado anteriormente
				
				.map(elem -> elem.s() + varD) //transformación que suma String varA con String varC 
				.filter(nom -> nom.length() < varE) //filtrado con los elementos que solamente queremos que son los que tienen la longitud del EnteroCadena menor que el Integer varE
				.collect(Collectors.groupingBy(String::length)); //Map que agrupa por lenght los EnteroCadena
	}
	
	//donde EnteroCadena es una clase con una propiedad entera a y otra de tipo cadena s, la cual debe implementar como un record. 
	private static record EnteroCadena(Integer a, String s) {
		public static EnteroCadena of(Integer a, String s) {
			//Metodo factoria para construir el tipo EnteroCadena
			return new EnteroCadena(a, s);
		}
	}
	
	//Iterativa
	public static Map<Integer, List<String>> solucionIterativa(Integer varA, String varB, Integer varC, String varD, Integer varE){
		Map <Integer, List<String>> ac = new HashMap<>(); //acumulador para guardar el resultado.

		//Voy iterando mientras evaluo las condiciones de la secuencia
		EnteroCadena ec = EnteroCadena.of(varA, varB);
		while (ec.a() < varC) { //condicion
			String res = ec.s() + varD;
			if(res.length() < varE) { //condicion
				Integer key = res.length(); //diccionario agrupado por la longitud
				if (ac.containsKey(key)) { //si ya esta en el diccionario esa clave le añado el valor
					ac.get(key).add(res);
				} else { //si no esta esa clave l inicializo
					ac.put(key, List2.of(res));
				}
			}
			//valor siguiente
			if (ec.a() % 3 == 0) { //valor siguiente si se cumple esa condicion
				ec = EnteroCadena.of(ec.a()+2, ec.s() + ec.a().toString());
			} else {
				ec = EnteroCadena.of(ec.a()+2, ec.s().substring(ec.a() % ec.s().length()));
			}
		}
		
		return ac;
	}
	
	//Recursiva final
	
	public static Map<Integer, List<String>> solucionRecursivaFinal(Integer varA, String varB, Integer varC, String varD, Integer varE) {
		return recFinal(varA, varB, varC, varD, varE, new HashMap<>(), EnteroCadena.of(varA, varB)); //llama a la llamada recursiva con los datos inicializados 
	}
	
	private static Map<Integer, List<String>> recFinal (Integer varA, String varB, Integer varC, String varD, Integer varE, Map<Integer, List<String>> ac, EnteroCadena ec) {
		Map<Integer, List<String>> r = ac; //doy el valor que nos dan como parametro de acumulador a r 
		//que es el resultado que voy guardando gracias a las llamadas recursivas
		//r es el final y ac al que le hacemos los calculos y damos como parametro en cada paso.
		
		//mismo procedimiento que en la iterativa a excepción del procedimiento al dar los siguientes valores
		if (ec.a() < varC) {
			String res = ec.s() + varD;
			if(res.length() < varE) {
				Integer key = res.length();
				if (ac.containsKey(key)) {
					ac.get(key).add(res);
				} else {
					ac.put(key, List2.of(res));
				}
			}
			//para dar los siguientes valores realizamos la llamada recursiva
			if (ec.a() % 3 == 0) {
				r = recFinal(varA, varB, varC, varD, varE, ac, EnteroCadena.of(ec.a()+2, ec.s() + ec.a().toString()));
			} else {
				r = recFinal(varA, varB, varC, varD, varE, ac, EnteroCadena.of(ec.a()+2, ec.s().substring(ec.a() % ec.s().length())));
			}
		}
		return r;
	}
}
