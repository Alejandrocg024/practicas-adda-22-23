package ejercicios;

import java.util.Comparator;
import java.util.List;

import us.lsi.common.IntPair;
import us.lsi.common.List2;
import us.lsi.common.Preconditions;
import us.lsi.math.Math2;

public class Ejercicio2 {

	/*
	 * 2. Analizar el tiempo de ejecución del algoritmo Quicksort usando la bandera holandesa.
	 * Analizar la influencia del tamaño del umbral del caso base, en el que se usará la ordenación por inserción
	 */
	
	//Todos estos metodos son copiados del repositorio con la modificacion del umbral del caso base.
	
	public static <E extends Comparable<? super E>> void sort(List<E> lista, Integer umbral){
		Comparator<? super E> ord = Comparator.naturalOrder();
		quickSort(lista,0,lista.size(),ord, umbral);	
	}
	
	private static <E> void quickSort(List<E> lista, int i, int j, Comparator<? super E> ord, Integer umbral){
		Preconditions.checkArgument(j>=i);
		if(j-i <= umbral){
			ordenaBase(lista, i, j, ord);
		}else{
			E pivote = escogePivote(lista, i, j);
			IntPair p = banderaHolandesa(lista, pivote, i, j, ord);
			quickSort(lista,i,p.first(),ord, umbral);
			quickSort(lista,p.second(),j,ord, umbral);			
		}
	}
	
	public static <T> void ordenaBase(List<T> lista, Integer inf, Integer sup, Comparator<? super T> ord) {		
		for (int i = inf; i < sup; i++) {
		      for(int j = i+1; j < sup; j++){
		    	  if(ord.compare(lista.get(i),lista.get(j))>0){
		    		  List2.intercambia(lista, i, j);
		    	  }
		      }
		}
	}
	private static <E> IntPair banderaHolandesa(List<E> ls, E pivote, Integer i, Integer j,  Comparator<? super E> cmp){
		Integer a=i, b=i, c=j;
		while (c-b>0) {
		    E elem =  ls.get(b);
		    if (cmp.compare(elem, pivote)<0) {
		    	List2.intercambia(ls,a,b);
				a++;
				b++;
		    } else if (cmp.compare(elem, pivote)>0) {
		    	List2.intercambia(ls,b,c-1);
				c--;	
		    } else {
		    	b++;
		    }
		}
		return IntPair.of(a, b);
	}
	
	private static <E> E escogePivote(List<E> lista, int i, int j) {
		E pivote = lista.get(Math2.getEnteroAleatorio(i, j));
		return pivote;
	}
}