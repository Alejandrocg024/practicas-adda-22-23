package ejercicios.tests.manual;

import java.util.List;

import _datos.DatosCursos;
import _utils.TestsPI5;
import ejercicios.ejercicio2.manual.CursosPDR;
import us.lsi.common.String2;

public class TestsEM2 {

	public static void main(String[] args) {
		List.of(1,2,3).forEach(num_test -> {

			DatosCursos.iniDatos("ficheros/Ejercicio2DatosEntrada"+num_test+".txt");
			String2.toConsole("Solucion obtenida: %s\n", CursosPDR.search());
			TestsPI5.line("*");
		});
	}
}