package ejemplos;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Ejemplo2 {
	/*
	 * Definir secuencia y acumulador, nuestra secuencia tendran los valores a y b.
	 * SEC= valor si a menor que 5 y b menor que 5 y si no,Vamos cambiando el estado de nuestras variables dividiento a entre 2 y b-2
	 * En nuestro acumulador vamos acumulando el tostring
	 * DIF ENTRE NO FINAL y FINAL, es decir el resultado está haciendo operaciones
	 * LA condicin para activar el caso base es el if, si no vamos a ir llamando las demas.
	 */
	public static String solucionRecursivaNoFinal(Integer a, Integer b) {
		String r = null;
		if ( a < 5 || b < 5) {
			r = String.format("(%d)", a * b);
		} else {
			r = String.format("%d", a + b) + solucionRecursivaNoFinal(a/2, b-2);
		}
		return r;
	}
	
	/*
	 * EN la recursiva final tendremos que hacer dos llamadas, una en la que inicializamos y otra en la que ya hacemos todas las llamadas
	 *
	 * primero un metodo en el que llama al rec final y en ese caso hacemos la llamada recursiva
	 * 
	 */
	public static String solucionRecursivaFinal(Integer a, Integer b) {
		return recFinal("", a, b);
	}
	
	private static String recFinal(String ac, Integer a, Integer b) {
		String r = null;
		if (a < 5 || b < 5) {
			r = ac + String.format("(%d)", a * b);
		} else {
			r = recFinal(ac + String.format("%d", a + b), a/2, b-2);
		}
		return r;
	}
	
	/*
	 * 	 * ITERATIVA
	 * cambiamos lo que teniamos antes por un if y incluimos un bucle en el la condicion es la misma. Mientras que a y b
	 * realizamos la operacion de antes pero finalmente aplicamos la solucion del caso base que es lo que devolvemos
	 * Como estamis uterando en cada iteracion aplicamos en otro caso y cuando ese bucle termina es que hemos llegado al case base que es lo que devuelvo
	 */
	public static String solucionIterativa(Integer a, Integer b) {
		String ac = "";
		while (!(a < 5 || b < 5)) {
			ac = String.format("%s%d", ac, a+b);
			a /= 2;
			b -= 2;
		}
		return ac + String.format("(%d)", a * b);
	}
	
	//Necesitamos definirnos una tupla para poder pasar de la notación imperativa (iterativa por ejemplo) a la funcional.
	//Esta tupla necesita el valor del acumulador
	private static record Tupla(String ac, Integer a, Integer b) {
		public static Tupla of(String ba, Integer a, Integer b) {
			//Metodo factoria para construir el tipo tupla, accede a las propiedades de la tupla
			return new Tupla(ba, a, b);
		}
		
		public static Tupla first(Integer a, Integer b) {
			return of("", a, b); //Inicializamos el acumulador y pasamos los valores de entrada
		}
		
		public Tupla next() { //funcion next que integra la función de acumulación y actualiza
			return of(ac + String.format("%d", a+b), a/2, b-2);
		}
	}
	
	/*
	 * FUNCIONAL
	 * tenemos que definirnos un elemento que una tupla que almacenamos los valores iniciales y el acumulador.
	 * 
	 * tenemos que definirnos un elemento record con el string ac y a y b,
	 * dentro tenemos que definirnos el metodo constructor para crear
	 * 
	 * un metodo first que inicializamos por primera vez la tupla
	 * 
	 * ademas, el next que es como vamos a ir actualizando la tupla que es donde introducimos el operador recursivo donde cambiamos a y b
	 * 
	 * DE operativa a funcional necesitamos estos tres metodos
	 * 
	 * 
	 * Entonces nos definimos un string y un iterador que tenemos dos formas que es pasarle fvalor inicial, la cond que termina y la funcion. si usamos la que iene dos argumentos (valores iniciales y funcion a aplicar) necesitaremos despues un filter
	 * Iterate que vamos a usar es devolver una secuencia con unos valores iniciales y esa lista se va a aplicar una funcions que le pasamos. Los valores iniciales es una tupla con string vacio a y b
	 * La funcion - es la funcion next que hemos usado antes
	 * 
	 * Luego, aplicamos el filter con el que aplicamos la condicion logica y el primer caso que ya cumpla esa condicino es con el que nos quedamos porque es el que pasa el caso base.
	 * 
	 */
	public static String solucionFuncional(Integer a, Integer b) {
		//iterate(x,y):
			//devuelve una secuencia infinita partiendo desde un valor inicial x
			//y la lista se genera aplicando la funcion Y a los elementos n-1
			//La función Y es de tipo Unary Operator (eso significa que devuelve un elemento del mismo que se le pasa)
			//Por ejemplo X(2) = y(X(1)) donde X(1) es nuestro valor inicial ("", a, b)
			//			  X(3) = y(X(2))
			//            ...etc
			//En este caso esto se repite "infinitamente"
		
			//Pero existe otro modo para usar iterate que es del tipo:
			//iterate (valor inicial, predicado, operador binario)
			//en ese caso se repite la función operador binario hasta que se deje de cumplir el predicado
		
		//.filter(e -> e.a()<5 || e.b()<5)
			//seleccionamos los elementos que cumplen el predicado de la secuencia
			//entonces para ello filtramos aquellos en donde a<5 o b<5
		
		//findFirst().get()
			//y cogemos el primer elemento que l cumple para aplicarle la solucion base final
		Tupla t = Stream.iterate(Tupla.first(a, b), e -> e.next())
				.filter(e -> e.a()<5 || e.b()<5)
				.findFirst()
				.get();
		
		
		
		//finalmente añadimos el caso base:
		return t.ac() + String.format("(%d)", t.a() * t.b());
	}
}
