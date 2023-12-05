package tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import ejercicios.Ejercicio2;
import us.lsi.common.Files2;
import us.lsi.common.List2;
import us.lsi.common.Pair;
import us.lsi.curvefitting.DataCurveFitting;
import utils.GraficosAjuste;
import utils.Resultados;
import utils.TipoAjuste;

public class TestEjercicio2 {
	
	// Parámetros de generación de las listas
	private static Integer sizeMin = 100; // tamaño mínimo de lista
	private static Integer sizeMax = 100000; // tamaño máximo de lista
	private static Integer numSizes = 50; // número de tamaños de listas
	private static Integer minValue = 0; 
	private static Integer maxValue = 1000000;
	private static Integer numListPerSize = 1; // número de listas por cada tamaño  (UTIL???)  
	private static Random rr = new Random(System.nanoTime()); // para inicializarlo una sola vez y compartirlo con los métodos que lo requieran
	
	// Parámetros de medición
	private static Integer numMediciones = 5; // número de mediciones de tiempo de cada caso (número de experimentos)
	private static Integer numIter = 50; // número de iteraciones para cada medición de tiempo
	private static Integer numIterWarmup = 100000; // número de iteraciones para warmup
	
	public static void main(String[] args) {
		// Generación de Listas
		PropiedadesListas props = PropiedadesListas.of(sizeMin, sizeMax, numSizes, minValue, maxValue,numListPerSize, rr);
		generaFicherosDatos(props);
		generaFicherosTiempoEjecucion();
		muestraGraficas();
	}
	

	public record PropiedadesListas(Integer sizeMin, Integer sizeMax, Integer numSizes, Integer minValue, Integer maxValue, Integer numListPerSize, Random rr) {
		public static PropiedadesListas of(Integer sizeMin, Integer sizeMax, Integer numSizes, Integer minValue, Integer maxValue, Integer numListPerSize, Random rr) {
			return new PropiedadesListas(sizeMin, sizeMax, numSizes, minValue, maxValue, numListPerSize, rr);
		}
	}
	
	private static String ficheroListaEntrada = "ficheros/Listas.txt";
	private static List<Integer> umbrales = List.of(4, 10, 25, 50, 75, 100, 175, 250, 375, 500);
	private static BiConsumer<List<Integer>, Integer> metodoOrdenacion = Ejercicio2::sort;
	private static String etiquetaMetodo = "QuickSort";

	
	//Los siguientes metodos han sido copiados del ejemplo3 con algunas excepciones
	public static void muestraGraficas() {
		List<String> ficherosSalida = new ArrayList<>();
		List<String> labels = new ArrayList<>();
		
		for (Integer umbral:umbrales) { 
			String ficheroMediosSalida = String.format("ficheros/TiemposMed%sConUmbral%d.csv", etiquetaMetodo, umbral);
			String label = etiquetaMetodo + " con umbral "+umbral.toString();
					
			TipoAjuste tipoAjuste = TipoAjuste.NLOGN_0;
					
			GraficosAjuste.show(ficheroMediosSalida, tipoAjuste, label);
					
			// Obtener ajusteString para mostrarlo en gráfica combinada
			Pair<Function<Double, Double>, String> parCurve = GraficosAjuste.fitCurve(DataCurveFitting.points(ficheroMediosSalida), tipoAjuste);
			String ajusteString = parCurve.second();
					
			ficherosSalida.add(ficheroMediosSalida);
			labels.add(String.format("%s     %s", label, ajusteString));
			}
		GraficosAjuste.showCombined("QuickSort", ficherosSalida, labels);
		}			

	
	public static void generaFicherosDatos(PropiedadesListas p) {
		Resultados.cleanFile("ficheros/Listas.txt");
		
		generaFicheroListasEnteros("ficheros/Listas.txt", p);
	}
	
	//Metodo copiado del repositorio
	public static void generaFicheroListasEnteros(String fichero, PropiedadesListas p) {
		for (int i=0; i<p.numSizes(); i++) {
			int tam = p.sizeMin() + i*(p.sizeMax()-p.sizeMin())/(p.numSizes()-1);
			for (int j=0; j<p.numListPerSize(); j++) {
				List<Integer> ls = generaListaEnteros(tam, p);
				String sls = ls.stream().map(x->x.toString()).collect(Collectors.joining(",")); 
				Resultados.toFile(String.format("%d#%s",tam,sls), fichero, false);
			}
		}
	}
	
	//Metodo copiado del repositorio
	public static List<Integer> generaListaEnteros(Integer sizeList, PropiedadesListas p) {
		List <Integer> ls = new ArrayList<Integer>();
		for (int i=0;i<sizeList;i++) {
			ls.add(p.minValue()+p.rr().nextInt(p.maxValue()-p.minValue()));
		}
		return ls;
	}
	
