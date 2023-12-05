package ejercicios;

import java.util.stream.Stream;

public class Ejercicio2 {

	/*
	 * 2. Dada la siguiente definición recursiva de la función f (que toma como entrada 2 
	 * números enteros positivos y una cadena, y devuelve un número entero): ****
	 * 
	 * Proporcione una solución iterativa usando while, una recursiva no 
	 * final, una recursiva final, y una en notación funcional.
	 */
	
	//SEC= nuestra secuencia será la condicion
	//ACUM= Vamos acumulando resultados
	public static Integer solucionIterativa(Integer a, Integer b, String s) {
		Integer ac = 0;
		while(s.length() != 0 && !(a<2 || b<2)) { //Al solamente entrar enteros, a y b solamente no entrarian cuando fuesen 0 o 1
			//Caso base que cubren todo caso y a%s.length()<b%s.length()
			if (a%s.length()<b%s.length()) {
				ac += a+b;
				s = s.substring(a%s.length(), b%s.length());
				a -= 1;
				b /= 2;
			} else {
				//en otro caso
				ac += a*b;
				s = s.substring(b%s.length(), a%s.length());
				a /= 2;
				b -= 1;
			}
		}
		
		//casos concretos
		if (s.length()==0) {
			ac += a * a + b * b;
		} else {
			ac += a+b+s.length();
		}
		
		return ac;
	}
	
	public static Integer solucionRecNoFinal(Integer a, Integer b, String s) {
		Integer res = null; //acumulador
		if (s.length()==0) {
			res = (a*a) + (b*b);
		} else if (a<2 || b<2) {
			res = a+b+s.length();
		} else if (a%s.length()<b%s.length()) { //caso previo al caso base que contiene recursion
			res= a + b + solucionRecNoFinal(a-1, b/2, s.substring(a%s.length(), b%s.length()));
		} else { //caso base con recursion
			res= a * b + solucionRecNoFinal(a/2, b-1, s.substring(b%s.length(), a%s.length()));
		}
		return res;
	}
	
	public static Integer solucionRecFinal(Integer a, Integer b, String s) {
		return recFinal(0, a, b, s); //llamamos a la recursiva inicial inicializando el acumulador a 0
	}
	
	private static Integer recFinal(Integer ac, Integer a, Integer b, String s) {
		Integer res = null;
		if (s.length()==0) {
			res = ac + (a*a) + (b*b);
		} else if (a<2 || b<2) {
			res = ac + a + b + s.length();
		} else if (a%s.length()<b%s.length()) { //caso previo al caso base que contiene recursion que lo sumamos con el acumulador
			res= recFinal(ac + a + b,a-1, b/2, s.substring(a%s.length(), b%s.length()));
		} else { //caso base con recursion que lo sumamos con el acumulador
			res= recFinal(ac + a * b,a/2, b-1, s.substring(b%s.length(), a%s.length()));
		}
		return res;
	}
	
	//Necesitaremos una tupla en la que guardar en valor del acumulador y las llamadas recursivas para la soluciono Funcional
	private static record Tupla(Integer ac, Integer a, Integer b, String s) {
		public static Tupla of(Integer base, Integer a, Integer b, String s) { //constructor
			return new Tupla(base, a, b, s);
		}
		
		public static Tupla first(Integer a, Integer b, String s) { //primer elemento con el acumulador a 0
			return of(0, a, b, s);
		}
		
		public Tupla next() { //llamadas recursivas
			Tupla res = null;
			if (a%s.length()<b%s.length()) {
				res= Tupla.of(ac + a + b,a-1, b/2, s.substring(a%s.length(), b%s.length()));
			} else {
				res= Tupla.of(ac + a * b,a/2, b-1, s.substring(b%s.length(), a%s.length()));
			}
			return res;
		}
	}
	
	public static Integer solucionFuncional(Integer a, Integer b, String s) {
		Tupla t = Stream.iterate(Tupla.first(a, b, s), e -> e.next()) //Itero con el elemento inicial y el metodo next creado anteriormente
				.filter(e -> e.s().length()==0 || (e.a() < 2 || e.b() < 2)) //Tomo solo las que tienen las condiciones que queremos
				.findFirst()
				.get();
		
		Integer res = null; 
		//Trato las condiciones concretas
		if (t.s().length()==0) {
			res = t.ac() + t.a() * t.a() + t.b() * t.b();
		} else {
			res = t.ac() + t.s().length() + t.a() + t.b();
		}
		return res;
	}
}
