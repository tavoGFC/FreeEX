package PaqueteCliente;

import org.json.JSONException;


import PaqueteGrafosMatriz.*;
import PaqueteJSON.*;

/**
 * 
 * @author Kevin
 *
 */

public class Usuario extends JSON{
	/**
	 * Atributos de la clase Cliente
	 */
	protected String correo;
	protected String ubicacion;
	protected String nombre;
	protected String mensaje;
	protected String mensajeEntrada;
	protected GrafoMatriz grafo;
	
	
	/**
	 * Constructor de la clase Cliente
	 * @param usuario
	 * @throws JSONException
	 */
	public Usuario(String correo,String nUsuario,GrafoMatriz grafo) throws JSONException {
		super();
		this.correo = correo;
		this.nombre = nUsuario;
		this.setGrafo(grafo);
		this.makeJSONObject("usuario", nUsuario);
		this.getGrafo().nuevoVertice(getCorreoElectronico());
		
	}
	
	/**
	 * Recive un String, permite redactar el mensaje que va a ser recibido
	 * @param msj
	 * @throws JSONException
	 */
	public void redactarMensaje(String msj) throws JSONException{
		this.setMensaje(msj);
		this.makeJSONObject(this.nombre,this.mensaje);
		System.out.println(this.Object);
	}
	
	/**
	 * Recive String con el usuario y el mensaje, permite realizar el envio de msj uno a uno
	 * @param Usuario
	 * @param mensaje
	 * @throws JSONException
	 */
	public void mensajeUnoAUno(String Usuario, String mensaje) throws JSONException{
		this.redactarMensaje(mensaje);
		this.AddtoJSONObject(Object, "receptor", Usuario);
		System.out.println(this.Object.toString());	
	}
	
	/**
	 *Recive String con el mensaje, permite realizar el envio de difusion
	 * @param mensaje
	 * @throws JSONException
	 */
	public void mensajeDifusion(String mensaje) throws JSONException{
		this.redactarMensaje(mensaje);
	}
	
	public void run(){
		while (true){
			
		}
	}
	
	/**
	 * Inicio de la seccion de getters
	 */
	public String getNombreUsuario(){
		return nombre;
	}
	
	public String getCorreoElectronico(){
		return correo;
	}
	
	public String getMensaje(){
		return mensaje;
	}

	public String getUbicacion() {
		return ubicacion;
	}
	public GrafoMatriz getGrafo() {
		return this.grafo;
	}
	/**
	 * Fin de la seccion de getters
	 */
	
	/**
	 * Inicio de la seccion de setters
	 */
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
	public void setGrafo(GrafoMatriz grafo) {
		this.grafo= grafo;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	/**
	 * Fin de la seccion de setters
	 */
}