	public static void generaFicherosTiempoEjecucion() {
		for (Integer umbral:umbrales) { 
			String ficheroSalida = String.format("ficheros/Tiempos%sConUmbral%d.csv", etiquetaMetodo, umbral);
			System.out.println(ficheroSalida);
			String ficheroMediosSalida = String.format("ficheros/TiemposMed%sConUmbral%d.csv", etiquetaMetodo, umbral);
			testTiemposEjecucion(ficheroListaEntrada, metodoOrdenacion, ficheroSalida, ficheroMediosSalida, umbral);
		}
	}

	private static void testTiemposEjecucion(String ficheroListas, BiConsumer<List<Integer>, Integer> metodoOrdenacion,
			String ficheroTiempos, String ficheroTiemposMedios, Integer umbral) {
		Map<Problema, Double> tiempos = new HashMap<Problema,Double>(); //Map con el tamaño del problema como clave y el tiempo calculado como valor
		Map<Integer, Double> tiemposMedios; // tiempos medios por cada tamaño
		List<String> lineasListas = Files2.linesFromFile(ficheroListas);
		
		for (int iter=0; iter<numMediciones; iter++) {
			System.out.println(iter);
			for (int i=0; i<lineasListas.size(); i++) { // numSizes*numListPerSize
				String lineaLista = lineasListas.get(i);
				List<String> ls = List2.parse(lineaLista, "#", Function.identity());
				Integer tam = Integer.parseInt(ls.get(0)); 
				List<Integer> le = List2.parse(ls.get(1), ",", Integer::parseInt);
				Problema p = Problema.of(tam, i%numListPerSize); //Tamaño del problema actual
				warmup(metodoOrdenacion, lineasListas.get(0), umbral);
				Long t0 = System.nanoTime();
				for (int z=0; z<numIter; z++) {
					metodoOrdenacion.accept(le, umbral);
					}
					Long t1 = System.nanoTime();
					actualizaTiempos(tiempos, p, Double.valueOf(t1-t0)/numIter); //Actualizamos los tiempos
			}
		}
		
		tiemposMedios = tiempos.entrySet().stream()
				.collect(Collectors.groupingBy(x->x.getKey().tam(),
							Collectors.averagingDouble(x->x.getValue())
							)
						);

		Resultados.toFile(tiemposMedios.entrySet().stream()
				.map(x->TResultMedD.of(x.getKey(),x.getValue()))
				.map(TResultMedD::toString),
			ficheroTiemposMedios,
			true);
		
	}
	
	private static void actualizaTiempos(Map<Problema, Double> tiempos, Problema p, double d) {
		if (!tiempos.containsKey(p)) {
			tiempos.put(p, d);
		} else if (tiempos.get(p) > d) {
				tiempos.put(p, d);
		}
	}


	private static int warmup(BiConsumer<List<Integer>, Integer> busca, String lineaLista, Integer umbral) {
		int res=0;
		List<String> ls = List2.parse(lineaLista, "#", Function.identity());
		Integer tam = Integer.parseInt(ls.get(0)); 
		List<Integer> le = List2.parse(ls.get(1), ",", Integer::parseInt);
		Integer z = 0; 
		for (int i=0; i<numIterWarmup; i++) {
			metodoOrdenacion.accept(le, umbral);
		}
		res = z>0?z+tam:tam;
		return res;
	}



	record TResult(Integer tam, Integer numList, Integer numCase, Long t) {
		public static TResult of(Integer tam, Integer numList, Integer numCase, Long t){
			return new TResult(tam, numList, numCase, t);
		}
		
		public String toString() {
			return String.format("%d,%d,%d,%d", tam, numList, numCase, t);
		}
	}
	
	record TResultD(Integer tam, Integer numList, Integer numCase, Double t) {
		public static TResultD of(Integer tam, Integer numList, Integer numCase, Double t){
			return new TResultD(tam, numList, numCase, t);
		}
		
		public String toString() {
			return String.format("%d,%d,%d,%.0f", tam, numList, numCase, t);
		}
	}
	
	record TResultMedD(Integer tam, Double t) {
		public static TResultMedD of(Integer tam, Double t){
			return new TResultMedD(tam, t);
		}
		
		public String toString() {
			return String.format("%d,%.0f", tam, t);
		}
	}
	
	//Record para almacenar el tamaño del problema
	record Problema(Integer tam, Integer numList) {
		public static Problema of(Integer tam, Integer numList){
			return new Problema(tam, numList);
		}
	}
	

}
