package ejercicio1;

import java.util.List;

import _datos.DatosCafe;
import _soluciones.SolucionCafe;
import us.lsi.ag.ValuesInRangeData;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;


public class InRangeCafeAG implements ValuesInRangeData<Integer, SolucionCafe>{
	
	public InRangeCafeAG (String linea) {
		DatosCafe.iniDatos(linea);
	}

	@Override
	public ChromosomeType type() {
		return ChromosomeType.Range;
	}

	@Override
	public Integer size() {
		return DatosCafe.getNumVariedades();
	}
	
	@Override
	public Integer max(Integer i) {
		return DatosCafe.getMaxCantidadVariedad(i) + 1;
	}
	
	@Override
	public Integer min(Integer i) {
		return 0;
	}

	@Override
	public Double fitnessFunction(List<Integer> ls) {
		double goal = 0, error = 0;
		
		//funcion objetivo
		for(int i=0; i< ls.size(); i++) { //recorrer variedades para realizar el sumatorio
			if(ls.get(i)>0) {
				goal += ls.get(i) * DatosCafe.getBeneficioVariedad(i);
			}
		}
		
		//restricciones
		for (int j = 0; j < DatosCafe.getNumTipos(); j++) { //recorrer tipos para realizar el sumatorio
			Integer kgsTotales = DatosCafe.getCantidadTipo(j);
			Double kgsUsados = 0.;
			
			for (int i = 0; i < DatosCafe.getNumVariedades(); i++) { //recorrer variedades para realizar el sumatorio
				kgsUsados += ls.get(i) * DatosCafe.getCantidadCafeEnVariedad(j, i);	
			}
			
			if(kgsTotales < kgsUsados) { //restriccion por si se usan mas kgs de los totales
				error += kgsUsados - kgsTotales;
			}
		}
		return goal - 10000*error;
	}
	
	@Override
	public SolucionCafe solucion(List<Integer> ls) {
		return SolucionCafe.of(ls);
		
	}
}
