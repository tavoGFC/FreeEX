package PaqueteGrafosMatriz;

import PaqueteCliente.Usuario;

public class Vertice{
	String nombre; 
	int numVertice; 
	public Vertice(String user){
		nombre = user;
		numVertice = -1; 
	} 
	public String nomVertice(){ // devuelve identificador del vértice {
		return nombre;
	}
	public boolean equals(Vertice n){// true, si dos vértices son iguales {  
		return nombre.equals(n.nombre); 
	} 
	public void asigVert(int n){// establece el número de vértice
		numVertice = n; 
	}
	 public String caracteristicas(){ // características del vértice {  
		 return nombre + " (" + numVertice + ")"; 
	 }
	
}