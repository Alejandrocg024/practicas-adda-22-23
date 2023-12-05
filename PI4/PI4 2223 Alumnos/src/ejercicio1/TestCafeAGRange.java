package ejercicio1;

import java.util.List;
import java.util.Locale;

import _soluciones.SolucionCafe;
import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agstopping.StoppingConditionFactory;



public class TestCafeAGRange {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		
		AlgoritmoAG.ELITISM_RATE  = 0.10;
		AlgoritmoAG.CROSSOVER_RATE = 0.95;
		AlgoritmoAG.MUTATION_RATE = 0.8;
		AlgoritmoAG.POPULATION_SIZE = 1000;
		
		StoppingConditionFactory.NUM_GENERATIONS = 1000;
		StoppingConditionFactory.stoppingConditionType = StoppingConditionFactory.StoppingConditionType.GenerationCount;
		
		for(int ej=1; ej <= 3; ej++) {
			System.out.println("\n\n================================================================================================");
			System.out.println("####################### APARTADO "+ej+"-AG #######################");
			System.out.println("================================================================================================");
			
			InRangeCafeAG p = new InRangeCafeAG("ficheros/Ejercicio1DatosEntrada"+ej+".txt");
			
			AlgoritmoAG<List<Integer>,SolucionCafe> ap = AlgoritmoAG.of(p);
			ap.ejecuta();
			
			System.out.println(ap.bestSolution());
		}
	}
}
