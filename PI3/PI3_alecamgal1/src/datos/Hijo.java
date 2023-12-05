package datos;

public record Hijo(Integer id1, Integer id2) {

	public static Hijo ofFormat(String[] formato) {
		return new Hijo(Integer.parseInt(formato[0]), Integer.parseInt(formato[1]));
	}
}
 