package PaqueteGrafosMatriz;

public class Vertice{       
	String nombre; int numVertice; 
	public Vertice(String x) {  
		nombre = x;
		numVertice = -1;
		} 
	// devuelve identificador del vértice
	public String nomVertice() {  
		return nombre; 
		} 
	 // true, si dos vértices son iguales
	public boolean equals(Vertice n) { 
		return nombre.equals(n.nombre); 
	}
	
}