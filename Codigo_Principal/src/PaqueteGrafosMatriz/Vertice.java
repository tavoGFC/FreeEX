package PaqueteGrafosMatriz;

public class Vertice{       
	String nombre; int numVertice; 
	public Vertice(String x) {  
		nombre = x;
		numVertice = -1;
		} 
	// devuelve identificador del v�rtice
	public String nomVertice() {  
		return nombre; 
		} 
	 // true, si dos v�rtices son iguales
	public boolean equals(Vertice n) { 
		return nombre.equals(n.nombre); 
	}
	
}