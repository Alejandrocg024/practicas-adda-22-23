package ejemplos.tests;

import java.util.List;
import java.util.function.Predicate;

import _datos.DatosMulticonjunto;
import _soluciones.SolucionMulticonjunto;
import _utils.GraphsPI5;
import _utils.TestsPI5;
import ejemplos.ejemplo1.MulticonjuntoVertex;

public class TestsEjemplo1 {
	
	public static void main(String[] args) {
		List.of(1,2,3).forEach(num_test -> {
			TestsPI5.iniTest(						//Gv con la solucion
					"Ejemplo1DatosEntrada", 		//Nobre de los ficheros de test
					num_test, 						//Numero de test
					DatosMulticonjunto::iniDatos);	//Referencia al metodo para inicialiazar los datos
			
			// TODO Defina en el tipo vertice un m. factoria para el vertice inicial
			// TODO Defina en el tipo vertice un m. static / Predicate para los vertices finales 
			TestsPI5.tests(
				MulticonjuntoVertex.initial(),		// Vertice Inicial
				MulticonjuntoVertex.goal(),			// Predicado para un vertice final
				GraphsPI5::multisetBuilder,			// Referencia al Builder del grafo
				MulticonjuntoVertex::greedyEdge,	// Referencia a la Funcion para la arista voraz
				SolucionMulticonjunto::of);			// Referencia al metodo factoria para la solucion
		});
	}

}
