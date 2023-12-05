package ejercicios;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import us.lsi.geometria.Punto2D;
import us.lsi.geometria.Punto2D.Cuadrante;
import us.lsi.streams.Stream2;

public class Ejercicio3 {
	/*
	 * 3. A partir de 2 ficheros ordenados de objetos de tipo Punto2D, obtener una lista ordenada 
	 * de puntos que incluya sólo los puntos del primer y tercer cuadrante. Para realizar la fusión 
	 * debe hacer uso de iteradores directamente sobre los ficheros de entrada, no permitiéndose 
	 * almacenar los puntos en listas y hacer fusión de listas.
	 * 
	 * Proporcione una solución iterativa usando while, una recursiva final,
	 * y una en notación funcional.
	 * 
	 * Tendremos funciones con los nombres de los ficheros, estos nombres con a stream y aqui hacemos la lectura
	 * una vez que tengamos esto lo pasaremos a iterator Iterador<Stream>
	 * Con este iterador empezamos a hacer las operaciones. En cada llamada accedemos a los puntos de los iteradores con una serie de funciones que es next y hasNext
	 * 
	 * Dado que tenemos dos iteradores, la condicion de terminacion es cuando uno de los dos iteradores no tenga hasNext porque ya no se puede comparadar.
	 * 
	 * Comprobamos, si siguen habiendo puntos, comprobamos los cuadrantes.
	 * 
	 * 
	 * 
	 */
	private static Iterator<Punto2D> tratarFile(String file){ //metodo con el que desde un String que obtenemos en el fichero transformo a un Iterador con puntos
		Function<String, Punto2D> parsePunto = s -> { 
			String [] v = s.split(",");
			return  Punto2D.of(Double.valueOf(v[0]), Double.valueOf(v[1]));
		};
		
		Iterator<Punto2D> p = Stream2.file(file).map(parsePunto).sorted().iterator();
		return p;
	}
	
	public static List<Punto2D> solucionIterativa(String fileA, String fileB){
		//Sacar dos iteradores con los puntos ordenados
		Iterator<Punto2D> itA = tratarFile(fileA);
		Iterator<Punto2D> itB = tratarFile(fileB);
		List<Punto2D> res = new ArrayList<>();
		//Inicializo los primeros puntos
		Punto2D pA = itA.next();
		Punto2D pB= itB.next();

		while(itA.hasNext()) {
			while(itB.hasNext()) { //Trato todos los casos en los que los dos iteradores tengan valores y paso al siguiente valor cuando añado ese valor a la lista
				if(pA.compareTo(pB) > 0) {
					if (pB.getCuadrante()==Cuadrante.PRIMER_CUADRANTE || pB.getCuadrante()==Cuadrante.TERCER_CUADRANTE) {
						res.add(pB);
					}
					pB = itB.next();
				} else {
					if (pA.getCuadrante()==Cuadrante.PRIMER_CUADRANTE || pA.getCuadrante()==Cuadrante.TERCER_CUADRANTE) {
						res.add(pA);
					}
					if (itA.hasNext()) {
						pA = itA.next();
					} else {
						//En el caso de que el iterador A no tenga más, entraran los del B y como ya estan ordenados simplemente los añado.
						if (pB.getCuadrante()==Cuadrante.PRIMER_CUADRANTE || pB.getCuadrante()==Cuadrante.TERCER_CUADRANTE) {
							res.add(pB);
						}
						pB = itB.next();
					}
				}
			}
			
			//no hay mas elementos en B y sigue habiendo en A, los introduzco en la lista como ya estan ordenados
			if (pA.getCuadrante()==Cuadrante.PRIMER_CUADRANTE || pA.getCuadrante()==Cuadrante.TERCER_CUADRANTE) {
				res.add(pA);
			} 
			if (itA.hasNext()) {
				pA = itA.next();
			}
		}
		//Ultimo caso en el que los iteradores no tienen valor siguiente
		if ((pA.getCuadrante()==Cuadrante.PRIMER_CUADRANTE || pA.getCuadrante()==Cuadrante.TERCER_CUADRANTE) && !(res.contains(pA))) {
			res.add(pA);
		} else if ((pB.getCuadrante()==Cuadrante.PRIMER_CUADRANTE || pB.getCuadrante()==Cuadrante.TERCER_CUADRANTE) && !(res.contains(pB))) {
			res.add(pB);
		}
		
		return res;
	}
	
