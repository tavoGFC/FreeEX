package com.example.gusfc_000.freeex.PaqueteEstructras;


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
	 * recibe un vertice y retorna true, si dos vertices son iguales
	 * @param n
	 * @return true, si dos vertices son iguales {
	 */
	public boolean equals(Vertice n){ 
		return nombre.equals(n.nombre); 
	} 
	
	/**
	 * establece el numero de vertice
	 * @param n
	 */
	public void asigVert(int n){
		numVertice = n; 
	}
	/**
	 * devuelve identificador del vertice {
	 *@return
	 */
	public String nomVertice(){ 
		return nombre;
	}
	/**
	 * brinda las caracteristicas del vertice {
	 * @return
	 */
	 public String caracteristicas(){
		 return nombre + " (" + numVertice + ")"; 
	 }
	
}