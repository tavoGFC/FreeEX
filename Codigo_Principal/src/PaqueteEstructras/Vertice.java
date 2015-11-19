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
	 * recibe un vertice y retorna true, si dos v�rtices son iguales  
	 * @param n
	 * @return true, si dos v�rtices son iguales { 
	 */
	public boolean equals(Vertice n){ 
		return nombre.equals(n.nombre); 
	} 
	
	/**
	 * establece el n�mero de v�rtice
	 * @param n
	 */
	public void asigVert(int n){
		numVertice = n; 
	}
	/**
	 * devuelve identificador del v�rtice {
	 *@return
	 */
	public String nomVertice(){ 
		return nombre;
	}
	/**
	 * brinda las caracter�sticas del v�rtice {  
	 * @return
	 */
	 public String caracteristicas(){
		 return nombre + " (" + numVertice + ")"; 
	 }
	
}