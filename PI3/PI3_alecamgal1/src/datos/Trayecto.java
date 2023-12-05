package datos;

public record Trayecto(String ciudad1, String ciudad2, Double precio, Double tiempo) {
	
	public static Trayecto ofFormat(String[] formato) {
		String ciudad1 = formato[0];
		String ciudad2 = formato[1];
		Double precio = Double.parseDouble(formato[2].replace("euros", ""));
		Double tiempo = Double.parseDouble(formato[3].replace("min", ""));
		
		return new Trayecto(ciudad1, ciudad2, precio, tiempo);
	}

	public static Trayecto of(String ciudad1, String ciudad2, Double precio, Double tiempo) {
		return new Trayecto(ciudad1, ciudad2, precio, tiempo);
	}
	
	@Override
	public String toString() {
		return this.precio + " euros" + "\n" + this.tiempo + " minutos";
	}
}
