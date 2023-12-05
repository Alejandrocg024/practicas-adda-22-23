package ejercicios.ejercicio1.manual;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import _datos.DatosCafe;
import _soluciones.SolucionCafe;
import us.lsi.common.List2;
import us.lsi.common.Map2;

public class CafePDR {

	public static record Spm(Integer a, Integer benef) implements Comparable<Spm> {
		public static Spm of(Integer a, Integer benef) {
			return new Spm(a, benef);
		}
		@Override
		public int compareTo(Spm sp) {
			return this.benef.compareTo(sp.benef);
		}
	}
	
	public static Map<CafeProblem, Spm> memory;
	public static Integer mejorValor; 

	public static SolucionCafe search() {
		memory =  Map2.empty();
		mejorValor = Integer.MIN_VALUE; // Estamos maximizando
		
		pdr_search(CafeProblem.initial(), 0, memory);
		return getSolucion();
	}

	private static Spm pdr_search(CafeProblem prob, Integer acumulado, Map<CafeProblem, Spm> memoria) {

		Spm res = null;
		Boolean esTerminal = prob.index().equals(DatosCafe.getNumVariedades());
		Boolean esSolucion =  true; //prob.remaining().stream().allMatch(e -> e == 0 );
		

		if (memory.containsKey(prob)) {
			res = memory.get(prob);
			
		} else if (esTerminal && esSolucion) {
			res = Spm.of(null, 0);
			memory.put(prob, res);
			if (acumulado > mejorValor) { // Estamos maximizando
				mejorValor = acumulado;
			}
		} else if (!esTerminal) {
			List<Spm> soluciones = List2.empty();
			for (Integer action : prob.actions()) {
				Double cota = acotar(acumulado, prob, action);   		
				if (cota < mejorValor) {
					continue;
				}
				CafeProblem vecino = prob.neighbor(action);
				Spm s = pdr_search(vecino, acumulado + action, memory);
				if (s != null) {
					Spm amp = Spm.of(action, s.benef() + action);
					soluciones.add(amp);
				}
			}
			// Estamos maximizando
			res = soluciones.stream().max(Comparator.naturalOrder()).orElse(null);
			if (res != null)
				memory.put(prob, res);
		}

		return res;
	}

	private static Double acotar(Integer acum, CafeProblem p, Integer a) {
		return acum + a + p.neighbor(a).heuristic();
	}

	public static SolucionCafe getSolucion() {
		List<Integer> acciones = List2.empty();
		CafeProblem prob = CafeProblem.initial();
		Spm spm = memory.get(prob);
		while (spm != null && spm.a != null) {
			CafeProblem old = prob;
			acciones.add(spm.a);
			prob = old.neighbor(spm.a);
			spm = memory.get(prob);
		}
		return SolucionCafe.of(acciones);
	}

}