	public static List<Punto2D> solucionRecFinal(String fileA, String fileB){
		//Sacar dos iteradores
		Iterator<Punto2D> itA = tratarFile(fileA);
		Iterator<Punto2D> itB = tratarFile(fileB);
		Punto2D pA = itA.next();
		Punto2D pB= itB.next();
		return RecFinal(fileA, fileB, itA, itB, pA, pB, new ArrayList<Punto2D>());
	}
	
	private static List<Punto2D> RecFinal(String fileA, String fileB, Iterator<Punto2D> itA, Iterator<Punto2D> itB, Punto2D pA, Punto2D pB, List<Punto2D> res){
		//Hago los mismos pasos que en la version iterativa con la excepcion de que en vez de pasar al siguiente element del iterador, realizo una llamada recursiva pasando al siguiente elemento
		if (itA.hasNext()) {
			if (itB.hasNext()) {
				if(pA.compareTo(pB) > 0) {
					if (pB.getCuadrante()==Cuadrante.PRIMER_CUADRANTE || pB.getCuadrante()==Cuadrante.TERCER_CUADRANTE) {
						res.add(pB);
					}
					RecFinal(fileA, fileB, itA, itB, pA, itB.next(), res);
				} else {
					if (pA.getCuadrante()==Cuadrante.PRIMER_CUADRANTE || pA.getCuadrante()==Cuadrante.TERCER_CUADRANTE) {
						res.add(pA);
					}
					if (itA.hasNext()) {
						RecFinal(fileA, fileB, itA, itB, itA.next(), pB, res);
					}
				}
			} else {
				if (pA.getCuadrante()==Cuadrante.PRIMER_CUADRANTE || pA.getCuadrante()==Cuadrante.TERCER_CUADRANTE) {
					res.add(pA);
				} 
				RecFinal(fileA, fileB, itA, itB, itA.next(), pB, res);
			}
		} else if(itB.hasNext()){
			//En el caso de que el iterador A no tenga más, entraran los del B y como ya estan ordenados.
			if (pB.getCuadrante()==Cuadrante.PRIMER_CUADRANTE || pB.getCuadrante()==Cuadrante.TERCER_CUADRANTE) {
				res.add(pB);
			}
			RecFinal(fileA, fileB, itA, itB, pA, itB.next(), res);
		}
		
		//Ultimo caso
		if ((pA.getCuadrante()==Cuadrante.PRIMER_CUADRANTE || pA.getCuadrante()==Cuadrante.TERCER_CUADRANTE) && !(res.contains(pA))) {
			res.add(pA);
		} else if ((pB.getCuadrante()==Cuadrante.PRIMER_CUADRANTE || pB.getCuadrante()==Cuadrante.TERCER_CUADRANTE) && !(res.contains(pB))) {
			res.add(pB);
		}
		return res;
		
	}
	
	public static List<Punto2D> solucionFuncional(String fileA, String fileB){
		Iterator<Punto2D> itA = tratarFile(fileA);
		Iterator<Punto2D> itB = tratarFile(fileB);
		List<Punto2D> res = new ArrayList<>();
		
		Predicate <Punto2D> filtro = p -> p.getCuadrante() == Cuadrante.PRIMER_CUADRANTE || p.getCuadrante() == Cuadrante.TERCER_CUADRANTE;
		Stream.of(itA, itB).iterator().forEachRemaining(x -> {
			x.forEachRemaining(p -> {
				res.add(p);
			});
		});
		
		return res.stream().filter(filtro).sorted().toList();
	}
}
