package PaqueteGrafosLista;
import PaqueteLista.*;

public class GrafoAdy {
	
	String nombre;
	int numVertice;
	Lista lad; //lista de adyacencia// constructor de la clase 
	
	public void VerticeAdy(String x) { 
		nombre = x; 
		numVertice = -1;
		lad = new Lista(); }

}
