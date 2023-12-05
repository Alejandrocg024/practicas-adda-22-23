package datos;

public record Pasillo(String c1, String c2, Double mts) {
	//c1 -> punto de cruce 1
	//c2 -> punto de cruce 2
	//mts: longitud de cables necesarios
	public static Pasillo ofFormat(String[] v) {
		return new Pasillo(v[0], v[1], Double.valueOf(v[2]));
	}
}
