package ejercicio2;

import java.util.List;
import java.util.Set;

import _datos.DatosCurso;
import _datos.DatosCurso.Curso;
import _soluciones.SolucionCurso;
import us.lsi.ag.BinaryData;
import us.lsi.common.Set2;

public class BinCursoAG implements BinaryData<SolucionCurso>{
	
	public BinCursoAG (String fichero) {
		DatosCurso.iniDatos(fichero);
	}

	@Override
	public Integer size() {
		return DatosCurso.getNumCursos() ;
	}

	@Override
	public SolucionCurso solucion(List<Integer> ls) {
		return SolucionCurso.of(ls);
	}
	
	@Override
	public Double fitnessFunction(List<Integer> ls) {
		double goal = 0, error = 0;
		
		//Tengo que comprobar si se escogen todas las tematicas y en cuantos centros
		Set<Integer> tematicas = Set2.empty();
		Set<Integer> centros = Set2.empty();
		
		for(int i=0; i<size(); i++) { //bucle para recorrer los cursos
			if(ls.get(i) == 1) { //es decir, si ese curso se imparte
				Curso c = DatosCurso.getCursos().get(i);
				
				goal += DatosCurso.getCosteCurso(i);
				tematicas.addAll(c.tematicas()); //añado todas las tematicas del curso en concreto
				centros.add(c.centro()); //añado el centro del curso
			}
		}
		
		//Restricciones
		if(!tematicas.equals(DatosCurso.getTematicas())){//es decir, si no se cubren todas las tematicas
			error += DatosCurso.getNumTematicas() - tematicas.size(); //añadimos al error las tematicas que nos faltan
		}
		
		if(centros.size() > DatosCurso.getMaxCentros()) { //es decir, que se supere el numero maximo de centros
			error += centros.size() - DatosCurso.getMaxCentros(); //añado al error cuanto nos hemos pasado de centros
		}
		return -goal -10000*error;
 	}
}
