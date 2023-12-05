package ejercicios.tests;

import java.util.List;

import _datos.DatosCafe;
import _soluciones.SolucionCafe;
import _utils.GraphsPI5;
import _utils.TestsPI5;
import ejercicios.ejercicio1.CafeVertex;

public class TestsEjercicio1 {
	
	public static void main(String[] args) {
		List.of(1,2,3).forEach(num_test -> {
			TestsPI5.iniTest(						//Gv con la solucion
					"Ejercicio1DatosEntrada", 		//Nobre de los ficheros de test
					num_test, 						//Numero de test
					DatosCafe::iniDatos);			//Referencia al metodo para inicialiazar los datos
			
			TestsPI5.tests(
				CafeVertex.initial(),		// Vertice Inicial
				CafeVertex.goal(),			// Predicado para un vertice final
				GraphsPI5::cafeBuilder,		// Referencia al Builder del grafo
				CafeVertex::greedyEdge,		// Referencia a la Funcion para la arista voraz
				SolucionCafe::of);			// Referencia al metodo factoria para la solucion
		});
	}

}
