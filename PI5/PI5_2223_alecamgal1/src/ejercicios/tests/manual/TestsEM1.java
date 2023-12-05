package ejercicios.tests.manual;

import java.util.List;

import _datos.DatosCafe;
import _datos.DatosMulticonjunto;
import _utils.TestsPI5;
import ejemplos.ejemplo1.manual.MulticonjuntoPDR;
import ejercicios.ejercicio1.manual.CafePDR;
import us.lsi.common.String2;

public class TestsEM1 {

	public static void main(String[] args) {
		List.of(1,2,3).forEach(num_test -> {
			DatosCafe.iniDatos("ficheros/Ejercicio1DatosEntrada"+num_test+".txt");
			String2.toConsole("Solucion obtenida: %s\n", CafePDR.search());
			TestsPI5.line("*");
		});
	}
	
}
