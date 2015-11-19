package PaqueteEstructras;

import PaqueteCliente.Usuario;

/**
 * 
 * @author Kevin
 *
 */
public class Vertice{
	String nombre; 
	int numVertice; 
	public Vertice(String user){
		nombre = user;
		numVertice = -1; 
	} 
	
	/**
	 * recibe un vertice y retorna true, si dos vértices son iguales  
	 * @param n
	 * @return true, si dos vértices son iguales { 
	 */
	public boolean equals(Vertice n){ 
		return nombre.equals(n.nombre); 
	} 
	
	/**
	 * establece el número de vértice
	 * @param n
	 */
	public void asigVert(int n){
		numVertice = n; 
	}
	/**
	 * devuelve identificador del vértice {
	 *@return
	 */
	public String nomVertice(){ 
		return nombre;
	}
	/**
	 * brinda las características del vértice {  
	 * @return
	 */
	 public String caracteristicas(){
		 return nombre + " (" + numVertice + ")"; 
	 }
	
}