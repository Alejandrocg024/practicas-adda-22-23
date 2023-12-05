package datos;

public record Persona(Integer id, String nombre, Integer anyoNacimiento, String ciudadNacimiento) {

	public static Persona ofFormat(String[] formato) {
		//1,Paco,1943,Cadiz
		Integer id = Integer.parseInt(formato[0]);
		String nomb = formato[1];
		Integer anyo = Integer.parseInt(formato[2]);
		String ciudad = formato[3];
		
		return new Persona(id, nomb, anyo, ciudad);	
	}
	
	public static Persona of(Integer id, String nomb, Integer anyo, String ciudad) {
		return new Persona(id, nomb, anyo, ciudad);	
	}
	
	@Override
	public String toString() {
		return nombre;
	}
}
