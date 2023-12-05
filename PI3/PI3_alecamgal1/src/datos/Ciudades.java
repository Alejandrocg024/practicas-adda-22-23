package datos;

public record Ciudades(String nombre, Integer puntuacion) {
	
	public static Ciudades ofFormat(String[] formato) {
		String nombre = formato[0];
		Integer puntuacion = Integer.parseInt(formato[1].replace("p", ""));
		return new Ciudades(nombre, puntuacion);
	}

	public static Ciudades of(String nombre, Integer puntuacion) {
		return new Ciudades(nombre, puntuacion);
	}
	
	@Override
	public String toString() {
		return this.nombre;
	}
}
