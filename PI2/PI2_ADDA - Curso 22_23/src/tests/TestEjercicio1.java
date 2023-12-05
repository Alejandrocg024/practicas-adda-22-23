package tests;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import ejercicios.Ejercicio1;
import us.lsi.common.Pair;
import us.lsi.common.Trio;
import us.lsi.curvefitting.DataCurveFitting;
import utils.GraficosAjuste;
import utils.Resultados;
import utils.TipoAjuste;

public class TestEjercicio1 {
	
	public static void main(String[] args) {
		//generaFicherosTiempoEjecucion(); //se generan puntos relacionando el tamaño con el tiempo para resolver el problema
		muestraGraficas(); //mostramos las graficas
	}
	
	//Definimos todos los parametros
	private static Integer nMin = 1; // n mínimo para el cálculo de potencia -> menor tamaño con el que probamos
	private static Integer nMaxRec = 50000; // n máximo para el cálculo de potencia -> maximo tamaño con el que qwueremos probar
	private static Integer nMaxIter = 50000; // n máximo para el iterativo
	private static Integer numSizes = 30; // número de problemas (número de potencias distintas a calcular) -> numero de tamaños diferentes con los que probamos. PEm cogeme problemas de tam 50, 100, 150, 200, establece el incremento
	private static Integer numMediciones = 10; // número de mediciones de tiempo de cada caso (número de experimentos) -> num de veces que repetimos los experimentos y nos quedamos con el minimo
	private static Integer numIter = 50; // número de iteraciones para cada medición de tiempo -> para un tamaño determinado repetimos el calculo 50 veces y nos quedamos con la media
	private static Integer numIterWarmup = 10000; // número de iteraciones para warmup (bucle for para llamar al metodoc varias veces)
	
	//dividiremos los resultados entre los realizados con BigInteger y los realizados con Double
	// Lista de trios con los metodos a probar. Cada trio está formado por:
		//	-La funcion a la que se va a medir el tiempo de ejecucion -> BiFunction
		//	-Tipo de ajuste que se realiza en la grafica
		//	-Etiqueta para identificar grafica y CSV
	private static List<Trio<Function<Integer, Number>, TipoAjuste, String>> metodosBigInteger = 
			List.of(
				Trio.of(Ejercicio1::factorialBigIntegerRec, TipoAjuste.EXP2, "Factorial_BigInt_Recursivo"), 
				Trio.of(Ejercicio1::factorialBigIntegerIter, TipoAjuste.EXP2, "Factorial_BigInt_Iterativo")
			);
	
	private static List<Trio<Function<Integer, Number>, TipoAjuste, String>> metodosDouble = 
			List.of(
					Trio.of(Ejercicio1::factorialDoubleRec, TipoAjuste.LINEAL, "Factorial_Double_Recursivo"), 
					Trio.of(Ejercicio1::factorialDoubleIter, TipoAjuste.LINEAL,"Factorial_Double_Iterativo")
			);


	//Todo el código que viene a continuación está copiado del ejemplo 2
	//primero generamos los ficheros para guardar los datos de ejecución
	private static <E> void generaFicherosTiempoEjecucionMetodos(List<Trio<Function<E, Number>, TipoAjuste, String>> metodos) {
		
		for (int i=0; i<metodos.size(); i++) { //por cada elemnento de metodos medimos el tiempo de ejecucion (2 iteraciones)
			int numMax = i==0 ? nMaxRec : nMaxIter; 
			Boolean flagExp = i==0 ? true : false;
			
			String ficheroSalida = String.format("ficheros/Tiempos%s.csv",
					metodos.get(i).third());
			
			//llamamos al metodo que va a realizar las ejecuciones y mediciones
			testTiemposEjecucion(nMin, numMax, 
						metodos.get(i).first(),
						ficheroSalida,
						flagExp);
			}
		
	}
	
