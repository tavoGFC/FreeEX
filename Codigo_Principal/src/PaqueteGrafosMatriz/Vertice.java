package PaqueteGrafosMatriz;

import PaqueteCliente.Usuario;

public class Vertice{
	String nombre; 
	int numVertice; 
	public Vertice(String user){
		nombre = user;
		numVertice = -1; 
	} 
	public String nomVertice(){ // devuelve identificador del v�rtice {
		return nombre;
	}
	public boolean equals(Vertice n){// true, si dos v�rtices son iguales {  
		return nombre.equals(n.nombre); 
	} 
	public void asigVert(int n){// establece el n�mero de v�rtice
		numVertice = n; 
	}
	 public String caracteristicas(){ // caracter�sticas del v�rtice {  
		 return nombre + " (" + numVertice + ")"; 
	 }
	
}