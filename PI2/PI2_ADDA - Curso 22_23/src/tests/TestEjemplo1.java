package tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import ejemplos.Ejemplo1;
import us.lsi.common.Pair;
import us.lsi.common.Trio;
import us.lsi.curvefitting.DataCurveFitting;
import us.lsi.math.Math2;
import utils.GraficosAjuste;
import utils.Resultados;
import utils.TipoAjuste;

public class TestEjemplo1 {
	
	public static void main(String[] args) {
		generaFicherosTiempoEjecucion(); //se generan puntos relacionando el tamaño con el tiempo para resolver el problema
 		muestraGraficas(); //mostramos las graficas
	}
	
	//Tenemos de introducir estos parametros que se reutilizan para cada ejercicio, incluso nos pueden hacer falta mas.
	private static Integer nMin = 50; // n mínimo para el cálculo de potencia -> menor tamaño con el que probamos
	private static Integer nMax = 100000; // n máximo para el cálculo de potencia -> maximo tamaño con el que qwueremos probar
	private static Integer numSizes = 50; // número de problemas (número de potencias distintas a calcular) -> numero de tamaños diferentes con los que probamos. PEm cogeme problemas de tam 50, 100, 150, 200, establece el incremento
	private static Integer numMediciones = 10; // número de mediciones de tiempo de cada caso (número de experimentos) -> num de veces que repetimos los experimentos y nos quedamos con el minimo
	private static Integer numIter = 50; // número de iteraciones para cada medición de tiempo -> para un tamaño determinado repetimos el calculo 50 veces y nos quedamos con la media
	private static Integer numIterWarmup = 100000; // número de iteraciones para warmup (bucle for para llamar al metodoc varias veces)
	
	// Lista de trios con los metodos a probar. Cada trio está formado por:
	//	-La funcion a la que se va a medir el tiempo de ejecucion -> BiFunction
	//	-Tipo de ajuste que se realiza en la grafica
	//	-Etiqueta para identificar grafica y CSV
	private static List<Trio<BiFunction<Double, Integer, Number>, TipoAjuste, String>> metodos = 
			List.of(
					//Cada elemento representa un elemento a probar
				Trio.of(Ejemplo1::potenciaR, TipoAjuste.POWERANB, "PotenciaRecursiva(lineal)"), 
				Trio.of(Ejemplo1::potenciaIter,TipoAjuste.POWERANB, "PotenciaIterativa(lineal)"),
				Trio.of(Math2::pow, TipoAjuste.LOG2_0,"PotenciaRecursiva(log)"),
				Trio.of(Math2::powr, TipoAjuste.LOG2_0,"PotenciaIterativa(log)")
			);
	
	

