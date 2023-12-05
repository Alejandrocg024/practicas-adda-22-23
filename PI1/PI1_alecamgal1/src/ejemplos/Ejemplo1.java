package ejemplos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import us.lsi.geometria.Punto2D;
import us.lsi.geometria.Punto2D.Cuadrante;

public class Ejemplo1 {
	
	public static Map <Cuadrante, Double> solucionFuncional(List<Punto2D> ls) {
		return ls.stream().collect( //convertir stream en otro tipo de dato en este caso en un map
				Collectors.groupingBy(Punto2D::getCuadrante, Collectors.reducing( //clave es el cuadrante y 
						//valor utilizo de nuevo collections para convertir desde un dato a otro
						//con el reducing hago una reduccion dentro del gruopingBy, reduccion multi-level y 
						//tiene como argumentos (U identity, una funcion, operador binario)
						//Mi identidad de los valores mapeados es = 0.
						//La funcion es que desde los valores obtenidos x, utilizo el metodo .x() = x.x()
						//Mi operador binario es el que suma los dos valores de entrada (x,y) -> x+y;
						0.,x->x.x(), (x,y) -> x+y)));
	}
	
	//Pasamos una lista como parametro  de tipo Punto 2D en la llamada
	public static Map<Cuadrante, Double> solucionIterativa(List<Punto2D> ls) {
		//indice (para recorrer la lista)
		Integer e = 0;
		
		//acumulador (por cada cuadrante, es un map) podemos utilizar un map y acumular por cuadrantes 
		//o hacer 4 acumuladores para ir sumando las propiedades x de cada cuadrante
		Map<Cuadrante, Double> ac = new HashMap<>();
		
		while (e < ls.size()) { //iteramos tantas veces como el tamaño de la lista
			Punto2D p = ls.get(e); //obtengo el punto correspondietne
			Cuadrante key = p.getCuadrante(); //identifico el cuadrante
			if (ac.containsKey(key)) //si el cuadrante existe
				ac.put(key, ac.get(key) + p.x()); //le sumo la x
			else //si no existe
				ac.put(key, p.x()); //inicializamos la clave con el cuadrante y el propio valor de x
			e++; //increment la secuencia para analizar el siguiente punto
		}
		return ac; //finalmente, devuelvo el acumulador
	}
	
	public static Map<Cuadrante, Double> solucionRecursivaFinal(List<Punto2D> ls) {
		return recFinal(0, new HashMap<>(), ls); //llama a la llamada recursiva con los datos inicializados 
		//con valores que son = sec, acum, lista que es el parametro que analizaremos
	}
	
	private static Map<Cuadrante, Double> recFinal (Integer e, Map<Cuadrante, Double> ac, List<Punto2D> ls) {
		Map<Cuadrante, Double> r = ac; //doy el valor que nos dan como parametro de acumulador a r 
		//Sque es el resultado que voy guardando gracias a las llamadas recursivas
		//r es el final y ac al que le hacemos los calculos y damos como parametro en cada paso.
		
		if (e < ls.size()) { //el if es igual que en la solucion iterativa
			Punto2D p = ls.get(e);
			Cuadrante key = p.getCuadrante();
			if (ac.containsKey(key)) {
				ac.put(key, ac.get(key) + p.x());
			} else {
				ac.put(key, p.x());
			}
			
			//Llamada recursiva
			r = recFinal (e+1, ac, ls);
		}
		return r;
	}
}
