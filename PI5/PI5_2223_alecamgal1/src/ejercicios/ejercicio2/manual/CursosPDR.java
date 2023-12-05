package ejercicios.ejercicio2.manual;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import _datos.DatosCursos;
import _soluciones.SolucionCursos;
import us.lsi.common.List2;

public class CursosPDR {

	public static record Spm(Integer a, Double weight) implements Comparable<Spm> {
		public static Spm of(Integer a, Double weight) {
			return new Spm(a, weight);
		}

		@Override
		public int compareTo(Spm sp) {

			return this.weight.compareTo(sp.weight);
		}

	}

	public static Double minValue = Double.MAX_VALUE;
	public static Map<CursosProblem, Spm> memory;
	public static CursosProblem start;

	public static SolucionCursos search() {
		CursosPDR.minValue = Double.MAX_VALUE;
		Set<Integer> initialThemes = CursosProblem.initial().remaining();
		Set<Integer> initialCentres = CursosProblem.initial().centros();
		CursosPDR.start = CursosProblem.of(0, initialThemes, initialCentres);
		CursosPDR.memory = new HashMap<>();
		pdr_search(start, 0., memory);
		return CursosPDR.getSol();
	}

	private static Spm pdr_search(CursosProblem prob, Double acumulado, Map<CursosProblem, Spm> memoria) {
		Spm r;

		Boolean esTerminal = prob.index() == DatosCursos.getNumCursos();
		Boolean esSolucion = prob.remaining().isEmpty();

		if (memoria.containsKey(prob)) {
			r = memoria.get(prob);

		} else if (esSolucion && esTerminal) {
			r = Spm.of(null, 0.);
			memoria.put(prob, r);
			if (acumulado < minValue) {
				minValue = acumulado;
			}

		} else {
			List<Spm> soluciones = new ArrayList<>();
			for (Integer a : prob.actions()) {
				CursosProblem vecino = prob.neighbor(a);
				Double ac = acumulado + a * DatosCursos.getCosteCurso(prob.index());
				Spm s = pdr_search(vecino, ac, memoria);
				if (s != null) {
					Spm sp = Spm.of(a, s.weight() + a * DatosCursos.getCosteCurso(prob.index()));
					soluciones.add(sp);
				}
			}
			r = soluciones.stream().filter(s -> s != null).min(Comparator.naturalOrder()).orElse(null);
			memory.put(prob, r);
		}
		return r;

	}

	private static SolucionCursos getSol() {
		List<Integer> acciones = List2.empty();
		CursosProblem v = CursosPDR.start;
		Spm s = CursosPDR.memory.get(v);
		while (s.a() != null) {
			acciones.add(s.a());
			v = v.neighbor(s.a());
			s = CursosPDR.memory.get(v);
		}
		return SolucionCursos.of_Range(acciones);
	}

}