	//primero generamos los ficheros para guardar los datos de ejecución
	public static void generaFicherosTiempoEjecucion() {
		
		for (int i=0; i<metodos.size(); i++) { //por cada elemnento de metodos medimos el tiempo de ejecucion (4 iteraciones)
						
			String ficheroSalida = String.format("ficheros/Tiempos%s.csv", //creamos ruta del fichero con el 3er elemento
					metodos.get(i).third());
			
			//llamamos al metodo que va a realizar las ejecuciones y mediciones
			testTiemposEjecucion(nMin, nMax, 
						metodos.get(i).first(),
						ficheroSalida
						);
			}
	}

	
	/*
	 * guardamos en un mapa el tamaño de nuestro objeto y el tiempo que tarda y mediante el bbucle llamamos varias veces a los metodos y vamos cambiando el tamaño de los problemas
	 * hacemos el warum que es llamar a la funcion una serie de veces
	 * calculamos el numero de iteraciones, las veces que vamo a solucionas para este tamaño
	 * vamos registrando mediante actualizatiempo
	 * lo guardamos como si fuera un fichero
	 * 
	 * nMin: valor min del calculo de la potencia
	 * nMax: valor max en el calculo de la potencia
	 * BiFunction: funcion en la que queremos medir el tiempo
	 * ficheroTiempos: ruta del fichero donde guardamos los resultados

	 */
	public static void testTiemposEjecucion(Integer nMin, Integer nMax,
			BiFunction<Double, Integer, Number> funcion,
			String ficheroTiempos
			) {
		
		Map<Problema, Double> tiempos = new HashMap<Problema,Double>(); //creamos un hashmap para guardar los tiempos de ejecucion de cada problema
		Integer nMed = numMediciones; 
		for (int iter=0; iter<nMed; iter++) { //numero de veces que vamos a realizar las mediciones
			for (int i=0; i<numSizes; i++) { //numero e llamadas recursivas a hacer
				Double r = Double.valueOf(nMax-nMin)/(numSizes-1);
				Integer tam = (Integer.MAX_VALUE/nMax > i) 
						? nMin + i*(nMax-nMin)/(numSizes-1)
						: nMin + (int) (r*i) ; //tamaño maximo en la potencia
				Problema p = Problema.of(tam);
				warmup(funcion, 10.,10); //"Calentamiento" para ajustar mejor el tiempo
				Integer nIter = numIter;
				Double[] res = new Double[nIter]; //array en el que guardaremos los resultados con un tamaño igual al numero de iteraciones
				Long t0 = System.nanoTime();
				for (int z=0; z<nIter; z++) {
					res[z] = (Double) funcion.apply(Double.valueOf(tam), tam); //guardamos los resultados de cada iteracion
				}
				Long t1 = System.nanoTime();
				actualizaTiempos(tiempos, p, Double.valueOf(t1-t0)/nIter); //actualizamos el tiempo relacionado con cada problema trar calcular
			}
			
		}
		
		//escribimos los resultados en un fichero
		Resultados.toFile(tiempos.entrySet().stream()
				.map(x->TResultD.of(x.getKey().tam(), 
									x.getValue()))
				.map(TResultD::toString),
				ficheroTiempos, true);
		
	}
	
	
	private static void actualizaTiempos(Map<Problema, Double> tiempos, Problema p, double d) {
		if (!tiempos.containsKey(p)) {
			tiempos.put(p, d);
		} else if (tiempos.get(p) > d) {
				tiempos.put(p, d);
		}
	}
	
	private static void warmup(BiFunction<Double,Integer, Number> pot, Double a, Integer n) {
		for (int i=0; i<numIterWarmup; i++) {
			pot.apply(a,n);
		}
	}

	record TResultD(Integer tam, Double t) {
		public static TResultD of(Integer tam, Double t){
			return new TResultD(tam, t);
		}
		
		public String toString() {
			return String.format("%d,%.0f", tam, t);
		}
	}

	record Problema(Integer tam) {
		public static Problema of(Integer tam){
			return new Problema(tam);
		}
	}
	
	
	/*
	 * para cada metodo que hemos generado antes obtenemos el label y hacemos el tipo de ajuste mediante el que generamos la grafica con esos ajustes que le pasamos un label para identificar
	 * hacemos llamada para ir acumulando resultados y enseñar grafica posteriormente
	 * 
	 * una vez generados los tiempos de ejecucion procedemos a realizar las graficas
	 */
	public static void muestraGraficas() {
		System.out.println("a*n^b*(ln n)^c + d");
		List<String> ficherosSalida = new ArrayList<>();
		List<String> labels = new ArrayList<>();
		for (int i=0; i<metodos.size(); i++) {  //para cada uno de los algoritmos que hemos definido
			
			String ficheroSalida = String.format("ficheros/Tiempos%s.csv",
					metodos.get(i).third()); //fichero del que cojemos los datos
			ficherosSalida.add(ficheroSalida);
			String label = metodos.get(i).third(); 
			System.out.println(label); //imprime el metodo que estamos probando
			
			TipoAjuste tipoAjuste = metodos.get(i).second(); //Selecionamos el ajuste
			GraficosAjuste.show(ficheroSalida, tipoAjuste, label); //con esta funcion generamos la grafica de los puntos con el ajuste

			// Obtener ajusteString para mostrarlo en gráfica combinada
			Pair<Function<Double, Double>, String> parCurve = GraficosAjuste.fitCurve(
					DataCurveFitting.points(ficheroSalida), tipoAjuste);
			String ajusteString = parCurve.second();
			labels.add(String.format("%s     %s", label, ajusteString));

					
		}

		GraficosAjuste.showCombined("Potencia", ficherosSalida, labels);
	}

}