	public static void generaFicherosTiempoEjecucion() {
		
		generaFicherosTiempoEjecucionMetodos(metodosBigInteger);
		generaFicherosTiempoEjecucionMetodos(metodosDouble);
	}
	
	
	public static <E> void muestraGraficasMetodos(List<Trio<Function<E, Number>, TipoAjuste, String>> metodos, List<String>
			ficherosSalida, List<String> labels) {
		for (int i=0; i<metodos.size(); i++) { 
			
			String ficheroSalida = String.format("ficheros/Tiempos%s.csv",
					metodos.get(i).third()); //fichero del que cojemos los datos
			ficherosSalida.add(ficheroSalida);
			String label = metodos.get(i).third();
			System.out.println(label);  //imprime el metodo que estamos probando

			TipoAjuste tipoAjuste = metodos.get(i).second(); //Seleccionamos el ajuste
			GraficosAjuste.show(ficheroSalida, tipoAjuste, label);	//generamos la grafica de los puntos con el ajuste
			
			// Obtener ajusteString para mostrarlo en gráfica combinada
			Pair<Function<Double, Double>, String> parCurve = GraficosAjuste.fitCurve(
					DataCurveFitting.points(ficheroSalida), tipoAjuste);
			String ajusteString = parCurve.second();
			labels.add(String.format("%s     %s", label, ajusteString));
		}
	}
	
	public static void muestraGraficas() {
		List<String> ficherosSalida = new ArrayList<>();
		List<String> labels = new ArrayList<>();
		
		muestraGraficasMetodos(metodosBigInteger, ficherosSalida, labels);
		muestraGraficasMetodos(metodosDouble, ficherosSalida, labels);
		
		GraficosAjuste.showCombined("Factorial", ficherosSalida, labels);
	}
	
	

	/*
	 * nMin: valor min del calculo de la potencia
	 * nMax: valor max en el calculo de la potencia
	 * Function: funcion en la que queremos medir el tiempo
	 * ficheroTiempos: ruta del fichero donde guardamos los resultados
	 */
	
	@SuppressWarnings("unchecked")
	public static <E> void testTiemposEjecucion(Integer nMin, Integer nMax,
			Function<E, Number> funcionFact,
			String ficheroTiempos,
			Boolean flagExp) {
		Map<Problema, Double> tiempos = new HashMap<Problema,Double>(); //creamos un hashmap para guardar los tiempos de ejecucion de cada problema
		Integer nMed = flagExp ? 1 : numMediciones; 
		for (int iter=0; iter<nMed; iter++) { //numero de veces que vamos a realizar las mediciones
			for (int i=0; i<numSizes; i++) { //numero e llamadas recursivas a hacer
				Double r = Double.valueOf(nMax-nMin)/(numSizes-1);
				Integer tam = (Integer.MAX_VALUE/nMax > i) 
						? nMin + i*(nMax-nMin)/(numSizes-1)
						: nMin + (int) (r*i) ; //tamaño maximo en la potencia
				Problema p = Problema.of(tam);
				System.out.println(tam);
				warmup(funcionFact, 10); //"Calentamiento" para ajustar mejor el tiempo
				Integer nIter = flagExp ? numIter/(i+1) : numIter;
				Number[] res = new Number[nIter]; //array en el que gardaremos los resultados con un tamaño igual al numero de iteraciones
				Long t0 = System.nanoTime();
				for (int z=0; z<nIter; z++) {
					res[z] = funcionFact.apply((E) tam); //guardamos los resultados de cada iteracion
				}
				Long t1 = System.nanoTime();
				actualizaTiempos(tiempos, p, Double.valueOf(t1-t0)/nIter);  //actualizamos el tiempo relacionado con cada problema trar calcular
			}
			
		}
		
	
		//guardamos el resultado en el fichero
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
	
	
	private static <E> BigInteger warmup(Function<E, Number> fib, Integer n) {
		BigInteger res=BigInteger.ZERO;
		BigInteger z = BigInteger.ZERO; 
		for (int i=0; i<numIterWarmup; i++) {
			if (fib.apply((E) n).equals(z)) z.add(BigInteger.ONE);
		}
		res = z.equals(BigInteger.ONE)? z.add(BigInteger.ONE):z;
		return res;
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
}